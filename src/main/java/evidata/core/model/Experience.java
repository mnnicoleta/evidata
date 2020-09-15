package evidata.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "experience")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Experience extends BaseEntity<Long> {

    private String level;
}
