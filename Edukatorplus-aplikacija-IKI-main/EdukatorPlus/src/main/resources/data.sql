-- Polaznici
INSERT INTO polaznik (ime, prezime, godina_rodenja, email, telefon) VALUES
('Ivan', 'Ivić', 1995, 'ivan.ivic@example.com', '0911234567'),
('Ana', 'Anić', 1997, 'ana.anic@example.com', '0987654321');

-- Radionice
INSERT INTO radionica (naziv, opis, datum, trajanje) VALUES
('React osnove', 'Uvod u React framework', '2025-07-01', 4),
('Spring Boot napredno', 'Napredne tehnike u Spring Bootu', '2025-08-15', 6);

-- Prisustva
-- Pretpostavljamo da su ID-evi polaznika i radionica auto-generated i krenu od 1 redom kako su ubaceni
INSERT INTO prisustvo (radionica_id, polaznik_id, status) VALUES
(1, 1, 'PRISUTAN'),
(2, 2, 'ODSUTAN');
