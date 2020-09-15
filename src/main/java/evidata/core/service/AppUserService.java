package evidata.core.service;


import evidata.core.model.AppRole;
import evidata.core.model.AppUser;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */

public interface AppUserService {
    /**
     * Get the appUser with given username
     *
     * @param username identifier of the appUser
     * @return the optional appUser
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Get the appUser with given email
     *
     * @param email identifier of the appUser
     * @return the optional appUser
     */
    AppUser findUserByEmail(String email);

    /**
     * Create  a appUser
     *
     * @param appUser saved entity
     */
    void createNewUser(AppUser appUser);


    /**
     * Update a appUser
     *
     * @param appUser saved entity
     */
    void updateUser(AppUser appUser);

    /**
     * Get a list will all available appUsers
     *
     * @return the list of appUsers
     */
    List<AppUser> getAllAppUsers();

    /**
     * Get a list will all appUsers which have the role given
     *
     * @param role identifier for appUser
     * @return the list of appUsers by role given
     */
    List<AppUser> getUsersByRole(String role);

    /**
     * Get the appUser with given email
     *
     * @param id identifier of the appUser
     * @return the optional appUser
     */
    Optional<AppUser> findAppUserById(Long id);

    /**
     * Delete the appUser by given ID
     *
     * @param id identifier for deleted appUser
     */
    void deleteAppUser(Long id);

    /**
     * Get a list will all existing roles
     *
     * @return the list of roles
     */
    List<AppRole> getAllAppRoles();

}
