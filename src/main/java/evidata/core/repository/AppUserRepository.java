package evidata.core.repository;


import org.springframework.stereotype.Repository;
import evidata.core.model.AppUser;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Repository
public interface AppUserRepository extends IRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findById(Long id);

    List<AppUser> findByAppRoles_Role(String role);
}
