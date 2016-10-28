package findatms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class FindAtmsSecurityConfiguration extends WebSecurityConfigurerAdapter {
	 
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("marshall").password("password").roles("USER");
        
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http
      	.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/**").hasAnyRole("USER") //accessing any page requires USER permission
        .and().formLogin()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
  
    }
}