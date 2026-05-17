package hu.studentproductivity.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // REST API lévén kikapcsoljuk a CSRF védelmet (tokeneket használunk helyette)
            .csrf { it.disable() }

            // CORS beállítások engedélyezése (a meglévő WebConfig-od alapján)
            .cors { }

            // Stateless működés, mivel nem tartunk szerver oldali session-t,
            // minden kérésnek tartalmaznia kell a JWT tokent
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            // Végpontok védelmének beállítása
            .authorizeHttpRequests { auth ->
                // Ha később lesznek publikus végpontok, azokat így lehet megnyitni:
                // auth.requestMatchers("/api/public/**").permitAll()

                auth.anyRequest().authenticated()
            }

            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { }
            }

        return http.build()
    }
}