package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.model.Rank;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 */
public interface RankService {
    /**
     * Get a list will all available ranks
     *
     * @return the list of ranks
     */
    List<Rank> getAllRanks();

    /**
     * Create or update a rank
     *
     * @param rank saved entity
     * @return the new created rank or updated rank
     */
    Rank saveRank(Rank rank);

    /**
     * Delete the rank by given ID
     *
     * @param id identifier for deleted rank
     */
    void deleteRank(Long id);

    /**
     * Get the rank with given id
     *
     * @param id identifier of the rank
     * @return the optional rank
     */
    Optional<Rank> findRank(Long id);

    /**
     * get the rank with the given name ignoring case
     * @param rankName identifier of the rank
     * @return the optional rank
     */
    Optional<Rank> findByNameIgnoringCase(String rankName);

    Page<Rank> findPaginated(String name, Pageable pageable);
}
