package evidata.core.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Created by Nicolle on aug. in 2018
 */
public class AttributeCriteriaBuilder {
    public static <Y extends Comparable<? super Y>> Predicate buildPredicate(SearchCriteria criteria, Path root, CriteriaBuilder builder) {
        OperationEnum operation = criteria.getOperation();
        Path<Y> yPath = root.get(criteria.getKey());
        switch (operation) {
            case GREATER:
                return builder.greaterThan(yPath, (Y) criteria.getValue());
            case GREATER_OR_EQUAL:
                return builder.greaterThanOrEqualTo(yPath, (Y) criteria.getValue());
            case LESS:
                return builder.lessThan(yPath, (Y) criteria.getValue());
            case LESS_OR_EQUAL:
                return builder.lessThanOrEqualTo(yPath, (Y) criteria.getValue());
            case LIKE:
                return builder.like(
                        builder.upper((Path<String>) yPath),
                        "%" + criteria.getValue().toString().toUpperCase() + "%");
            case EQUAL:
                return builder.equal(yPath, criteria.getValue());

        }
        return null;
    }
}
