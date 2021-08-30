package org.sid.secservice.sec;

import org.sid.secservice.sec.filters.JwtAuthenticationFilter;
import org.sid.secservice.sec.filters.JwtAuthorizationFilter;
import org.sid.secservice.sec.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailServiceImpl userDetailService;

    public SecurityConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/**").permitAll()
                .antMatchers("/swagger-ui/*", "/swagger-ui.html/", "/v3/api-docs/*").permitAll();// pour autoriser le swaaagger

        http.headers().frameOptions().disable();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/**").access("hasAuthority('ADMIN')");
        //http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/*").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/*").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        // http.formLogin();


    }

    //@Bean cad on a 1 object de type AuthenticationManager et on peut l'injecter là ou on veut
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
