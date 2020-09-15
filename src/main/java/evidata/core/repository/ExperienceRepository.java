package evidata.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import evidata.core.model.Experience;
import java.util.Optional;

/**
 * Created by Nicolle on sept. in 2018
 */
@Repository
public interface ExperienceRepository extends IRepository<Experience, Long> {
    Optional<Experience> findById(Long id);

    Optional<Experience> findByLevelIgnoringCase(String experienceLevel);

    Page<Experience> findByLevelStartingWithIgnoreCase(String level, Pageable pageable);
}
