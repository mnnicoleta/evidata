package evidata.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Nicolle on aug. in 2018
 */
@Setter
@Getter
public class InvestigationSearch {
    private Long userId;
    private String expertiseNumber;
    private String evidence;
    private String result;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long equipmentId;

}
