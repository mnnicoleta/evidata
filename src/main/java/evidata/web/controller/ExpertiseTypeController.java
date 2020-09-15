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
import evidata.core.model.ExpertiseType;
import evidata.core.service.ExpertiseTypeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Controller
public class ExpertiseTypeController {
    private static final Logger log = LoggerFactory.getLogger(ExpertiseTypeController.class);

    @Autowired
    private ExpertiseTypeService expertiseTypeService;

    @RequestMapping(value = "/admin/expertiseTypeList", method = RequestMethod.GET)
    public String getExpertiseTypes(Model model) {
        log.trace("getExpertiseTypes");
        List<ExpertiseType> expertiseTypes = expertiseTypeService.getAllExpertiseTypes();

        log.trace("getExpertiseTypes: expertiseTypes={}", expertiseTypes);

        model.addAttribute("expertiseTypes", expertiseTypes);
        model.addAttribute("message", "Success");
        return "admin/expertiseTypeList";

    }

    @RequestMapping(value = {"admin/expertiseTypeEdit", "admin/expertiseTypeEdit/{id}"}, method = RequestMethod.GET)
    public String expertiseTypeEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getExpertiseType: id={}", id);
        ExpertiseType expertiseType;
        if (id != null) {
            Optional<ExpertiseType> expertiseTypeOptional = expertiseTypeService.findExpertiseType(id);

            expertiseType = expertiseTypeOptional.get();
        } else {
            expertiseType = new ExpertiseType();
        }

        model.addAttribute("expertiseType", expertiseType);
        return "admin/expertiseTypeEdit";

    }


    @RequestMapping(value = "admin/expertiseTypeEdit", method = RequestMethod.POST)
    public String expertiseTypeSave(@Valid ExpertiseType expertiseType, BindingResult bindingResult) {
        log.trace("saveExpertiseType: {}", expertiseType);

        Optional<ExpertiseType> equipmentExists = expertiseTypeService.findByTypeIgnoringCase(expertiseType.getType());
        if (equipmentExists.isPresent()) {
            bindingResult
                    .rejectValue("type", "error.expertiseType",
                            "There is already an expertiseType registered with the type provided");
        }

        if (bindingResult.hasErrors()) {
            return "admin/expertiseTypeEdit";
        } else {
            //no errors, save the entity
            expertiseTypeService.saveExpertiseType(expertiseType);
            //redirect to list view
            return "redirect:/admin/expertiseTypeList";
        }
    }


    @RequestMapping(value = "admin/expertiseTypeDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteExpertiseType(Model model, @PathVariable final Long id) {
        log.info("deleteExpertiseType: id={}", id);

        expertiseTypeService.deleteExpertiseType(id);

        return new RedirectView("/admin/expertiseTypeList");
    }
}
