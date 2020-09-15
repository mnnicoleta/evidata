package evidata.core.repository;

import evidata.core.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import evidata.core.model.ExpertiseType;

import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Repository
public interface ExpertiseTypeRepository extends IRepository<ExpertiseType, Long> {
    Optional<ExpertiseType> findById(Long id);

    Optional<ExpertiseType> findByTypeIgnoringCase(String expertiseType);

    Page<ExpertiseType> findByTypeStartingWithIgnoreCase(String type, Pageable pageable);
}
