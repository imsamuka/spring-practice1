package com.example.demo.security;

import static com.example.demo.security.ApplicationUserRole.*;
// import static com.example.demo.security.ApplicationUserPermissions.*;

// import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		http
				.csrf().disable()
				// .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.authorizeRequests()
				.antMatchers("/", "index", "/css/*", "/js/*")
				.permitAll()
				// .antMatchers(HttpMethod.DELETE,
				// "/api/**").hasAuthority(COURSE_WRITE.getPermission())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {

		UserDetails smk = User.builder()
				.username("smk")
				.password(passwordEncoder.encode("123"))
				// .roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();

		UserDetails ana = User.builder()
				.username("ana")
				.password(passwordEncoder.encode("123"))
				// .roles(STUDENT.name())
				.authorities(STUDENT.getGrantedAuthorities())
				.build();

		return new InMemoryUserDetailsManager(smk, ana);

		// return super.userDetailsService();
	}

}
