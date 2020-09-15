package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.model.Equipment;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
public interface EquipmentService {
    /**
     * Get a list will all available equipments
     *
     * @return the list of equipments
     */
    List<Equipment> getAllEquipments();

    /**
     * Create or update a equipment
     *
     * @param equipment saved entity
     * @return the new created equipment or updated equipment
     */
    Equipment saveEquipment(Equipment equipment);

    /**
     * Delete the equipment by given ID
     *
     * @param id identifier for deleted equipment
     */
    void deleteEquipment(Long id);

    /**
     * Get the equipment with given id
     *
     * @param id identifier of the equipment
     * @return the optional equipment
     */
    Optional<Equipment> findEquipment(Long id);

    /**
     * get de equipment with the given name ignoring case
     * @param equipmentName identifier of the equipment
     * @return the optional equipment
     */
    Optional<Equipment> findyByNameIgnoringCase(String equipmentName);

    Page<Equipment> findPaginated(String name, Pageable pageable);
}
