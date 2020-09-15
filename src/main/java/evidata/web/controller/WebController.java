package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import evidata.core.model.AppUser;
import evidata.core.service.AppUserService;
import evidata.core.service.DepartmentService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Controller
public class WebController {
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @RequestMapping(value = {"/home"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = {"/about"})
    public String about() {
        return "about";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String redirect() {
        return "redirect:home";
    }

    @RequestMapping(value = "/403")
    public String Error403() {
        return "403";
    }

    @RequestMapping(value = "/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        AppUser appUser = new AppUser();
        modelAndView.addObject("appUser", appUser);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid AppUser appUser, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<AppUser> appUserExists = appUserService.findByUsername(appUser.getUsername());
        if (appUserExists.isPresent()) {
            bindingResult
                    .rejectValue("username", "error.appUser",
                            "There is already an appUser registered with the username provided");
            log.warn("There is already an appUser registered with the username provided:'{}'",appUser.getUsername());

        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            appUserService.createNewUser(appUser);
            modelAndView.addObject("successMessage", "AppUser has been registered successfully");
            modelAndView.addObject("user", new AppUser());
            modelAndView.setViewName("registration");
            log.info("User '{}' successfully registered!",appUser.getUsername());
        }
        return modelAndView;
    }
}
