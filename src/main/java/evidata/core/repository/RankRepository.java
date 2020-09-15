package evidata.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.model.Rank;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Nicolle on sept. in 2018
 */
@Repository
public interface RankRepository extends IRepository<Rank, Long> {
    Optional<Rank> findById(Long id);

    Optional<Rank> findByNameIgnoringCase(String rankName);

    Page<Rank> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
