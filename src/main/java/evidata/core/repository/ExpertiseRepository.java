package evidata.core.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import evidata.core.model.Expertise;

import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Repository
public interface ExpertiseRepository extends IRepository<Expertise, Long>, JpaSpecificationExecutor<Expertise> {
    Optional<Expertise> findById(Long id);
}
