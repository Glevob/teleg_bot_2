package ru.telgram.jokebot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.telgram.jokebot.model.UserAuthority;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login", "/jokes/top").permitAll()

                                .requestMatchers(HttpMethod.POST, "/jokes").hasAuthority(UserAuthority.PLACE_JOKES.getAuthority())

                                .requestMatchers(HttpMethod.GET, "/jokes/**").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.POST, "/jokes").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/jokes/{id}").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/jokes/{id}").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .anyRequest().hasAuthority(UserAuthority.FULL.getAuthority()))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
