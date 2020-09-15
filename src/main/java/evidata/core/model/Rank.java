package evidata.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "rank")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Rank extends BaseEntity<Long> {

    private String name;
}
