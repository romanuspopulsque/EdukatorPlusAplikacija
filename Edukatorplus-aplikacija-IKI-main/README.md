# Edukator+

**Edukator+** je Java Spring Boot aplikacija za upravljanje edukacijskim radionicama u neprofitnim organizacijama. Omogućuje evidenciju polaznika, radionica i prisustava putem REST API-ja.

Aplikacija je izrađena kao projektni zadatak za kolegij **Informacijsko-komunikacijska infrastruktura** (FFOS, Odsjek za informacijske znanosti), pod mentorstvom **izv. prof. dr. sc. Tomislava Jakopeca**, koristeći znanja stečena kroz taj kolegij, kao i kroz kolegij **Programiranje 2 (P2)**.

## Funkcionalnosti

- CRUD operacije za:
  - Radionice
  - Polaznike
  - Prisustva
- Validacija unosa s prikazom korisničkih grešaka
- Statistika: broj prisutnih i odsutnih po radionici
- Filtriranje polaznika po godini rođenja
- Generiranje testnih podataka pomoću Fakera
- Swagger (OpenAPI) dokumentacija za sve rute

## Tehnologije

- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Swagger UI (springdoc-openapi)  
- Maven

## Pokretanje i testiranje aplikacije

1. Kloniraj ili otvori projekt u NetBeansu.  
2. Kreiraj bazu u MySQL-u:
   ```sql
   CREATE DATABASE edukatorplus;
   ```
3. Ažuriraj `application.properties` s vlastitim DB podacima.
4. Pokreni aplikaciju desnim klikom na `Start.java` → `Run`.
5. Testiraj API putem Swagger sučelja:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

**Autor:**  
Roman Šimunović  
📧 romansimunovic21@gmail.com  
📍 Osijek, 2025.
