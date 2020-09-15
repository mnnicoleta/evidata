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
import evidata.core.model.Rank;
import evidata.core.repository.RankRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 06.09.2018
 */
@Service
public class RankServiceImpl implements RankService {
    private static final Logger log = LoggerFactory.getLogger(RankServiceImpl.class);

    
    private evidata.core.repository.RankRepository rankRepository;

    @Override
    public List<Rank> getAllRanks() {
        log.trace("getAllRanks --- method entered");

        List<Rank> Ranks = getRankRepository().findAll(sortByNameAsc());

        log.trace("getAllRanks: Ranks={}", Ranks);

        return Ranks;
    }

    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @Override
    @Transactional
    public Rank saveRank(Rank Rank) {
        if (Rank.getId() == null) {
            log.trace("create new Rank type={} ", Rank.getName());
        } else {
            log.trace("updateRank: id={}, type={} ", Rank.getId(), Rank.getName());
        }
        Rank savedRank = getRankRepository().save(Rank);
        log.trace("saveRank: result={}", savedRank);
        return savedRank;
    }

    @Override
    public void deleteRank(Long id) {
        log.trace("deleteRank: id={}", id);
        getRankRepository().delete(id);
        log.trace("deleteRank --- method finished");
    }

    @Override
    public Optional<Rank> findRank(Long id) {
        log.trace("findRank: id={}", id);

        Optional<Rank> RankOptional = getRankRepository().findById(id);

        log.trace("findRank: RankOptional={}", RankOptional);

        return RankOptional;
    }

    @Override
    public Optional<Rank> findByNameIgnoringCase(String rankName) {
        log.trace("findRank: rankName={}", rankName);

        Optional<Rank> rankOptional = getRankRepository().findByNameIgnoringCase(rankName);

        log.trace("findRank: rankOptional={}", rankOptional);

        return rankOptional;
    }

    public Page<Rank> findPaginated(String name, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Rank> page;
        if (StringUtils.isEmpty(name)) {
            page = getRankRepository().findAll(new PageRequest(currentPage, pageSize, sortByNameAsc()));
        } else {
            page = getRankRepository().findByNameStartingWithIgnoreCase(name, new PageRequest(currentPage, pageSize, sortByNameAsc()));
        }
        return page;
    }

    public RankRepository getRankRepository() {
        return rankRepository;
    }

    @Autowired
    public void setRankRepository(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }
}
