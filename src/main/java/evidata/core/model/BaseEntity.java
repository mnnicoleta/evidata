package evidata.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Nicolle on iun. in 2018
 */
@MappedSuperclass
public class BaseEntity<ID extends Serializable> {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue
    private ID id;

    public BaseEntity() {
    }

    public BaseEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
