# Student Productivity - Backend (Kotlin + Spring Boot)

## Rövid leírás
Ez a backend szolgáltatása a Student Productivity frontendhez. REST API-t szolgáltat a kliens számára.

## Felépítés
- Kotlin + Spring Boot
- Gradle
- Spring Data JPA

## Lokális futtatás
TODO

## Redis tartalmának megtekintése
- Redis futtatása: redis-cli
- Összes kulcs lekérdezése: KEYS pomodoro:settings:*
- ID alapján lekérni: GET pomodoro:settings:{id}