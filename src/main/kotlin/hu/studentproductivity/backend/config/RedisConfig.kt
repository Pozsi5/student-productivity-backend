package hu.studentproductivity.backend.config

import hu.studentproductivity.backend.pomodoro.model.PomodoroSettingsDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    /**
     * Manuálisan definiáljuk a RedisTemplate beanjét a megfelelő szerializálókkal.
     * Ez felülírja a Spring Boot alapértelmezett beállítását.
     */
    @Bean
    fun pomodoroRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, PomodoroSettingsDTO> {

        val template = RedisTemplate<String, PomodoroSettingsDTO>()

        // Csatlakozási gyár beállítása (ezt a Spring Auto-Configuration hozza létre)
        template.setConnectionFactory(connectionFactory)

        // Kulcs szerializálása (mindig String formában tároljuk a kulcsokat, pl. "pomodoro:settings:id")
        template.setKeySerializer(StringRedisSerializer())
        template.setHashKeySerializer(StringRedisSerializer())

        // Érték szerializálása (JSON formában tároljuk a DTO objektumot)
        val jsonSerializer = Jackson2JsonRedisSerializer(PomodoroSettingsDTO::class.java)

        template.setValueSerializer(jsonSerializer)
        template.setHashValueSerializer(jsonSerializer)

        template.afterPropertiesSet()
        return template
    }
}