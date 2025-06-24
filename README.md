# Edukator+

**Edukator+** je Java Spring Boot aplikacija za upravljanje edukacijskim radionicama u neprofitnim organizacijama. Omogućuje evidenciju polaznika, radionica i prisustava putem REST API-ja.

Aplikacija je izrađena kao projektni zadatak za kolegij **Informacijsko-komunikacijska infrastruktura** (FFOS, Odsjek za informacijske znanosti), pod mentorstvom **izv. prof. dr. sc. Tomislava Jakopeca**, koristeći znanja stečena kroz taj kolegij, kao i kroz kolegij **Programiranje 2 (P2)**.

---

## Funkcionalnosti

- CRUD operacije za:
  - Radionice
  - Polaznike
  - Prisustva
- Validacija unosa s jasnim prikazom korisničkih grešaka
- Statistika: prikaz broja prisutnih i odsutnih polaznika po radionici
- Filtriranje polaznika po različitim kriterijima (npr. godini rođenja, imenu, prezimenu, emailu)
- Pretraga polaznika po imenu, prezimenu i emailu
- Generiranje testnih podataka pomoću biblioteke Faker
- Swagger (OpenAPI) dokumentacija dostupna za sve API rute, olakšavajući integraciju i testiranje
- Relacijsko upravljanje podacima (prisustva povezana s polaznicima i radionicama)
- Softverski slojevi razdvojeni po dobrim praksama: kontroleri, servisi, repozitoriji
- Jednostavna integracija s bazom podataka (MySQL) putem Spring Data JPA i Hibernate-a
- Modularnost i proširivost aplikacije za buduće nadogradnje
- (Opcionalno) Sigurnosni mehanizmi poput autentikacije, autorizacije i CORS konfiguracije

---

## Tehnologije

- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Swagger UI (springdoc-openapi)  
- Maven  

---

## Pokretanje i testiranje aplikacije

1. Kloniraj ili otvori projekt u IDE-u (npr. NetBeans, IntelliJ IDEA).
2. Kreiraj bazu podataka u MySQL-u:
   ```sql
   CREATE DATABASE edukatorplus;
3. Ažuriraj konfiguraciju baze podataka u src/main/resources/application.properties (korisničko ime, lozinka, URL).

4. Pokreni aplikaciju (npr. desni klik na Start.java → Run).

5. Otvori Swagger UI za isprobavanje API-ja:
http://localhost:8080/swagger-ui/index.html

Autor
Roman Šimunović
📧 romansimunovic21@gmail.com
📍 Osijek, 2025.
