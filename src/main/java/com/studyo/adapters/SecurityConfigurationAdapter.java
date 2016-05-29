package com.studyo.adapters;

import com.studyo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        auth.userDetailsService(authenticationService).passwordEncoder(encoder);
    }


}
