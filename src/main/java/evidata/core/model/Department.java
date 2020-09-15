package evidata.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Department extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<Expertise> expertises;

    public Department(String name) {
        this.name = name;
    }
}
