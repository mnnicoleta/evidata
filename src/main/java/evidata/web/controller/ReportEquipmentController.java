package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import evidata.core.dto.InvestigationSearch;
import evidata.core.model.Equipment;
import evidata.core.model.Investigation;
import evidata.core.service.EquipmentService;
import evidata.core.service.InvestigationService;
import evidata.web.PageWrapper;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Controller
public class ReportEquipmentController {
    private static final Logger log = LoggerFactory.getLogger(ReportEquipmentController.class);

    @Autowired
    InvestigationService investigationService;


    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "report/equipment", method = RequestMethod.GET)
    public String equipmentInvestigations(HttpSession session, Model model,
                                          @RequestParam("id") Optional<Long> id,
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


        InvestigationSearch search = (InvestigationSearch) session.getAttribute("ReportEquipmentSearch");
        if (search == null) {
            search = new InvestigationSearch();
        }

        return doSearch(model, id, currentPage, pageSize, search);

    }

    @RequestMapping(value = "report/equipment", method = RequestMethod.POST)
    public String searchInvestigations(HttpSession session, Model model,
                                       InvestigationSearch search) {

        int currentPage = 1;
        int pageSize = 5;


        session.setAttribute("ReportEquipmentSearch", search);

        return doSearch(model, Optional.empty(), currentPage, pageSize, search);
    }

    private String doSearch(Model model,  Optional<Long> equipmentID, int currentPage, int pageSize, InvestigationSearch search) {

        if(equipmentID.isPresent()) {
            search.setEquipmentId(equipmentID.get());
        }

        List<Equipment> equipments = equipmentService.getAllEquipments();
        model.addAttribute("equipments", equipments);

        Long searchEquipmentID=search.getEquipmentId();
        if(searchEquipmentID==null && !CollectionUtils.isEmpty(equipments)){
            search.setEquipmentId(equipments.get(0).getId());
        }

        Page<Investigation> investigationPage = investigationService.findPaginated(search, new PageRequest(currentPage - 1, pageSize));
        PageWrapper<Investigation> investigationPageWrapper = new PageWrapper<>(investigationPage, currentPage, pageSize, "/report/equipment");

        model.addAttribute("pageWrapper", investigationPageWrapper);

        model.addAttribute("search", search);

        return "report/reportEquipment";
    }
}

