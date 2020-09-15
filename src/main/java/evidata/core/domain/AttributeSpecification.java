package evidata.core.domain;

import org.springframework.data.jpa.domain.Specification;
import evidata.core.model.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Nicolle on aug. in 2018
 */
public class AttributeSpecification<Y extends Comparable<? super Y>> implements Specification<BaseEntity> {
    private SearchCriteria criteria;

    public AttributeSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<BaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return AttributeCriteriaBuilder.buildPredicate(criteria, root, builder);
    }
}
