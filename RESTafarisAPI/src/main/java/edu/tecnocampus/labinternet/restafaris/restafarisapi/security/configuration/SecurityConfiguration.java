package edu.tecnocampus.labinternet.restafaris.restafarisapi.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/authenticate",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /* At this moment I'm not able to have the h2-console working with the security. So, I disabled the
    console in the -h2-memory.properties file
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // .headers(headers -> headers.frameOptions(frameOptions -> frameOptions
                //         .sameOrigin()))
                // .csrf(csrf -> csrf
                //         .ignoringRequestMatchers(PathRequest.toH2Console()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                //.requestMatchers( "/**").hasAnyRole("ADMIN")
                                .requestMatchers(POST,"/courses").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(GET,"/courses/{id}").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(PUT, "/courses/{id}").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(PATCH,"/courses/{id}/price").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(PATCH,"/courses/{id}/availability").hasAnyRole("ADMIN")
                                .requestMatchers(GET,"/courses").permitAll()
                                //upgrate the user a teacher (nomes admin)
                                //list available courses w// review (tots i unregistered user)
                                .requestMatchers(GET,"/courses/search").permitAll()
                                .requestMatchers(GET,"/courses/categoriesAndLanguages").permitAll()
                                .requestMatchers(POST,"/lenguages").hasAnyRole("ADMIN")
                                .requestMatchers(POST,"/lessons").hasAnyRole("ADMIN")
                                .requestMatchers(GET,"/lessons/{id}").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(GET,"/lessons/{id}/order").permitAll()
                                .requestMatchers(PUT,"/lessons/{id}/order").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(PATCH,"/lessons/{id}/markAsDone").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(PUT,"/courses/{id}/review").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(GET,"/courses/myCourses").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(GET,"/courses/getMyCreatedCourses").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(POST,"/courses/{idUser}/courses/{idCourse}/purchase").hasAnyRole("ADMIN", "TEACHER", "STUDENT")

                                .requestMatchers(POST,"/categories").hasAnyRole("ADMIN")
                                .requestMatchers(DELETE,"/categories/{id}").hasAnyRole("ADMIN")

                                .requestMatchers(GET,"/users").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(GET,"/users/{id}/courses").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(GET,"/users/{id}/buyCourse").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(GET, "/courses/myCourses/finished").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(GET, "/courses/unregistered").permitAll()

                                //.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
