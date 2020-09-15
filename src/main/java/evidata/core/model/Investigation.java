package evidata.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicolle on aug. in 2018
 */
@Entity
@Table(name = "investigation")
@Setter
@Getter
@NoArgsConstructor
public class Investigation extends BaseEntity<Long> {
    @Size(min=10, max=100, message = "Description of evidence must be between 10 and 100 characters")
    private String evidence;

    @Size(min=10, max=50, message = "Method of examination must be between 10 and 50 characters")
    private String method;

    private String intermediateResult;

    @Size(min=10, max=100, message = "Result description must be between 10 and 100 characters")
    private String result;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    private Expertise expertise;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "investigation_user", joinColumns = @JoinColumn(name = "investigation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<AppUser> users;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "investigation_equipment", joinColumns = @JoinColumn(name = "investigation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private List<Equipment> equipments;
}
