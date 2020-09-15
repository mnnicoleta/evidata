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
import evidata.core.model.Department;
import evidata.core.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Controller
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/admin/departmentList", method = RequestMethod.GET)
    public String getDepartments(Model model) {

        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("departments", departments);
        return "admin/departmentList";
    }

    @RequestMapping(value = {"admin/departmentEdit", "admin/departmentEdit/{id}"}, method = RequestMethod.GET)
    public String departmentEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getDepartment: id={}", id);
        Department department;
        if (id != null) {
            Optional<Department> departmentOptional = departmentService.findDepartment(id);

            department = departmentOptional.get();
        } else {
            department = new Department();
        }

        model.addAttribute("department", department);
        return "admin/departmentEdit";

    }

    @RequestMapping(value = "admin/departmentEdit", method = RequestMethod.POST)
    public String departmentSave(@Valid Department department, BindingResult bindingResult) {
        log.trace("saveDepartment: {}", department);


        Optional<Department> departmentExists = departmentService.findDepartmentByNameIgnoringCase(department.getName());
        if (departmentExists.isPresent()) {
            bindingResult
                    .rejectValue("name", "error.department",
                            "There is already a department registered with the type provided");
        }
        if(department.getName().length()>40 && department.getName().length()<3){
            bindingResult
                    .rejectValue("name", "error.department",
                            "Name must be between 3 and 40 characters");
        }
        if (bindingResult.hasErrors()) {
            return "admin/departmentEdit";
        }else {
            //no errors, save the entity
            departmentService.saveDepartment(department);
            //redirect to list view
            return "redirect:/admin/departmentList";
        }
    }

    @RequestMapping(value = "admin/departmentDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteDepartment(Model model, @PathVariable final Long id) {
        log.info("deleteDepartment: id={}", id);

        departmentService.deleteDepartment(id);

        return new RedirectView("/admin/departmentList");
    }
}
