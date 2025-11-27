package com.projetwuri.alimentation.security;

import com.projetwuri.alimentation.services.CustomUserDetailsService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS ));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**","api/v1/categorie/liste","api/v1/logs","/actuator/**" ).permitAll() // Chemin correct avec slash
                // CORRECTION ICI : Ajout du slash et utilisation de hasAnyAuthority
                .requestMatchers("/api/v1/produit/creer","api/v1/idProduit/{idProduit}/idCategorie/{idCategorie}", "/api/v1/produit/liste","/api/v1/categorie/creer","api/v1/produit/update/{id}","api/v1/produit/consulter/{id}","api/v1/produit/delete/{id}").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
        );

        http.authenticationProvider(authenticationProvider( ));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
        return http.build( );
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Lien avec UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


