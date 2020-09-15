package evidata.core.domain;

import org.springframework.data.jpa.domain.Specification;
import evidata.core.model.BaseEntity;

import javax.persistence.criteria.*;

/**
 * Created by Nicolle on aug. in 2018
 */
public class JoinAttributeSpecification<T extends BaseEntity> implements Specification<BaseEntity> {
    private JoinSearchCriteria criteria;

    public JoinAttributeSpecification(JoinSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<BaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join join = root.join(criteria.getRelName());
        return AttributeCriteriaBuilder.buildPredicate(criteria, join, builder);
    }
}
