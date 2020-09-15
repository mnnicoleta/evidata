package evidata.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import evidata.core.model.AppRole;
import evidata.core.model.AppUser;
import evidata.core.repository.AppRoleRepository;
import evidata.core.repository.AppUserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {

    private static final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);


    private AppUserRepository appUserRepository;

    private AppRoleRepository appRoleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppRoleRepository getAppRoleRepository() {
        return appRoleRepository;
    }

    @Autowired
    public void setAppRoleRepository(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public void createNewUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setActive(false);
        if (CollectionUtils.isEmpty(appUser.getAppRoles())) {
            AppRole userAppRole = appRoleRepository.findByRole("RESPONSIBLE");
            appUser.setAppRoles(new HashSet<AppRole>(Arrays.asList(userAppRole)));
        }
        appUserRepository.save(appUser);
    }

    @Override
    public void updateUser(AppUser appUserEdit) {
        AppUser origUser=appUserRepository.findOne(appUserEdit.getId());

        String password = appUserEdit.getPassword();
        if(StringUtils.isEmpty(password)) {
            appUserEdit.setPassword(origUser.getPassword());
        }else{
            appUserEdit.setPassword(bCryptPasswordEncoder.encode(password));
        }

        appUserRepository.save(appUserEdit);
    }



    @Override
    public List<AppUser> getAllAppUsers() {
        log.trace("getAllAppUsers --- method entered");

        List<AppUser> appUsers = appUserRepository.findAll();

        log.trace("getAllAppUsers: appUsers={}", appUsers);

        return appUsers;
    }


    @Override
    public List<AppUser> getUsersByRole(String role) {
        List<AppUser> appUsers = appUserRepository.findByAppRoles_Role(role);
        return appUsers;

    }

    @Override
    public List<AppRole> getAllAppRoles() {
        List<AppRole> appUsers = appRoleRepository.findAll();
        return appUsers;
    }

    @Override
    public Optional<AppUser> findAppUserById(Long id) {
        log.trace("findAppUser: id={}", id);

        Optional<AppUser> appUserOptional = appUserRepository.findById(id);

        log.trace("findAppUser: appUserOptional={}", appUserOptional);

        return appUserOptional;
    }

    @Override
    public void deleteAppUser(Long id) {
        log.trace("deleteAppUser: id={}", id);
        appUserRepository.delete(id);
        log.trace("deleteAppUser --- method finished");
    }

}
