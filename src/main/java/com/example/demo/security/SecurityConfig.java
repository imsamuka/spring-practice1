package com.example.demo.security;

import static com.example.demo.security.UserRole.*;
// import static com.example.demo.security.ApplicationUserPermissions.*;

import java.util.concurrent.TimeUnit;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		http
				.csrf().disable()
				// .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.authorizeRequests()
				.antMatchers("/", "/login", "/css/*", "/js/*")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()

				.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/students", false)
				.and()

				.rememberMe()
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
				.key("bX9h3wdf*LWxK$VX^uK@BlvPvj#xWzXkYyKpk#y")
				.and()

				.logout()
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("remember-me", "JSESSIONID", "csrftoken")
				.logoutSuccessUrl("/login?logout");
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {

		UserDetails smk = User.builder()
				.username("smk")
				.password(passwordEncoder.encode("123"))
				.authorities(ADMIN.getGrantedAuthorities())
				.build();

		UserDetails ana = User.builder()
				.username("ana")
				.password(passwordEncoder.encode("123"))
				.authorities(STUDENT.getGrantedAuthorities())
				.build();

		return new InMemoryUserDetailsManager(smk, ana);

		// return super.userDetailsService();
	}

}
