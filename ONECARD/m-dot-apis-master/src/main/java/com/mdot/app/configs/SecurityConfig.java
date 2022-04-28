package com.mdot.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mdot.app.securities.CustomUserDetailsService;
import com.mdot.app.securities.JwtAuthenticationEntryPoint;
import com.mdot.app.securities.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	// Authentication : User --> Roles
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authorization : Role -> Access
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers("/api/auth/signin", "/api/auth/signin/app", "/api/user/save","/api/user/usersave", "/api/auth/signup","/api/auth/signup/pos","/api/file/image/**",
						"/api/auth/register/app", "/api/auth/isexist/**", "/api/auth/forgotpassword", "/api/auth/forgotpassword/app",
						"/api/auth/resetpassword", "/api/auth/validateToken","/api/auth/login","/api/auth/register",
						"/api/project/save/{id}/{title}/{description}","/api/project/my-projects/","/api/project/{id}","/api/card/save/{id}/{cardid}","/api/socialmedia/save/{id}",
						"/api/user/{id}","/api/user/image/{image}","/api/user/username/{username}","/api/user/phone/{phone}","/api/user/email/{email}","/api/user/{id}/status/{status}")
				.permitAll().antMatchers("/api/user/checkAvailability/username", "/api/user/checkAvailability/email")
				.permitAll().antMatchers(HttpMethod.GET, "/api/category/public/**", "/api/stock/public/**", "/api/user/show/image/**", "/api/product/suggest/**", "/api/postcode/**").permitAll()
				.antMatchers("/api/users/validateToken*", "/api/users/savePassword*")
				.hasAuthority("CHANGE_PASSWORD_PRIVILEGE").anyRequest().authenticated();
		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}  
}