package evidata.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import evidata.core.model.Status;

import java.util.Date;

/**
 * Created by Nicolle on aug. in 2018
 */
@Getter
@Setter
public class ExpertiseSearch {
    private String expertiseNumber;
    private String requestedBy;
    private Long expertiseTypeId;
    private Long userId;
    private Long departmentId;
    private Status status;
    private String fileNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
