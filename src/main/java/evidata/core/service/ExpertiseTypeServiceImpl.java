package evidata.core.service;

import evidata.core.model.ExpertiseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import evidata.core.model.ExpertiseType;
import evidata.core.repository.ExpertiseTypeRepository;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Service
public class ExpertiseTypeServiceImpl implements ExpertiseTypeService {

    private static final Logger log = LoggerFactory.getLogger(ExpertiseTypeServiceImpl.class);
    
    private ExpertiseTypeRepository expertiseTypeRepository;

    @Override
    public List<ExpertiseType> getAllExpertiseTypes() {
        log.trace("getAllExpertiseTypes --- method entered");

        List<ExpertiseType> expertiseTypes = expertiseTypeRepository.findAll(sortByNameAsc());

        log.trace("getAllExpertiseTypes: expertiseTypes={}", expertiseTypes);

        return expertiseTypes;
    }

    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "type");
    }

    @Override
    @Transactional
    public ExpertiseType saveExpertiseType(ExpertiseType expertiseType) {
        if (expertiseType.getId() == null) {
            log.trace("create new expertiseType type={} ", expertiseType.getType());
        } else {
            log.trace("updateExpertiseType: id={}, type={} ", expertiseType.getId(), expertiseType.getType());
        }
        ExpertiseType savedExpertiseType = getExpertiseTypeRepository().save(expertiseType);
        log.trace("saveExpertiseType: result={}", savedExpertiseType);
        return savedExpertiseType;
    }

    @Override
    public void deleteExpertiseType(Long id) {
        log.trace("deleteExpertiseType: id={}", id);

        expertiseTypeRepository.delete(id);

        log.trace("deleteExpertiseType --- method finished");
    }

    @Override
    public Optional<ExpertiseType> findExpertiseType(Long id) {
        log.trace("findExpertiseType: id={}", id);


        Optional<ExpertiseType> expertiseTypeOptional = expertiseTypeRepository.findById(id);

        log.trace("findExpertiseType: expertiseTypeOptional={}", expertiseTypeOptional);

        return expertiseTypeOptional;
    }

    @Override
    public Optional<ExpertiseType> findByTypeIgnoringCase(String expertiseType) {
        log.trace("findExpertiseType: expertiseTypeName={}", expertiseType);

        Optional<ExpertiseType> expertiseTypeOptional = getExpertiseTypeRepository().findByTypeIgnoringCase(expertiseType);

        log.trace("findExpertiseType: expertiseTypeOptional={}", expertiseTypeOptional);

        return expertiseTypeOptional;
    }

    public ExpertiseTypeRepository getExpertiseTypeRepository() {
        return expertiseTypeRepository;
    }

    @Autowired
    public void setExpertiseTypeRepository(ExpertiseTypeRepository expertiseTypeRepository) {
        this.expertiseTypeRepository = expertiseTypeRepository;
    }

    public Page<ExpertiseType> findPaginated(String name, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<ExpertiseType> page;
        if (StringUtils.isEmpty(name)) {
            page = getExpertiseTypeRepository().findAll(new PageRequest(currentPage, pageSize, sortByNameAsc()));
        } else {
            page = getExpertiseTypeRepository().findByTypeStartingWithIgnoreCase(name, new PageRequest(currentPage, pageSize, sortByNameAsc()));
        }
        return page;
    }
}
