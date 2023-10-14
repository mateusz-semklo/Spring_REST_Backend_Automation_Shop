package pl.mateusz_semklo.automationshoprest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager= new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .csrf((csrf)->csrf.disable())
                .cors((cors)->cors.disable())
                .authorizeHttpRequests((request)-> {
                            request.requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
                                    .requestMatchers(new MvcRequestMatcher(introspector, "/login**")).permitAll()
                                    .requestMatchers(new MvcRequestMatcher(introspector, "/alfa")).permitAll()
                                    .requestMatchers(new AntPathRequestMatcher("/console**")).permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .headers((headers)->headers.frameOptions((frame)->frame.sameOrigin()))
              //  .sessionManagement((session)->{
               //     session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
              //  })
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
