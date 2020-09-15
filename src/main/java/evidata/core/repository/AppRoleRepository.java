package evidata.core.repository;


import org.springframework.stereotype.Repository;
import evidata.core.model.AppRole;

/**
 * Created by Nicolle on aug. in 2018
 */
@Repository
public interface AppRoleRepository extends IRepository<AppRole, Long> {
    AppRole findByRole(String role);
}
