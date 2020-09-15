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
import evidata.core.model.*;
import evidata.core.service.AppUserService;
import evidata.core.service.DepartmentService;
import evidata.core.service.ExperienceService;
import evidata.core.service.RankService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Controller
public class AppUserController {
    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RankService rankService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/admin/appUserList", method = RequestMethod.GET)
    public String getAppUsers(Model model) {
        log.trace("getAppUsers");
        List<AppUser> appUsers = appUserService.getAllAppUsers();

        log.trace("getAppUsers: appUsers={}", appUsers);

        model.addAttribute("appUsers", appUsers);
        model.addAttribute("message", "Success");
        return "admin/appUserList";
    }

    @RequestMapping(value = {"admin/appUserEdit", "admin/appUserEdit/{id}"}, method = RequestMethod.GET)
    public String appUserEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getAppUser: id={}", id);
        AppUser appUser;
        if (id != null) {
            Optional<AppUser> appUserOptional = appUserService.findAppUserById(id);

            appUser = appUserOptional.get();
        } else {
            appUser = new AppUser();
        }
        appUser.setPassword("");

        model.addAttribute("appUser", appUser);

        List<Rank> ranks=rankService.getAllRanks();
        List<Department> departments=departmentService.getAllDepartments();
        List<AppRole> allRoles = appUserService.getAllAppRoles();
        List<Experience> experiences=experienceService.getAllExperiences();
        model.addAttribute("ranks", ranks);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("experiences", experiences);
        model.addAttribute("departments", departments);

        return "admin/appUserEdit";

    }


    @RequestMapping(value = "admin/appUserEdit", method = RequestMethod.POST)
    public RedirectView appUserSave(@Valid AppUser appUser, BindingResult bindingResult) {
        log.trace("saveAppUser: {}", appUser);

        appUserService.updateUser(appUser);

        return new RedirectView("/admin/appUserList");
    }


    @RequestMapping(value = "admin/appUserDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteAppUser(Model model, @PathVariable final Long id) {
        log.info("deleteAppUser: id={}", id);


        appUserService.deleteAppUser(id);
        return new RedirectView("/admin/appUserList");
    }
}


