package evidata.core.service;

import evidata.core.model.Department;
import evidata.core.model.ExpertiseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
public interface ExpertiseTypeService {
    /**
     * get all existing expertiseTypes
     *
     * @return a list of expertiseTypes
     */
    List<ExpertiseType> getAllExpertiseTypes();

    /**
     * create or update an expertiseType
     *
     * @param expertiseType the new or updated entity
     * @return the new or updated entity
     */
    ExpertiseType saveExpertiseType(ExpertiseType expertiseType);

    /**
     * delete an expertiseType by a given id
     *
     * @param id identifier for the expertiseType to delete
     */
    void deleteExpertiseType(Long id);

    /**
     * get an expertiseType by id
     *
     * @param id identifier of the expertiseType
     * @return the optional of expertiseType with the given id
     */
    Optional<ExpertiseType> findExpertiseType(Long id);

    /**
     * get the expertiseType with the type given ignoring case
     * @param expertiseTypeType
     * @return
     */
    Optional<ExpertiseType> findByTypeIgnoringCase(String expertiseTypeType);

    Page<ExpertiseType> findPaginated(String type, Pageable pageable);
}
