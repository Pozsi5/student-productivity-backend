package hu.studentproductivity.backend.pomodoro.model

// A Pomodoro beállításokat tartalmazó adatmodell.
data class PomodoroSettingsDTO(
    // Munkaidő hossza percben (alapértelmezett: 25 perc)
    val workDurationMin: Int = 25,
    // Rövid szünet hossza percben (alapértelmezett: 5 perc)
    val shortBreakDurationMin: Int = 5,
    // Hosszú szünet hossza percben (alapértelmezett: 15 perc)
    val longBreakDurationMin: Int = 15,
    // Rövid szünetek száma a hosszú szünet előtt (alapértelmezett: 4 ciklus)
    val loops: Int = 4
)