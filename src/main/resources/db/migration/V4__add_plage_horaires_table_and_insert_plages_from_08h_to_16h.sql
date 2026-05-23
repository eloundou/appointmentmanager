CREATE TABLE plage_horaires (
    id BIGSERIAL PRIMARY KEY,
    debut TIME NOT NULL,
    fin TIME NOT NULL,
    libelle VARCHAR(50) NOT NULL,
    CONSTRAINT unique_slot UNIQUE (debut, fin)
);

INSERT INTO plage_horaires (debut, fin, libelle) VALUES
('08:00:00', '09:00:00', '08h'),
('09:00:00', '10:00:00', '09h'),
('10:00:00', '11:00:00', '10h'),
('11:00:00', '12:00:00', '11h'),
('12:00:00', '13:00:00', '12h'),
('13:00:00', '14:00:00', '13h'),
('14:00:00', '15:00:00', '14h'),
('15:00:00', '16:00:00', '15h'),
('16:00:00', '17:00:00', '16h')