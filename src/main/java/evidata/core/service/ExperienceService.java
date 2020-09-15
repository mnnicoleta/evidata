package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.model.Experience;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 *
 */
public interface ExperienceService {
    /**
     * Get a list will all available experiences
     *
     * @return the list of experiences
     */
    List<Experience> getAllExperiences();

    /**
     * Create or update a experience
     *
     * @param experience saved entity
     * @return the new created experience or updated experience
     */
    Experience saveExperience(Experience experience);

    /**
     * Delete the experience by given ID
     *
     * @param id identifier for deleted experience
     */
    void deleteExperience(Long id);

    /**
     * Get the experience with given id
     *
     * @param id identifier of the experience
     * @return the optional experience
     */
    Optional<Experience> findExperience(Long id);

    /**
     * get the experienceLevel with the name given ignoring case
     * @param experienceLevel identifier of the experience
     * @return the optional experience
     */
    Optional<Experience> findByLevelIgnoringCase( String experienceLevel);

    Page<Experience> findPaginated(String level, Pageable pageable);
}
