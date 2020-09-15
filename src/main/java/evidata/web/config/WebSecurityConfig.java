package evidata.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Nicolle on aug. in 2018
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan({"evidata.web.controller"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()

                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/about/**").hasAnyRole("ADMIN","MANAGER","RESPONSIBLE")


                .antMatchers("/investigationList/**").hasAnyRole("MANAGER", "RESPONSIBLE")
                .antMatchers("/investigationEdit/**").hasAnyRole("MANAGER", "RESPONSIBLE")
                .antMatchers("/investigationDelete/**").hasAnyRole("MANAGER", "RESPONSIBLE")

                .antMatchers("/expertiseList/**").hasAnyRole("MANAGER", "RESPONSIBLE")
                .antMatchers("/expertiseEdit/**").hasAnyRole("MANAGER", "RESPONSIBLE")
                .antMatchers("/expertiseDelete/**").hasAnyRole("MANAGER", "RESPONSIBLE")

                .antMatchers("/report/**").hasAnyRole("MANAGER", "RESPONSIBLE")

                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .successForwardUrl("/home")
                .defaultSuccessUrl("/home")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");

    }


}