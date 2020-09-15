package evidata.core.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.util.StringUtils;
import evidata.core.dto.InvestigationSearch;
import evidata.core.model.Investigation;

import java.util.Date;

/**
 * Created by Nicolle on aug. in 2018
 */
public class InvestigationSpecificationBuilder {

    public static Specification<Investigation> byUser(Long id) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("users", "id", OperationEnum.EQUAL, id);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    public static Specification<Investigation> byEquipment(Long id) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("equipments", "id", OperationEnum.EQUAL, id);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    public static Specification<Investigation> byExpertiseNumber(String expertiseNumber) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("expertise", "expertiseNumber", OperationEnum.LIKE, expertiseNumber);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    static AttributeSpecification byResult(String result) {
        return new AttributeSpecification(new SearchCriteria("result", OperationEnum.LIKE, result));
    }

    static AttributeSpecification byEvidence(String evidence) {
        return new AttributeSpecification(new SearchCriteria("evidence", OperationEnum.LIKE, evidence));
    }

    static AttributeSpecification byDateAfter(Date startDate) {
        return new AttributeSpecification(new SearchCriteria("date", OperationEnum.GREATER_OR_EQUAL, startDate));
    }

    static AttributeSpecification byDateBefore(Date endDate) {
        return new AttributeSpecification(new SearchCriteria("date", OperationEnum.LESS_OR_EQUAL, endDate));
    }


    public static Specification<Investigation> bySearch(InvestigationSearch investigationSearch) {

        Long userID = investigationSearch.getUserId();
        Date startDate = investigationSearch.getStartDate();
        Date endDate = investigationSearch.getEndDate();
        String expertiseNumber = investigationSearch.getExpertiseNumber();
        String evidence = investigationSearch.getEvidence();
        String result = investigationSearch.getResult();
        Long equipmentId=investigationSearch.getEquipmentId();

        Specifications specifications = null;

        if (userID != null) {
            specifications = whereOrAddToSpecifications(specifications, byUser(userID));
        }
        if (startDate != null) {
            specifications = whereOrAddToSpecifications(specifications, byDateAfter(startDate));
        }
        if (endDate != null) {
            specifications = whereOrAddToSpecifications(specifications, byDateBefore(endDate));
        }
        if (!StringUtils.isEmpty(expertiseNumber)) {
            specifications = whereOrAddToSpecifications(specifications, byExpertiseNumber(expertiseNumber));
        }
        if (!StringUtils.isEmpty(evidence)) {
            specifications = whereOrAddToSpecifications(specifications, byEvidence(evidence));
        }
        if (!StringUtils.isEmpty(result)) {
            specifications = whereOrAddToSpecifications(specifications, byResult(result));
        }

        if (equipmentId != null) {
            specifications = whereOrAddToSpecifications(specifications, byEquipment(equipmentId));
        }

        return specifications;
    }


    static Specifications whereOrAddToSpecifications(Specifications specifications, Specification<Investigation> spec) {
        if (specifications == null) {
            specifications = Specifications.where(spec);
        } else {
            specifications = specifications.and(spec);
        }
        return specifications;
    }

}
