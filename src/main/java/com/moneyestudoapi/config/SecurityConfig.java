package com.moneyestudoapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ROLE");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/categorias/*").permitAll() // exemplo categorias qualquer um pode
																				// acessar
				.anyRequest().authenticated()// o restante tem que estar autenticado
				.and().httpBasic().and() // tipo de autenticação
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // não mantem sessão
				.and().csrf().disable(); // desabilita o js injection
	}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests((authz) -> authz
//                .anyRequest().authenticated()
//            )
//            .httpBasic(withDefaults());
//        return http.build();
//    }

}
