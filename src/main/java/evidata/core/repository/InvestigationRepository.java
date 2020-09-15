package evidata.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import evidata.core.model.Investigation;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Repository
public interface InvestigationRepository extends IRepository<Investigation, Long>, JpaSpecificationExecutor<Investigation> {
    Optional<Investigation> findById(Long id);

    Page<Investigation> findByEquipments_Id(Long equipmentId, Pageable pageable);

    List<Investigation> findByExpertise_Id(Long expertiseId);

}
