package evidata.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import evidata.core.model.AppUser;
import evidata.core.service.AppUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Nicolle on aug. in 2018
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImp.class);

    @Autowired
    private AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserService.findByUsername(username);
        User.UserBuilder builder = null;
        if (user.isPresent()) {
            if(user.get().isActive()) {
                builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.password(user.get().getPassword());
                List<String> rolesList = user.get().getAppRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
                String[] roles = new String[rolesList.size()];
                roles = rolesList.toArray(roles);
                builder.roles(roles);
            }else{
                log.warn("User inactive {}",username);
                //inactive user
                throw new UsernameNotFoundException("Inactive user found.");
            }
        } else {
            log.warn("User with type '{}' not found",username);
            throw new UsernameNotFoundException("User not found.");
        }

        UserDetails userDetails = builder.build();
        return userDetails;
    }
}