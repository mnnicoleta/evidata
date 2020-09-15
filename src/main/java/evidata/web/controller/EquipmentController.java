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
import evidata.core.model.Equipment;
import evidata.core.service.EquipmentService;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 01.09.2018
 */
@Controller
public class EquipmentController {
    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    private EquipmentService equipmentService;


    @RequestMapping(value = "/admin/equipmentList", method = RequestMethod.GET)
    public String getEquipments(Model model) {
        List<Equipment> equipments = equipmentService.getAllEquipments();

        model.addAttribute("equipments", equipments);
        return "admin/equipmentList";
    }

    @RequestMapping(value = {"admin/equipmentEdit", "admin/equipmentEdit/{id}"}, method = RequestMethod.GET)
    public String equipmentEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getEquipment: id={}", id);
        Equipment equipment;
        if (id != null) {
            Optional<Equipment> equipmentOptional = equipmentService.findEquipment(id);

            equipment = equipmentOptional.get();
        } else {
            equipment = new Equipment();
        }

        model.addAttribute("equipment", equipment);
        return "admin/equipmentEdit";

    }


    @RequestMapping(value = "admin/equipmentEdit", method = RequestMethod.POST)
    public String equipmentSave(@Valid Equipment equipment, BindingResult bindingResult) {
        log.trace("saveEquipment: {}", equipment);

        Optional<Equipment> equipmentExists = equipmentService.findyByNameIgnoringCase(equipment.getName());
        if (equipmentExists.isPresent()) {
            bindingResult
                    .rejectValue("name", "error.equipment",
                            "There is already a equipment registered with the name provided");
        }
//        if(equipment.getName().length()>40 && equipment.getName().length()<3){
//            bindingResult
//                    .rejectValue("name", "error.equipment",
//                            "Name must be between 5 and 40 characters");
//        }
        if (bindingResult.hasErrors()) {
            return "admin/equipmentEdit";
        }else {
            //no errors, save the entity
            equipmentService.saveEquipment(equipment);
            //redirect to list view
            return "redirect:/admin/equipmentList";
        }
    }


    @RequestMapping(value = "admin/equipmentDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteEquipment(Model model, @PathVariable final Long id) {
        log.info("deleteEquipment: id={}", id);

        equipmentService.deleteEquipment(id);

        return new RedirectView("/admin/equipmentList");
    }
}
