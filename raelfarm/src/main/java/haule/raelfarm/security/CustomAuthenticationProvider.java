package haule.raelfarm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;


    // 검쯩을 위한 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        
        UserDetails user = userDetailsService.loadUserByUsername(username);
        log.info("================================================");
        log.info("user.getPassword : "+user.getPassword());
        log.info("password : "+password);
        log.info(passwordEncoder.encode((String)authentication.getCredentials()));
        log.info("================================================");
        
        if(!user.isEnabled()){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        // password 일치하지 않으면!
        if(!passwordEncoder.matches(password, user.getPassword())) {
        	throw new BadCredentialsException("BadCredentialsException");
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

	// 토큰 타입과 일치할 때 인증
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
