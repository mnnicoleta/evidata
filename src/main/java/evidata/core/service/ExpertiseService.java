package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.dto.ExpertiseSearch;
import evidata.core.model.Expertise;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
public interface ExpertiseService {
    /**
     * get a list with all expertises
     *
     * @return a list of expertises
     */
    List<Expertise> getAllExpertises();

    /**
     * create or update an expertise
     *
     * @param expertise saved entity
     * @return the new created or updated expertise
     */
    Expertise saveExpertise(Expertise expertise);

    /**
     * delete an expertise by given id
     *
     * @param id of the expertise to delete
     */
    void deleteExpertise(Long id);

    /**
     * get the expertise with the given id
     *
     * @param id identifier of the expertise
     * @return the optional of expertise
     */
    Optional<Expertise> findExpertise(Long id);

    Page<Expertise> findPaginated(ExpertiseSearch expertise, Pageable pageable);

    Page<Expertise> findPaginated(Pageable pageable);

}
