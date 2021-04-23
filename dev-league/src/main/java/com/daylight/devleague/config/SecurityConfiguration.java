package com.daylight.devleague.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.daylight.devleague.filters.JWTAuthorizationFilter;
import com.daylight.devleague.filters.SimpleCORSFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthorizationFilter authFilter;
	
	@Autowired
	private SimpleCORSFilter simpleCORSFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
		.and()
			.csrf().disable()
			.addFilterAfter(authFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(simpleCORSFilter, UsernamePasswordAuthenticationFilter.class)
			.headers().frameOptions().disable()
		.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
		.and()
		.authorizeRequests()
			.antMatchers("/h2-console/**", "/h2-console/**/**").permitAll()
			.anyRequest().authenticated();
	}
	
	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder(); 
	}

}
