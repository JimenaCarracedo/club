package com.sanmartin.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sanmartin.club.Service.SocioService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SocioService myAppUserDetailsService;

    @Override
	protected void configure(HttpSecurity http) throws Exception{
		http.headers().frameOptions().sameOrigin().and().authorizeRequests()
			.antMatchers("/css/*", "/js/*", "/img/*").permitAll()
			.and().formLogin().loginPage("/login")										
										.usernameParameter("username")
										.passwordParameter("password")
										.defaultSuccessUrl("/admin")
										.failureUrl("/usuario/login")
										.permitAll()
							.and().logout()
									.logoutUrl("/logout")
									.logoutSuccessUrl("/")
									.permitAll();
	}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService( myAppUserDetailsService).passwordEncoder(passwordEncoder);
    }
    
}