package es.urjc.computadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		// Public pages
		 http.authorizeRequests().antMatchers("/").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject/project/{id}").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject/login").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject/register").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject/newUser").permitAll();
		 http.authorizeRequests().antMatchers("/ourProject/init").permitAll();

		 
		 http.authorizeRequests().antMatchers("/ourProject/myProfile").authenticated();
		 http.authorizeRequests().anyRequest().authenticated();
		 
		 
		// Login form
		 http.formLogin().loginPage("/ourProject/login");
		 http.formLogin().usernameParameter("username");
		 http.formLogin().passwordParameter("password");
		 http.formLogin().defaultSuccessUrl("/ourProject");
		 http.formLogin().failureUrl("/ourProject/login");
		 
		// Logout
		 http.logout().logoutUrl("/logout");
		 http.logout().logoutSuccessUrl("/ourProject");

		 // Disable CSRF at the moment
		 //http.csrf().disable();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// User
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**", "/php/**", "/vendor/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	

}
