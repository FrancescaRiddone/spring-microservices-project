package com.microservices.project.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.microservices.project.service.UserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
		        .antMatchers(SecurityConstants.PRIVATE_FLIGHT_CATALOG_URL).denyAll()
				.antMatchers(SecurityConstants.PRIVATE_HOTEL_CATALOG_URL).denyAll()
        		.antMatchers(SecurityConstants.PUBLIC_FLIGHT_CATALOG_URL).permitAll()
        		.antMatchers(SecurityConstants.PUBLIC_HOTEL_CATALOG_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}
}
