package evidata.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nicolle on aug. in 2018
 */
@Entity
@Table(name = "app_role")
@Setter
@Getter
@NoArgsConstructor
public class AppRole extends BaseEntity<Long> {

    @Column(name = "role")
    private String role;

}
