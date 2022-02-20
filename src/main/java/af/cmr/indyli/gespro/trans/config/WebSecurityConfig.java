package af.cmr.indyli.gespro.trans.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**"
			// other public endpoints of your API may be appended to this array
	};

//	@Autowired
//	private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//	@Bean
//	public JwtRequestFilter authenticationJwtTokenFilter() {
//
//		return new JwtRequestFilter();
//	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		authProvider.setUserDetailsService(userDetailsService);
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//configuration authentification user en base sans token

//	  http
//	  .authorizeRequests()
//	  .antMatchers("/role", "/role/*")
//	  .permitAll()
//	  .antMatchers( "/delete/**").hasAuthority("SUPER_ADMIN")
//	  .antMatchers("/edit/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN", "LOCATAIRE", "PROPRIETAIRE")
//	  .anyRequest()
//	  .authenticated()
//	  .and()
//	  .formLogin().permitAll()
//	  .and()
//	  .logout().permitAll()
//	  .and()
//	  .exceptionHandling().accessDeniedPage("/page_403")
//	  .and()
//	  .httpBasic();

		/**
		 * configuration authentification user en base Avec Token JWT
		 */
		http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/user/**").permitAll().and().authorizeRequests().antMatchers("/role", "/role/**")
				.permitAll().and().authorizeRequests().antMatchers("/category", "/category/**").permitAll().and()
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()

				.anyRequest().permitAll()
				// .anyRequest().authenticated()
				.and().formLogin().permitAll().and().logout().permitAll().and().exceptionHandling()
				.accessDeniedPage("/page_403");

	}

	/**
	 * AuthenticationManagerBuilder est la principale interface pour
	 * l'authentification
	 */

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService);
//	}

	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}

}
