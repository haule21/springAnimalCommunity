package haule.raelfarm.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import haule.raelfarm.security.AuthFailureHandler;
import haule.raelfarm.security.AuthSuccessHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{
	
	private final AuthSuccessHandler authSuccessHandler;
	private final AuthFailureHandler authFailureHandler;
	
	//	@Autowired
	// CustomAuthorizationManager customAuthorizationManager;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		String encodingId = "sha256";
		Map<String, PasswordEncoder> encoders = new HashMap<>();  
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
		return new DelegatingPasswordEncoder(encodingId, encoders);
    }

	
	@Bean
 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf().and()
			
			.authorizeHttpRequests()
				.requestMatchers("/templates/content/login/**").permitAll()
				.requestMatchers("/register_agree","/register_page").permitAll()
				.requestMatchers("/register_page/idcheck","/register_page/nicknamecheck"
						,"/register_page/send-one","/register_page/register").permitAll()
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/js/*.js","/css/*.css","/img/*.PNG").permitAll()
				.requestMatchers("/img/*/*/*/*").permitAll()
				.anyRequest()
				//.access(customAuthorizationManager)
				.authenticated()
				.and()
				
			.formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/loginProc").permitAll()
				.failureHandler(authFailureHandler)
				.successHandler(authSuccessHandler).and()
			.logout().permitAll().and()
			.exceptionHandling().accessDeniedPage("/404");
			
 		return http.build();
 	}
	
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setAuthoritiesByUsernameQuery("SELECT A.USERID, B.ROLE_NAME FROM USERS_TEST A, USER_ROLE_TEST B WHERE A.USERID = ? AND A.ROLE_NUM = B.ROLE_NUM");
        users.setUsersByUsernameQuery("SELECT USERID, PASSWORD, IF(DELETED = 'N', 'TRUE', 'FALSE') AS ENABLED FROM USERS_TEST WHERE USERID = ?");
        return users;
    }
    
}