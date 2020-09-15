package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import evidata.core.dto.ExpertiseSearch;
import evidata.core.model.*;
import evidata.core.service.*;
import evidata.web.PageWrapper;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Controller
public class ExpertiseController {
    private static final Logger log = LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ExpertiseTypeService expertiseTypeService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AppSettingsService appSettingsService;

    @Autowired
    InvestigationService investigationService;

    @RequestMapping(value = "/expertiseList", method = RequestMethod.GET)
    public String getExpertises(HttpSession session, Authentication authentication,
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

        ExpertiseSearch search = (ExpertiseSearch) session.getAttribute("ExpertiseSearch");
        if (search == null) {
            search = new ExpertiseSearch();
        }

        return doSearch(authentication, model, currentPage, pageSize, search);

    }

    @RequestMapping(value = "/expertiseList", method = RequestMethod.POST)
    public String searchExpertises(HttpSession session, Model model, Authentication authentication, ExpertiseSearch search) {

        int currentPage = 1;
        int pageSize = 5;

        session.setAttribute("ExpertiseSearch", search);

        return doSearch(authentication, model, currentPage, pageSize, search);
    }

    @RequestMapping(value = {"expertiseEdit", "expertiseEdit/{id}"}, method = RequestMethod.GET)
    public String expertiseEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getExpertise: id={}", id);
        Expertise expertise;
        if (id != null) {
            Optional<Expertise> expertiseOptional = expertiseService.findExpertise(id);

            expertise = expertiseOptional.get();
        } else {
            expertise = new Expertise();
        }
        List<AppUser> appUsers = appUserService.getUsersByRole("RESPONSIBLE");
        List<Department> departments = departmentService.getAllDepartments();
        List<ExpertiseType> expertiseTypes = expertiseTypeService.getAllExpertiseTypes();
        List<Equipment> equipments = equipmentService.getAllEquipments();

        log.trace("getDepartments: departments={}", departments);

        log.trace("getExpertiseTypes: expertiseTypes={}", expertiseTypes);

        log.trace("getAllAppUsers: appUsers={}", appUsers);

        log.trace("getEquipments: equipments={}", equipments);

        model.addAttribute("equipments", equipments);
        model.addAttribute("appUsers", appUsers);
        model.addAttribute("departments", departments);
        model.addAttribute("expertiseTypes", expertiseTypes);

        model.addAttribute("expertise", expertise);
        return "expertiseEdit";

    }

    @RequestMapping(value = "expertiseDetail/{id}", method = RequestMethod.GET)
    public String expertiseDetail(Model model, @PathVariable(required = true, name = "id") Long id) {
        log.trace("getExpertise: id={}", id);
        Expertise expertise;
        if (id != null) {
            Optional<Expertise> expertiseOptional = expertiseService.findExpertise(id);

            expertise = expertiseOptional.get();
        } else {
            expertise = new Expertise();
        }

        model.addAttribute("expertise", expertise);

        List<Investigation> investigations=investigationService.getInvestigationsByExpertiseId(id);
        model.addAttribute("investigations", investigations);

        return "expertiseDetail";
    }


    @RequestMapping(value = "expertiseEdit", method = RequestMethod.POST)
    public String expertiseSave(Model model, @Valid Expertise expertise, BindingResult bindingResult) {
        log.trace("saveExpertise: {}", expertise);
      
        if (expertise.getExpertiseNumber().length() > 40 && expertise.getExpertiseNumber().length() < 5) {
            bindingResult
                    .rejectValue("expertiseNumber", "error.expertiseType",
                            "Expertise number must be between 5 and 40 characters");
        }
        if (expertise.getFileNumber().length() > 40 && expertise.getFileNumber().length() < 5) {
            bindingResult
                    .rejectValue("File Number", "error.expertiseType",
                            "File number must be between 5 and 40 characters");
        }
        if (expertise.getRequestedBy().length() > 40 && expertise.getRequestedBy().length() < 5) {
            bindingResult
                    .rejectValue("requestedByr", "error.expertiseType",
                            "Solicitor must be between 5 and 40 characters");
        }
        if (bindingResult.hasErrors()) {

            List<AppUser> appUsers = appUserService.getUsersByRole("RESPONSIBLE");
            List<Department> departments = departmentService.getAllDepartments();
            List<ExpertiseType> expertiseTypes = expertiseTypeService.getAllExpertiseTypes();
            List<Equipment> equipments = equipmentService.getAllEquipments();

            model.addAttribute("equipments", equipments);
            model.addAttribute("appUsers", appUsers);
            model.addAttribute("departments", departments);
            model.addAttribute("expertiseTypes", expertiseTypes);

            return "expertiseEdit";
        }else {
            //no errors, save the entity
            expertiseService.saveExpertise(expertise);
            //redirect to list view
            return "redirect:/expertiseList";
        }
        
    }


    @RequestMapping(value = "expertiseDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteExpertise(Model model, @PathVariable final Long id) {
        log.warn("deleteExpertise: id={}", id);

        expertiseService.deleteExpertise(id);

        return new RedirectView("/expertiseList");
    }


    @RequestMapping(value = "expertise/uploadFile", method = RequestMethod.POST)
    public RedirectView submit(@RequestParam MultipartFile file, Authentication authentication, @RequestParam Long expertiseId, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        if(file.getSize()==0){
            log.warn("No file selected!");
            return new RedirectView("/expertiseDetail/"+expertiseId);
        }


        Attachment attachment=new Attachment();

        Optional<Expertise> expertise=expertiseService.findExpertise(expertiseId);
        attachment.setExpertise(expertise.get());

        String userName = authentication.getName();
        AppUser loggedUser=appUserService.findByUsername(userName).get();
        attachment.setAppUser(loggedUser);

        attachment.setFileName(file.getOriginalFilename());
        attachment.setSize(file.getSize());

        attachment= attachmentService.saveAttachment(attachment);

        String fileNameOnDisk=getFullFileName(attachment);
        try {
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(new File(fileNameOnDisk)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RedirectView("/expertiseDetail/"+expertiseId);
    }

    protected String getFullFileName(Attachment attachment) {
        String dir = appSettingsService.getAppSettings().getAttachmentDir() +File.separator+ attachment.getExpertise().getId() ;
        File dirFile=new File(dir);
        dirFile.mkdirs();
        String fileName = attachment.getId() + "_" + attachment.getFileName();
        return dir + File.separator + fileName;
    }

    private String doSearch(Authentication authentication, Model model, int currentPage, int pageSize, ExpertiseSearch search) {
        List<AppUser> users = appUserService.getUsersByRole("RESPONSIBLE");

        Page<Expertise> expertisePage = expertiseService.findPaginated(search, new PageRequest(currentPage - 1, pageSize));
        PageWrapper<Expertise> expertisePageWrapper = new PageWrapper<>(expertisePage, currentPage, pageSize, "/expertiseList");

        model.addAttribute("pageWrapper", expertisePageWrapper);
        model.addAttribute("users", users);
        List<ExpertiseType> expertiseTypes=expertiseTypeService.getAllExpertiseTypes();
        model.addAttribute("expertiseTypes",expertiseTypes);
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        model.addAttribute("search", search);

        return "expertiseList";
    }
}
