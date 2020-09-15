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
import evidata.core.model.Equipment;
import evidata.core.repository.EquipmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {
    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> getAllEquipments() {
        log.trace("getAllEquipments --- method entered");

        List<Equipment> equipments = getEquipmentRepository().findAll(sortByNameAsc());

        log.trace("getAllEquipments: equipments={}", equipments);

        return equipments;
    }

    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @Override
    @Transactional
    public Equipment saveEquipment(Equipment equipment) {
        if (equipment.getId() == null) {
            log.trace("create new equipment type={} ", equipment.getName());
        } else {
            log.trace("updateEquipment: id={}, type={} ", equipment.getId(), equipment.getName());
        }
        Equipment savedEquipment = getEquipmentRepository().save(equipment);
        log.trace("saveEquipment: result={}", savedEquipment);
        return savedEquipment;
    }

    @Override
    public void deleteEquipment(Long id) {
        log.trace("deleteEquipment: id={}", id);
        getEquipmentRepository().delete(id);
        log.trace("deleteEquipment --- method finished");
    }

    @Override
    public Optional<Equipment> findEquipment(Long id) {
        log.trace("findEquipment: id={}", id);

        Optional<Equipment> equipmentOptional = getEquipmentRepository().findById(id);

        log.trace("findEquipment: equipmentOptional={}", equipmentOptional);

        return equipmentOptional;
    }

    @Override
    public Optional<Equipment> findyByNameIgnoringCase(String equipmentName) {
        log.trace("findEquipment: equipmentName={}", equipmentName);

        Optional<Equipment> equipmentOptional = getEquipmentRepository().findByNameIgnoringCase(equipmentName);

        log.trace("findEquipment: equipmentOptional={}", equipmentOptional);

        return equipmentOptional;
    }

    public Page<Equipment> findPaginated(String name, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Equipment> page;
        if (StringUtils.isEmpty(name)) {
            page = getEquipmentRepository().findAll(new PageRequest(currentPage, pageSize, sortByNameAsc()));
        } else {
            page = getEquipmentRepository().findByNameStartingWithIgnoreCase(name, new PageRequest(currentPage, pageSize, sortByNameAsc()));
        }

        return page;
    }

    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    @Autowired
    public void setEquipmentRepository(EquipmentRepository getEquipmentRepository) {
        this.equipmentRepository = getEquipmentRepository;
    }
    
}
