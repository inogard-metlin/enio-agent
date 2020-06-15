package kr.co.inogard.enio.agent.configs;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig {

	@Configuration
	@Order(1)
	public static class AgentWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Resource(name = "agentUserDetailsService")
		private UserDetailsService userDetailsService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
			authenticationEntryPoint.setKey("enio2017");
			authenticationEntryPoint.setRealmName("enio system");
			authenticationEntryPoint.setNonceValiditySeconds(10);

			DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
			filter.setAuthenticationEntryPoint(authenticationEntryPoint);
			filter.setUserDetailsService(userDetailsService());

			http.antMatcher("/agent/**").authorizeRequests().anyRequest().hasRole("AGENT").and().addFilter(filter)
					.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().headers()
					.cacheControl().disable().and().csrf().disable();
		}
	}

	@Configuration
	@Order(2)
	public static class FormLoginWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		PasswordEncoder passwordEncoder;

		@Resource(name = "userDetailsService")
		private UserDetailsService userDetailsService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/webjars/**").permitAll().antMatchers("/**").hasRole("ADMIN")
					.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
					.usernameParameter("username").passwordParameter("password").permitAll().and().logout().permitAll()
					.and().exceptionHandling().accessDeniedPage("/error/403");
		}
	}
}
