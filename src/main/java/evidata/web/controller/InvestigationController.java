package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import evidata.core.dto.InvestigationSearch;
import evidata.core.model.AppUser;
import evidata.core.model.Equipment;
import evidata.core.model.Expertise;
import evidata.core.model.Investigation;
import evidata.core.service.AppUserService;
import evidata.core.service.EquipmentService;
import evidata.core.service.ExpertiseService;
import evidata.core.service.InvestigationService;
import evidata.web.PageWrapper;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Controller
public class InvestigationController {
    private static final Logger log = LoggerFactory.getLogger(InvestigationController.class);

    @Autowired
    InvestigationService investigationService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value = "/investigationList", method = RequestMethod.GET)
    public String getInvestigations(HttpSession session, Authentication authentication,
                                    Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {

        int currentPage = 1;
        int pageSize = 5;
        if (page.isPresent() && page.get() > 0) {
            currentPage = page.get();
        }
        if (size.isPresent() && size.get() > 0) {
            pageSize = size.get();
        }

        InvestigationSearch search = (InvestigationSearch) session.getAttribute("InvestigationSearch");
        if (search == null) {
            search = new InvestigationSearch();
        }

        return doSearch(authentication, model, currentPage, pageSize, search);
    }

    @RequestMapping(value = "/investigationList", method = RequestMethod.POST)
    public String searchInvestigations(HttpSession session, Model model, Authentication authentication, InvestigationSearch search) {

        int currentPage = 1;
        int pageSize =5;

        session.setAttribute("InvestigationSearch", search);

        return doSearch(authentication, model, currentPage, pageSize, search);
    }

    @RequestMapping(value = {"investigationEdit", "investigationEdit/{id}"}, method = RequestMethod.GET)
    public String investigationEdit(Model model, Authentication authentication, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getInvestigation: id={}", id);
        Investigation investigation;
        if (id != null) {
            Optional<Investigation> investigationOptional = investigationService.findInvestigation(id);

            investigation = investigationOptional.get();
        } else {
            investigation = new Investigation();
            investigation.setDate(new Date());
        }

        model.addAttribute("investigation", investigation);
        List<Expertise> expertises = expertiseService.getAllExpertises();
        model.addAttribute("expertises", expertises);

        List<Equipment> equipments = equipmentService.getAllEquipments();
        model.addAttribute("equipments", equipments);

        List<AppUser> users = appUserService.getUsersByRole("RESPONSIBLE");
        model.addAttribute("users", users);

        return "investigationEdit";

    }


    @RequestMapping(value = "investigationEdit", method = RequestMethod.POST)
    public String investigationSave( Model model, @Valid Investigation investigation, BindingResult bindingResult) {
        log.trace("saveInvestigation: {}", investigation);

        if (investigation.getEvidence().length() > 100 && investigation.getEvidence().length()< 10) {
            bindingResult
                    .rejectValue("evidence", "error.investigation");
        }
        if (investigation.getMethod().length() > 50 && investigation.getMethod().length() < 10) {
            bindingResult
                    .rejectValue("method", "error.investigation");
        }
        if (investigation.getResult().length() > 100 && investigation.getResult().length() < 10) {
            bindingResult
                    .rejectValue("result", "error.investigation");
        }
        if (bindingResult.hasErrors()) {

            model.addAttribute("investigation", investigation);
            List<Expertise> expertises = expertiseService.getAllExpertises();
            model.addAttribute("expertises", expertises);

            List<Equipment> equipments = equipmentService.getAllEquipments();
            model.addAttribute("equipments", equipments);

            List<AppUser> users = appUserService.getUsersByRole("RESPONSIBLE");
            model.addAttribute("users", users);

            return "investigationEdit";
        }else {
            //no errors, save the entity
            investigationService.saveInvestigation(investigation);
            //redirect to list view
            return "redirect:/investigationList";
        }
    }


    @RequestMapping(value = "investigationDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteInvestigation(Model model, @PathVariable final Long id) {
        log.warn("deleteInvestigation: id={}", id);

        investigationService.deleteInvestigation(id);

        return new RedirectView("/investigationList");
    }

    private String doSearch(Authentication authentication, Model model, int currentPage, int pageSize, InvestigationSearch search) {
        String userName = authentication.getName();
        AppUser loggedUser = appUserService.findByUsername(userName).get();
        AppUser currentUser = null;
        boolean hasManagerRole = loggedUser.getAppRoles().stream().anyMatch(r -> r.getRole().equals("MANAGER"));
        List<AppUser> users = null;
        if (hasManagerRole) {
            users = appUserService.getUsersByRole("RESPONSIBLE");
        } else {
            search.setUserId(loggedUser.getId());
            currentUser = loggedUser;
        }
        Page<Investigation> investigationPage = investigationService.findPaginated(search, new PageRequest(currentPage - 1, pageSize));
        PageWrapper<Investigation> investigationPageWrapper = new PageWrapper<>(investigationPage, currentPage, pageSize, "/investigationList");

        model.addAttribute("pageWrapper", investigationPageWrapper);
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("search", search);

        return "investigationList";
    }

}
