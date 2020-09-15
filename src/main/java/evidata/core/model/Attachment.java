package evidata.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nicolle on aug. in 2018
 */
@Entity
@Table(name = "attachment")
@Setter
@Getter
@NoArgsConstructor
public class Attachment extends BaseEntity<Long> {

    @Column(name = "filename")
    @NotEmpty(message = "*Please provide a filename")
    private String fileName;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "size")
    private long size;

    @ManyToOne(cascade = CascadeType.ALL)
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private Expertise expertise;
}
