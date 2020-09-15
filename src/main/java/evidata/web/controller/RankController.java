package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import evidata.core.model.Rank;
import evidata.core.service.RankService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 */
@Controller
public class RankController {
    private static final Logger log = LoggerFactory.getLogger(RankController.class);

    @Autowired
    private RankService rankService;


    @RequestMapping(value = "/admin/rankList", method = RequestMethod.GET)
    public String getRanks(Model model) {

        List<Rank> ranks = rankService.getAllRanks();

        model.addAttribute("ranks", ranks);
        return "admin/rankList";
    }

    @RequestMapping(value = {"admin/rankEdit", "admin/rankEdit/{id}"}, method = RequestMethod.GET)
    public String rankEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getRank: id={}", id);
        Rank rank;
        if (id != null) {
            Optional<Rank> rankOptional = rankService.findRank(id);

            rank = rankOptional.get();
        } else {
            rank = new Rank();
        }

        model.addAttribute("rank", rank);
        return "admin/rankEdit";

    }


    @RequestMapping(value = "admin/rankEdit", method = RequestMethod.POST)
    public String rankSave(@Valid Rank rank, BindingResult bindingResult) {
        log.trace("saveRank: {}", rank);

        Optional<Rank> rankExists = rankService.findByNameIgnoringCase(rank.getName());
        if (rankExists.isPresent()) {
            bindingResult
                    .rejectValue("name", "error.rank",
                            "There is already a rank registered with the type provided");
        }

        if (bindingResult.hasErrors()) {
            return "admin/rankEdit";
        }else {
            //no errors, save the entity
            rankService.saveRank(rank);
            //redirect to list view
            return "redirect:/admin/rankList";
        }
    }


    @RequestMapping(value = "admin/rankDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteRank(Model model, @PathVariable final Long id) {
        log.trace("deleteRank: id={}", id);

        rankService.deleteRank(id);

        return new RedirectView("/admin/rankList");
    }
}
