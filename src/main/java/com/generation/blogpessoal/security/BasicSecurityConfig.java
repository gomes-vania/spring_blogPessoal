package com.generation.blogpessoal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {
	
	// Essa classe define como a segurança da aplicação irá funcionar.
	
	 	@Bean //injeção de independência com maior visibilidade.
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 	
	 	 @Bean
	     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
	             throws Exception {
	         return authenticationConfiguration.getAuthenticationManager();
	     }
	 	 
	 	@Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	        http
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and().csrf().disable()
	            .cors();

	        http
	            .authorizeHttpRequests((auth) -> auth
	            .antMatchers("/usuarios/logar").permitAll()//endpoint desprotegido para todos logar
	            .antMatchers("/usuarios/cadastrar").permitAll()//endpoint desprotegido para todos cadastrar
	            .antMatchers(HttpMethod.OPTIONS).permitAll()//endpoint porta de entrada para entrar no sistema através do login e senha autenticado.
	            //versão 3 do spring usa o Request Matchers
	            .anyRequest().authenticated())
	            .httpBasic();

	        return http.build();

	    }
}
