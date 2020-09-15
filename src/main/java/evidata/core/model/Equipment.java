package evidata.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created by Nicolle on aug. in 2018
 */
@Entity
@Table(name = "equipment")
@Setter
@Getter
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Equipment extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String name;

    public Equipment(String name) {
        this.name = name;
    }
}