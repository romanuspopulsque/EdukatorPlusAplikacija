-- Ubacivanje polaznika
INSERT INTO polaznik (id, ime, prezime, godina_rodenja, email, telefon)
VALUES 
  (1, 'Ivan', 'Ivić', 1999, 'ivan.ivic@example.com', '0911234567'),
  (2, 'Ana', 'Anić', 2000, 'ana.anic@example.com', '0927654321');

-- Ubacivanje radionica
INSERT INTO radionica (id, naziv, opis, datum, trajanje)
VALUES 
  (1, 'Uvod u React', 'Osnove razvoja React aplikacija', '2025-07-01', 3),
  (2, 'Spring Boot API', 'Izrada REST API-ja u Spring Bootu', '2025-08-15', 4);

-- Ubacivanje prisustva (PRISUTAN i ODSUTAN su enum vrijednosti)
INSERT INTO prisustvo (id, polaznik_id, radionica_id, status)
VALUES 
  (1, 1, 1, 'PRISUTAN'),
  (2, 2, 1, 'ODSUTAN'),
  (3, 1, 2, 'OPRAVDANO'),
  (4, 2, 2, 'NEOPRAVDANO');
