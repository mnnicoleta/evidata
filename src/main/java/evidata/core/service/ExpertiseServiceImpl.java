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
import evidata.core.domain.ExpertiseSpecificationBuilder;
import evidata.core.dto.ExpertiseSearch;
import evidata.core.model.Expertise;
import evidata.core.repository.ExpertiseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Service
public class ExpertiseServiceImpl implements ExpertiseService {

    private static final Logger log = LoggerFactory.getLogger(ExpertiseServiceImpl.class);

    private ExpertiseRepository expertiseRepository;

    public ExpertiseRepository getExpertiseRepository() {
        return expertiseRepository;
    }

    @Autowired
    public void setExpertiseRepository(ExpertiseRepository expertiseRepository) {
        this.expertiseRepository = expertiseRepository;
    }

    @Override
    public List<Expertise> getAllExpertises() {

        log.trace("getAllExpertises --- method entered");

        List<Expertise> expertise = getExpertiseRepository().findAll(sortByExpertiseNumberAsc());

        log.trace("getAllExpertises: expertises={}", expertise);

        return expertise;
    }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    private Sort sortByExpertiseNumberAsc() {
        return new Sort(Sort.Direction.ASC, "expertiseNumber");
    }

    @Override
    @Transactional
    public Expertise saveExpertise(Expertise expertise) {
        if (expertise.getId() == null) {
            log.trace("create new expertise type={} ", expertise.getExpertiseNumber());
        } else {
            log.trace("updateExpertise: id={}, type={} ", expertise.getId(), expertise.getExpertiseNumber());
        }
        Expertise savedExpertise = getExpertiseRepository().save(expertise);
        log.trace("saveExpertise: result={}", savedExpertise);
        return savedExpertise;
    }

    @Override
    public void deleteExpertise(Long id) {

        log.trace("deleteExpertise: id={}", id);
        if (!getExpertiseRepository().exists(id)) {
            log.warn("No expertise for delete found with id: {}", id);
            return;
        }

        Expertise expertise = getExpertiseRepository().findOne(id);
        expertise.setExpertiseType(null);
        expertise.setDepartment(null);
        expertise.setAppUserList(null);
        expertise.setAttachments(null);
        getExpertiseRepository().save(expertise);

        getExpertiseRepository().delete(id);

        log.trace("deleteExpertise --- method finished");

    }

    @Override
    public Optional<Expertise> findExpertise(Long id) {
        log.trace("findExpertise: id={}", id);

        Optional<Expertise> expertiseOptional = getExpertiseRepository().findById(id);

        log.trace("findExpertise: expertiseOptional={}", expertiseOptional);

        return expertiseOptional;
    }

    @Override
    public Page<Expertise> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Expertise> page = getExpertiseRepository().findAll(new PageRequest(currentPage, pageSize, sortByExpertiseNumberAsc()));
        return page;
    }

    @Override
    public Page<Expertise> findPaginated(ExpertiseSearch expertiseSearch, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Specification<Expertise> specification = ExpertiseSpecificationBuilder.bySearch(expertiseSearch);
        Page<Expertise> page;
        if (specification != null) {
            page = getExpertiseRepository().findAll(specification, new PageRequest(currentPage, pageSize, sortByExpertiseNumberAsc()));
        } else {
            page = getExpertiseRepository().findAll(new PageRequest(currentPage, pageSize, sortByExpertiseNumberAsc()));
        }
        return page;
    }

    private Sort sortByDateAsc() {
        return new Sort(Sort.Direction.ASC, "finishDate");
    }



}
