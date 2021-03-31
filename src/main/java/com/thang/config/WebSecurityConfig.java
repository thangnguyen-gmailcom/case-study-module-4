package com.thang.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@ComponentScan("com.thang")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	.csrf().disable()
				.authorizeRequests()
					.antMatchers("/product/**","/product-type/**").hasAnyRole("ADMIN", "USER", "NORMAL")
					.antMatchers("/user/**").access("hasRole('ADMIN')")
					.antMatchers("/home/productDetails/addCart/**","/home/updateCart/**","/home/**/deleteCart","/home/deleteCart")
					//.permitAll()
					.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_NORMAL")
					.and()
				.formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.passwordParameter("password")
			        .defaultSuccessUrl("/home/", true)
			        .failureUrl("/login?error=true")
			        .and()
			    .rememberMe()
			        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
			        .key("something very secured")
			        .rememberMeParameter("remember-me")
			    .and()
			    .exceptionHandling().accessDeniedPage("/Access_Denied")
			    .and()
			    .logout()
			        .logoutUrl("/logout")
			        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			        .clearAuthentication(true)
			        .invalidateHttpSession(true)
			        .deleteCookies("JSESSIONID", "remember-me")
			        .logoutSuccessUrl("/login");
	}
	

	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	  }
}