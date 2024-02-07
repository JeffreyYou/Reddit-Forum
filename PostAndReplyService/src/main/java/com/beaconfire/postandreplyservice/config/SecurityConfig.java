package com.beaconfire.postandreplyservice.config;

import com.beaconfire.postandreplyservice.security.JwtFilter;
import com.beaconfire.postandreplyservice.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/posts/publish").permitAll()
                .antMatchers(HttpMethod.PUT, "/posts/save").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/published/all").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/banned/all").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_SADMIN.name())
                .antMatchers(HttpMethod.GET, "/posts/deleted/all").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_SADMIN.name())
                .antMatchers(HttpMethod.GET, "/posts/unpublished/all").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/hidden/all").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/*").permitAll()
                .antMatchers(HttpMethod.PATCH, "/posts/*/ban").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_SADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/posts/*/unban").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_SADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/posts/*/hide").permitAll()
                .antMatchers(HttpMethod.PATCH, "/posts/*/unhide").permitAll()
                .antMatchers(HttpMethod.PATCH, "/posts/*/delete").permitAll()
                .antMatchers(HttpMethod.PATCH, "/posts/*/recover").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_SADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/posts/*/modify").permitAll()
                .antMatchers(HttpMethod.PATCH, "/posts/*/archive").permitAll()
                .antMatchers(HttpMethod.PATCH, "/reply/*/reply").permitAll()
                .antMatchers(HttpMethod.PATCH, "/reply/*/delete-reply/*").permitAll()
                .anyRequest().authenticated();

    }
}
