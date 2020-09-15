package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.dto.InvestigationSearch;
import evidata.core.model.Investigation;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
public interface InvestigationService {

    /**
     * Create or update a investigation
     *
     * @param investigation saved entity„
     * @return the new created investigation or updated investigation
     */
    Investigation saveInvestigation(Investigation investigation);

    /**
     * Delete the investigation by given ID
     *
     * @param id identifier for deleted investigation
     */
    void deleteInvestigation(Long id);

    /**
     * Get the investigation with given id
     *
     * @param id identifier of the investigation
     * @return the optional investigation
     */
    Optional<Investigation> findInvestigation(Long id);

    Page<Investigation> findPaginated(Pageable pageable);

    Page<Investigation> findPaginated(InvestigationSearch investigationSearch, Pageable pageable);

    List<Investigation> getInvestigationsByExpertiseId(Long expertiseID);
}
