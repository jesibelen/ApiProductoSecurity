package com.example.apiproductosecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.html").permitAll()
                .antMatchers("/api/productos/detalles").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }
    @Bean // Ejecute una vez y cada vez que yo pida
    //le aviso a spring que esto es una entidad y necesito que este instanciada y se ejecute y que me devuelva el resultado
    public PasswordEncoder passwordEncoder(){// ES UNA INTERFAZ
        return new BCryptPasswordEncoder(5);// DEVUELVE UN OBJETO CONCRETO
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //creo mis tres usuarios con sus respectivas claves
        UserDetails usuario1 = User.builder()
                .username("martin")
                .password(passwordEncoder().encode("hola"))
                .roles("ADMIN")
                .build();

        UserDetails usuario2 = User.builder()
                .username("geraldine")
                .password(passwordEncoder().encode("clave"))
                .roles("CLIENTE")
                .build();

        UserDetails usuario3 = User.builder()
                .username("Jesica")
                .password(passwordEncoder().encode("jesi"))
                .roles("CLIENTE")
                .build();

        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3);
        /* Se guarda en la memoria de la computadora y funciona directamente con una lista o mapa de usuario
        sin necesidad de una base de datos
         */
    }
}
