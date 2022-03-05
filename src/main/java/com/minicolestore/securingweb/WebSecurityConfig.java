package com.minicolestore.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.minicolestore.service.Impl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	String[] resources = new String[]{
	            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**","/recursos/**"};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().disable().authorizeRequests()
		.antMatchers(resources).permitAll()
		.antMatchers("/", "/catalogo","/registro","/procesarRegistro","/contacto","/sobreNosotros","/verificarEmail","/registroExitoso","/procesarContacto","/changePassword","/changePassword","/changePasswordCheck","/changePasswordProcess").permitAll()
		.antMatchers("/comentarios*").access("hasRole('ADMIN')")
		.antMatchers("/usuariosregistrados*").access("hasRole('ADMIN')")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/iniciarSesion").permitAll()
		.defaultSuccessUrl("/user")
		.usernameParameter("username")
        .passwordParameter("password")
        .and()
        .rememberMe().key("uniqueAndSecret")
        .tokenValiditySeconds(1440) //valor de la cookie en segundo ej, 60 => min
        .rememberMeParameter("recordar")
		.and()
		.logout()
		.permitAll()
		.deleteCookies("JSESSIONID");

	}
	
	
	//Encriptador
    BCryptPasswordEncoder bCryptPasswordEncoder;
   	
    
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
	 
    //inyeccion de dependencia
    @Autowired
    @Qualifier("userdetails")
    UserDetailsService userDetailsService;
	
   
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
	

}
