package evidata.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nicolle on aug. in 2018
 */
@Entity
@Table(name = "app_settings")
@Setter
@Getter
public class AppSettings extends BaseEntity<Long> {
    private String attachmentDir;
}
