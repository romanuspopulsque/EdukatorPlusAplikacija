-- Drop ako već postoji
DROP TABLE IF EXISTS prisustvo;
DROP TABLE IF EXISTS polaznik;
DROP TABLE IF EXISTS radionica;
DROP TYPE IF EXISTS status_prisustva;

-- Enum tip za status
CREATE TYPE status_prisustva AS ENUM ('PRISUTAN', 'ODSUTAN', 'OPRAVDANO', 'NEOPRAVDANO');

-- Tablica Polaznik
CREATE TABLE polaznik (
    id SERIAL PRIMARY KEY,
    ime VARCHAR(50) NOT NULL,
    prezime VARCHAR(50) NOT NULL,
    godina_rodenja INT CHECK (godina_rodenja >= 1900 AND godina_rodenja <= EXTRACT(YEAR FROM CURRENT_DATE)),
    email VARCHAR(100) UNIQUE,
    telefon VARCHAR(20)
);

-- Tablica Radionica
CREATE TABLE radionica (
    id SERIAL PRIMARY KEY,
    naziv VARCHAR(100) NOT NULL,
    opis TEXT,
    datum DATE NOT NULL,
    trajanje INT NOT NULL CHECK (trajanje > 0)
);

-- Tablica Prisustvo
CREATE TABLE prisustvo (
    id SERIAL PRIMARY KEY,
    polaznik_id INT NOT NULL REFERENCES polaznik(id) ON DELETE CASCADE,
    radionica_id INT NOT NULL REFERENCES radionica(id) ON DELETE CASCADE,
    status status_prisustva NOT NULL,
    UNIQUE (polaznik_id, radionica_id) -- Jedan zapis po polaznik-radionica kombinaciji
);

-- Ubacivanje testnih polaznika
INSERT INTO polaznik (ime, prezime, godina_rodenja, email, telefon) VALUES
  ('Ivan', 'Ivić', 1999, 'ivan.ivic@example.com', '0911234567'),
  ('Ana', 'Anić', 2000, 'ana.anic@example.com', '0927654321');

-- Ubacivanje testnih radionica
INSERT INTO radionica (naziv, opis, datum, trajanje) VALUES
  ('Uvod u React', 'Osnove razvoja React aplikacija', '2025-07-01', 3),
  ('Spring Boot API', 'Izrada REST API-ja u Spring Bootu', '2025-08-15', 4);

-- Ubacivanje testnih prisustava
INSERT INTO prisustvo (polaznik_id, radionica_id, status) VALUES
  (1, 1, 'PRISUTAN'),
  (2, 1, 'ODSUTAN'),
  (1, 2, 'OPRAVDANO'),
  (2, 2, 'NEOPRAVDANO');
