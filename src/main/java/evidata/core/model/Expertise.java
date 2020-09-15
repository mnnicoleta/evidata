package evidata.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "expertise")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Expertise extends BaseEntity<Long> {
    @Size(min=5, max=40, message = "Expertise number must be between 5 and 40 characters")
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "*Please provide a number for your expertise")
    private String expertiseNumber;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Size(min=5, max=40, message = "File number must be between 5 and 40 characters")
    @Column(nullable = false)
    private String fileNumber;

    @Size(min=5, max=40, message = "Solicitor type must be between 5 and 40 characters")
    @Column(nullable = false)
    private String requestedBy;

    @ManyToOne
    private ExpertiseType expertiseType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "expertise_appUser", joinColumns = @JoinColumn(name = "appUser_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "expertise_id", referencedColumnName = "id"))
    private List<AppUser> appUserList;

    @ManyToOne
    private Department department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expertise")
    private List<Attachment> attachments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expertise)) return false;
        Expertise expertise1 = (Expertise) o;
        return Objects.equals(getId(), expertise1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Expertise{" +
                "expertiseId ' " + getId() + '\'' +
                "expertiseNumber='" + expertiseNumber + '\'' +
                ", deadline=" + deadline +
                ", fileNumber='" + fileNumber + '\'' +
                ", requestedBy='" + requestedBy + '\'' +
                ", expertiseType=" + expertiseType.getType() +
                ", appUserList=" + appUserList +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", attachments=" + attachments +
                '}';
    }
}
