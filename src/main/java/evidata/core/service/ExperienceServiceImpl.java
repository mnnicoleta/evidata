package evidata.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import evidata.core.model.Experience;
import evidata.core.repository.ExperienceRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 */
@Service
public class ExperienceServiceImpl implements ExperienceService{
    private static final Logger log = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private ExperienceRepository experienceRepository;

    @Override
    public List<Experience> getAllExperiences() {
        log.trace("getAllExperiences --- method entered");

        List<Experience> Experiences = getExperienceRepository().findAll(sortByLevelAsc());

        log.trace("getAllExperiences: Experiences={}", Experiences);

        return Experiences;
    }

    private Sort sortByLevelAsc() {
        return new Sort(Sort.Direction.ASC, "level");
    }

    @Override
    @Transactional
    public Experience saveExperience(Experience Experience) {
        if (Experience.getId() == null) {
            log.trace("create new Experience level={} ", Experience.getLevel());
        } else {
            log.trace("updateExperience: id={}, level={} ", Experience.getId(), Experience.getLevel());
        }
        Experience savedExperience = getExperienceRepository().save(Experience);
        log.trace("saveExperience: result={}", savedExperience);
        return savedExperience;
    }

    @Override
    public void deleteExperience(Long id) {
        log.trace("deleteExperience: id={}", id);
        getExperienceRepository().delete(id);
        log.trace("deleteExperience --- method finished");
    }

    @Override
    public Optional<Experience> findExperience(Long id) {
        log.trace("findExperience: id={}", id);

        Optional<Experience> ExperienceOptional = getExperienceRepository().findById(id);

        log.trace("findExperience: ExperienceOptional={}", ExperienceOptional);

        return ExperienceOptional;
    }

    @Override
    public Optional<Experience> findByLevelIgnoringCase(String experienceLevel) {
        log.trace("findExperience: experienceName={}", experienceLevel);

        Optional<Experience> experienceOptional = getExperienceRepository().findByLevelIgnoringCase(experienceLevel);

        log.trace("findExperience: experienceOptional={}", experienceOptional);

        return experienceOptional;
    }

    public Page<Experience> findPaginated(String level, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Experience> page;
        if (StringUtils.isEmpty(level)) {
            page = getExperienceRepository().findAll(new PageRequest(currentPage, pageSize, sortByLevelAsc()));
        } else {
            page = getExperienceRepository().findByLevelStartingWithIgnoreCase(level, new PageRequest(currentPage, pageSize, sortByLevelAsc()));
        }
        return page;
    }

    public ExperienceRepository getExperienceRepository() {
        return experienceRepository;
    }

    @Autowired
    public void setExperienceRepository(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }
}
