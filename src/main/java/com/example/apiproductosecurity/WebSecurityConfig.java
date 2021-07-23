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
/*
 # @Configuration
 Con la anotacion @Configaration estamos diciendo a Spring  que toodo lo que pasa aca adentro en esta clase sea lo primero
  en leer y analizar antes de iniciar a correr la aplicacion, para que configure.
 ---------------------------------------------------------------
 # @EnableWebSecurity
  Tenemos que activar la SEGURIDAD WEB
  Es una anotacion que establece ciertas pautas de configuracion
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    Generate -> Override Methods -> configure(http:HttpSecurity):void
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* # ESTAMOS ROMPIENDO LA REGLA DE LA LEY DE DEMETER?

        NO un rotundamente no, porque la ley de demeter dice que no podemos hablar con extranios, en nuestro caso
        el metodo disable() me devuelve un objeto que es un HttpSecurity y ese objeto es mi http que me enviaron
        como parametro en el metodo configue, luego los metodos authorizeRequests(), antMatchers(), etc...
        me retornan ese mismisimo objeto http ya configurado, por lo cual me permite hacer una sola linea de codigo
        o sea concatenar los metodos pero los escribimos uno bajo del otro para que se vea bonito
        y no se vea un choclo XD.
        ----------------------------------------------------------------------------------------------------
        # MUY IMPORTANTE : cumplir con el orden de la configuracion especifica, porque al cambiar el orden de configuracion
        de algunas cosas puede provocar distintos resultados en mi configuracion.
        ----------------------------------------------------------------------------------------------------
        # QUE ES CSRF (Cross Site Request  Forgery)?
        Es una subplantancion de un request, pero el metodo csrf() es para asegurar cuando tenemos un front en nuestro
        como hicimos una API deshabilitaremos el metodo csrf().
        ----------------------------------------------------------------------------------------------------
         */
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
