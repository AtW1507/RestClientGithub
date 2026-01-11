RestClientGithub

Aplikacja Spring Boot pełniąca rolę prostego proxy do GitHub REST API, umożliwiająca pobranie listy repozytoriów użytkownika GitHub, które nie są forkami, wraz z informacjami o branchach i ostatnich commitach.

Projekt został przygotowany jako zadanie rekrutacyjne i spełnia wszystkie przekazane wymagania oraz acceptance criteria.

🎯 Zakres funkcjonalny
Endpoint
GET /users/{username}/repositories


Zwraca listę repozytoriów użytkownika GitHub, które nie są forkami.

Dla każdego repozytorium zwracane są:

repositoryName - nazwa repozytorium

ownerLogin - login właściciela

branches:

name - nazwa brancha

lastCommit - SHA ostatniego commita

Obsługa błędów

Dla nieistniejącego użytkownika GitHub zwracana jest odpowiedź:

HTTP 404

{
  "status": 404,
  "message": "User not found: {username}"
}

🧱 Architektura

Aplikacja posiada prostą architekturę typu proxy:

Controller → Service → Client → GitHub REST API


Założenia architektoniczne:

brak podziału na DTO / modele domenowe

minimalna liczba modeli (record)

wszystkie klasy w jednym pakiecie

brak nadmiarowych warstw i konfiguracji

🛠️ Stack technologiczny

Java 25

Spring Boot 4.0.1

Gradle (Kotlin DSL)

Spring RestClient

JUnit 5

WireMock (testy integracyjne)

🚫 Świadome ograniczenia

Zgodnie z treścią zadania projekt celowo nie zawiera:

WebFlux

paginacji

security

cache

mechanizmów resilience (retry, circuit breaker)

architektury DDD / Hexagonalnej

podziału projektu na moduły

🧪 Testy

Projekt zawiera wyłącznie testy integracyjne.

Charakterystyka:

brak mocków

emulacja GitHub API przy użyciu WireMock

testowane kluczowe przypadki biznesowe:

zwracanie repozytoriów bez forków

obsługa nieistniejącego użytkownika

▶️ Uruchomienie aplikacji
Wymagania

Java 25

Gradle

Uruchomienie
./gradlew bootRun


Domyślny adres GitHub API:

https://api.github.com

📄 Przykładowe wywołanie
GET http://localhost:8080/users/octocat/repositories
