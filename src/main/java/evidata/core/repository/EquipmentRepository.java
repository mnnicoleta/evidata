package evidata.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import evidata.core.model.Equipment;

import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Repository
public interface EquipmentRepository extends IRepository<Equipment, Long> {
    Optional<Equipment> findById(Long id);

    Optional<Equipment> findByNameIgnoringCase(String equipmentName);

    Page<Equipment> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}