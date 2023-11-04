package ge.vako.bootcamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails student = User.withDefaultPasswordEncoder()
                .username("student")
                .password("pass")
                .roles("STUDENT")
                .build();

        UserDetails teacher = User.withDefaultPasswordEncoder()
                .username("teacher")
                .password("pass")
                .roles("TEACHER")
                .build();

        return new InMemoryUserDetailsManager(student, teacher);
    }
}
