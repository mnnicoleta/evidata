package evidata.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import evidata.core.domain.InvestigationSpecificationBuilder;
import evidata.core.dto.InvestigationSearch;
import evidata.core.model.Investigation;
import evidata.core.repository.InvestigationRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Service
public class InvestigationServiceImpl implements InvestigationService {

    private static final Logger log = LoggerFactory.getLogger(InvestigationServiceImpl.class);

    InvestigationRepository investigationRepository;

    @Override
    @Transactional
    public Investigation saveInvestigation(Investigation investigation) {
        if (investigation.getId() == null) {
            log.trace("create new investigation");
        } else {
            log.trace("updateInvestigation: id={} ", investigation.getId());
        }
        Investigation savedInvestigation = getInvestigationRepository().save(investigation);
        log.trace("saveInvestigation: result={}", savedInvestigation);
        return savedInvestigation;
    }

    @Override
    @Transactional
    public void deleteInvestigation(Long id) {
        log.trace("deleteInvestigation: id={}", id);
        if (!getInvestigationRepository().exists(id)) {
            log.warn("No investigation for delete found with id: {}", id);
            return;
        }

        Investigation investigation = getInvestigationRepository().findOne(id);
        investigation.setUsers(null);
        investigation.setEquipments(null);
        getInvestigationRepository().save(investigation);

        getInvestigationRepository().delete(id);

        log.trace("deleteInvestigation --- method finished");
    }

    @Override
    public Optional<Investigation> findInvestigation(Long id) {
        log.trace("findInvestigations: id={}", id);

        Optional<Investigation> investigationOptional = getInvestigationRepository().findById(id);

        log.trace("findInvestigations: investigationOptional={}", investigationOptional);

        return investigationOptional;
    }

    @Override
    public Page<Investigation> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Investigation> page = getInvestigationRepository().findAll(new PageRequest(currentPage, pageSize, sortByDateAsc()));
        return page;

    }

    @Override
    public Page<Investigation> findPaginated(InvestigationSearch investigationSearch, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Specification<Investigation> specification = InvestigationSpecificationBuilder.bySearch(investigationSearch);
        Page<Investigation> page;
        if (specification != null) {
            page = getInvestigationRepository().findAll(specification, new PageRequest(currentPage, pageSize, sortByDateAsc()));
        } else {
            page = getInvestigationRepository().findAll(new PageRequest(currentPage, pageSize, sortByDateAsc()));
        }

        return page;
    }

    @Override
    public List<Investigation> getInvestigationsByExpertiseId(Long expertiseID) {
        List<Investigation> investigations = getInvestigationRepository().findByExpertise_Id(expertiseID);
        return investigations;
    }

    private Sort sortByDateAsc() {
        return new Sort(Sort.Direction.ASC, "date");
    }

    public InvestigationRepository getInvestigationRepository() {
        return investigationRepository;
    }

    @Autowired
    public void setInvestigationRepository(InvestigationRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }
}
