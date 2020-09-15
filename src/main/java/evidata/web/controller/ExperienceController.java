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
import evidata.core.model.Experience;
import evidata.core.service.ExperienceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 */
@Controller
public class ExperienceController {
    private static final Logger log = LoggerFactory.getLogger(ExperienceController.class);

    @Autowired
    private ExperienceService experienceService;


    @RequestMapping(value = "/admin/experienceList", method = RequestMethod.GET)
    public String getExperiences(Model model) {

        List<Experience> experiences = experienceService.getAllExperiences();

        model.addAttribute("experiences", experiences);
        return "admin/experienceList";
    }

    @RequestMapping(value = {"admin/experienceEdit", "admin/experienceEdit/{id}"}, method = RequestMethod.GET)
    public String experienceEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getExperience: id={}", id);
        Experience experience;
        if (id != null) {
            Optional<Experience> experienceOptional = experienceService.findExperience(id);

            experience = experienceOptional.get();
        } else {
            experience = new Experience();
        }

        model.addAttribute("experience", experience);
        return "admin/experienceEdit";

    }


    @RequestMapping(value = "admin/experienceEdit", method = RequestMethod.POST)
    public String experienceSave(@Valid Experience experience, BindingResult bindingResult) {
        log.trace("saveExperience: {}", experience);

        Optional<Experience> experienceExists = experienceService.findByLevelIgnoringCase(experience.getLevel());
        if (experienceExists.isPresent()) {
            bindingResult
                    .rejectValue("level", "error.experience",
                            "There is already a experience registered with the type provided");
        }

        if (bindingResult.hasErrors()) {
            return "admin/experienceEdit";
        }else {
            //no errors, save the entity
            experienceService.saveExperience(experience);
            //redirect to list view
            return "redirect:/admin/experienceList";
        }
    }


    @RequestMapping(value = "admin/experienceDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteExperience(Model model, @PathVariable final Long id) {
        log.info("deleteExperience: id={}", id);

        experienceService.deleteExperience(id);

        return new RedirectView("/admin/experienceList");
    }
}
