package hu.studentproductivity.backend.pomodoro.controller

import hu.studentproductivity.backend.pomodoro.model.PomodoroSettingsDTO
import hu.studentproductivity.backend.pomodoro.service.PomodoroService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
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
        // Az anonim azonosítót egy HTTP header-ből (fejlécből) olvassuk ki
        @RequestHeader("X-Client-ID") clientId: String?,
        @RequestBody settings: PomodoroSettingsDTO
    ) {
        // Ellenőrizzük, hogy a clientId megérkezett-e
        if (clientId.isNullOrBlank()) {
            throw IllegalArgumentException("Az X-Client-ID fejléc hiányzik vagy érvénytelen.")
            // Később 400 Bad Request hibát dobni
        }

        pomodoroService.saveSettings(clientId, settings)
    }

    // GET /api/pomodoro/settings
    // A beállítások betöltése
    @GetMapping
    fun loadSettings(
        @RequestHeader("X-Client-ID") clientId: String?
    ): PomodoroSettingsDTO {

        if (clientId.isNullOrBlank()) {
            // Ha hiányzik az ID, visszaadunk egy alapértelmezett beállítást,
            // de a logikának jelezzük, hogy ez nem volt menthető, vagy hibát dobunk.
            // Ebben a példában az alapértelmezettet adjuk, de élesben 400-at javaslok.
            throw IllegalArgumentException("Az X-Client-ID fejléc hiányzik vagy érvénytelen.")
        }

        return pomodoroService.loadSettings(clientId)
    }
}