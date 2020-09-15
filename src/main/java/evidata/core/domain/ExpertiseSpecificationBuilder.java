package evidata.core.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.util.StringUtils;
import evidata.core.dto.ExpertiseSearch;
import evidata.core.model.Expertise;
import evidata.core.model.Status;

import java.util.Date;

/**
 * Created by Nicolle on 01.09.2018
 */
public class ExpertiseSpecificationBuilder {

    public static Specification<Expertise> byUser(Long id) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("appUserList", "id", OperationEnum.EQUAL, id);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    public static Specification<Expertise> byExpertiseNumber(String expertiseNumber) {
        return new AttributeSpecification(new SearchCriteria("expertiseNumber", OperationEnum.LIKE, expertiseNumber));
    }

    static Specification byDepartmentId(Long departmentId) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("department", "id", OperationEnum.EQUAL, departmentId);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    static Specification byRequestedBy(String requestedBy) {
        return new AttributeSpecification(new SearchCriteria("requestedBy", OperationEnum.LIKE, requestedBy));
    }
    static Specification byFileNumber(String fileNumber) {
        return new AttributeSpecification(new SearchCriteria("fileNumber", OperationEnum.LIKE, fileNumber));
    }

    static Specification byExpertiseTypeId(Long expertiseTypeId) {
        JoinSearchCriteria joinSearchCriteria = new JoinSearchCriteria("expertiseType", "id", OperationEnum.EQUAL, expertiseTypeId);
        return new JoinAttributeSpecification(joinSearchCriteria);
    }

    static Specification byDateAfter(Date startDate) {
        return new AttributeSpecification(new SearchCriteria("finishDate", OperationEnum.GREATER_OR_EQUAL, startDate));
    }

    static Specification byDateBefore(Date endDate) {
        return new AttributeSpecification(new SearchCriteria("finishDate", OperationEnum.LESS_OR_EQUAL, endDate));
    }

    static Specification byStatus(Status status) {
        return new AttributeSpecification(new SearchCriteria("status", OperationEnum.EQUAL, status));
    }

    public static Specification<Expertise> bySearch(ExpertiseSearch expertiseSearch) {

        Long userID = expertiseSearch.getUserId();
        Date startDate = expertiseSearch.getStartDate();
        Date endDate = expertiseSearch.getEndDate();
        String expertiseNumber = expertiseSearch.getExpertiseNumber();
        String requestedBy = expertiseSearch.getRequestedBy();
        Long departmentId = expertiseSearch.getDepartmentId();
        Long expertiseTypeId = expertiseSearch.getExpertiseTypeId();
        Status status = expertiseSearch.getStatus();
        String fileNumber=expertiseSearch.getFileNumber();

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
        if (!StringUtils.isEmpty(requestedBy)) {
            specifications = whereOrAddToSpecifications(specifications, byRequestedBy(requestedBy));
        }
        if (!StringUtils.isEmpty(fileNumber)){
            specifications=whereOrAddToSpecifications(specifications,byFileNumber(fileNumber));
        }
        if (departmentId != null) {
            specifications = whereOrAddToSpecifications(specifications, byDepartmentId(departmentId));
        }
        if (expertiseTypeId != null) {
            specifications = whereOrAddToSpecifications(specifications, byExpertiseTypeId(expertiseTypeId));
        }
        if (status != null) {
            specifications = whereOrAddToSpecifications(specifications, byStatus(status));
        }
        return specifications;
    }


    static Specifications whereOrAddToSpecifications(Specifications specifications, Specification<Expertise> spec) {
        if (specifications == null) {
            specifications = Specifications.where(spec);
        } else {
            specifications = specifications.and(spec);
        }
        return specifications;
    }

}
