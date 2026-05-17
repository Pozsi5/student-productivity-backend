package hu.studentproductivity.backend.pomodoro.controller

import hu.studentproductivity.backend.pomodoro.model.PomodoroSettingsDTO
import hu.studentproductivity.backend.pomodoro.service.PomodoroService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pomodoro/settings")
class PomodoroController(
    private val pomodoroService: PomodoroService
) {

    // A beállítások mentése
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun saveSettings(
        // Az X-Client-ID helyett elkérjük a hitelesített JWT tokent a Spring Security-től
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody settings: PomodoroSettingsDTO
    ) {
        // A jwt.subject tartalmazza a Keycloak (és ezáltal a Google) által generált,
        // teljesen egyedi és állandó felhasználói azonosítót (UUID-t szövegként).
        val userId = jwt.subject

        // Meghívjuk a service-t az egyedi felhasználói ID-val
        pomodoroService.saveSettings(userId, settings)
    }

    // GET /api/pomodoro/settings
    // A beállítások betöltése
    @GetMapping
    fun loadSettings(
        @AuthenticationPrincipal jwt: Jwt
    ): PomodoroSettingsDTO {

        val userId = jwt.subject

        return pomodoroService.loadSettings(userId)
    }
}