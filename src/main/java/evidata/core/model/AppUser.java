package evidata.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nicolle on iun. in 2018
 */
@Entity
@Table(name = "app_user")
@Setter
@Getter
@NoArgsConstructor
public class AppUser extends BaseEntity<Long> {

    //@Size(min=2, max=30, message = "First type must be between 2 and 20 characters")
    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your first Name")
    private String firstName;

    //@Size(min=2, max=30, message = "Last type must be between 2 and 30 characters")
    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last type")
    private String lastName;

    //@Size(min=7, max=30, message = "Username must be between 7 and 30 characters")
    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "*Please provide your userName")
    private String username;

    //@Size(min=7, max=30, message = "Password must be between 7 and 30 characters")
    @Column(name = "password")
    //@Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    private String email;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<AppRole> appRoles;

    @ManyToOne
    private Rank rank;

    @ManyToOne
    private Experience experience;

    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;


}
