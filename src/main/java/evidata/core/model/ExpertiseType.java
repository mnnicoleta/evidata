package evidata.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "expertiseType")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ExpertiseType extends BaseEntity<Long> {
    @Column(unique = true, nullable = false)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expertiseType")
    private List<Expertise> expertises;

    public ExpertiseType(String type) {
        this.type = type;
    }
}
