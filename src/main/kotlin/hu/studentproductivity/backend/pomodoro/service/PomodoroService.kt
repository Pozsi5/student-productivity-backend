package hu.studentproductivity.backend.pomodoro.service

import hu.studentproductivity.backend.pomodoro.model.PomodoroSettingsDTO
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class PomodoroService(
    // A RedisTemplate injektálása.
    private val redisTemplate: RedisTemplate<String, PomodoroSettingsDTO>
) {
    // Az Redis kulcs prefixe a beállítások elkülönítéséhez.
    private val KEY_PREFIX = "pomodoro:settings:"

    // A beállítások tárolásának időtartama (pl. 30 nap)
    // Ez a kulcs lejár, ha a felhasználó sokáig nem tér vissza.
    private val TTL_DAYS = 30L

    /**
     * Elmenti a Pomodoro beállításokat a megadott kliensazonosító (User ID) alá.
     */
    fun saveSettings(userId: String, settings: PomodoroSettingsDTO) {
        val key = KEY_PREFIX + userId

        // Mentés a Redisbe, és beállítás a lejárati idő (TTL)
        redisTemplate.opsForValue().set(key, settings, TTL_DAYS, TimeUnit.DAYS)
    }

    /**
     * Betölti a Pomodoro beállításokat a megadott kliensazonosító alá.
     * Ha nincs mentett beállítás, az alapértelmezett DTO-t adja vissza.
     */
    fun loadSettings(userId: String): PomodoroSettingsDTO {
        val key = KEY_PREFIX + userId

        // Megpróbáljuk betölteni az adatot a Redis-ből
        val settings = redisTemplate.opsForValue().get(key)

        // Ha van találat, azt adjuk vissza, különben az alapértelmezett DTO-t (a data class default értékeivel)
        return settings ?: PomodoroSettingsDTO()
    }
}