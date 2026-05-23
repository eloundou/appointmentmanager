ALTER TABLE appointments
ADD COLUMN plage_horaire_id BIGINT;

ALTER TABLE appointments
ADD CONSTRAINT fk_appointments_plage_horaire
        FOREIGN KEY (plage_horaire_id)
        REFERENCES plage_horaires(id)
        ON DELETE RESTRICT;

CREATE INDEX idx_appointment_plage_horaire ON appointments(plage_horaire_id);