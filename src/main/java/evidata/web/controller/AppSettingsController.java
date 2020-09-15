package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import evidata.core.model.AppSettings;
import evidata.core.service.AppSettingsService;

import javax.validation.Valid;
import java.io.File;


@Controller
public class AppSettingsController {
    private static final Logger log = LoggerFactory.getLogger(AppSettingsController.class);

    @Autowired
    private AppSettingsService appSettingsService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "admin/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        log.trace("settings");
        ModelAndView modelAndView = new ModelAndView();
        AppSettings appSettings = appSettingsService.getAppSettings();

        modelAndView.addObject("appSettings", appSettings);
        modelAndView.setViewName("admin/settings");
        return modelAndView;
    }


    @RequestMapping(value = "admin/settings", method = RequestMethod.POST)
    public ModelAndView saveSettings(@Valid AppSettings appSettings, BindingResult bindingResult) {
        log.trace("saveSettings: {}", appSettings);
        ModelAndView modelAndView=new ModelAndView();
        String attachmentDir = appSettings.getAttachmentDir();
        if(StringUtils.isEmpty(attachmentDir)) {
            bindingResult
                    .rejectValue("attachmentDir", "error.appSettings",
                            "The attachmentDir is required!");
            log.warn("The attachmentDir is required!");
        }else{
            File file=new File(attachmentDir);
            final String msg2 = messageSource.getMessage("setting.message.failed", null,LocaleContextHolder.getLocale());
            if(!file.exists() || !file.isDirectory()){
                bindingResult
                        .rejectValue("attachmentDir", "error.appSettings",
                                msg2);
                log.warn("Invalid file type '{}'!",attachmentDir);
            }
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("appSettings", appSettings);
        }else {
            appSettings = appSettingsService.saveAppSettings(appSettings);
            final String msg = messageSource.getMessage("setting.message.success", null,LocaleContextHolder.getLocale());
            modelAndView.addObject("successMessage", msg);
            modelAndView.addObject("appSettings", appSettings);
            log.info("AttachmentDir location change to:{}",attachmentDir);
        }
        modelAndView.setViewName("admin/settings");
        return modelAndView;
    }

}
