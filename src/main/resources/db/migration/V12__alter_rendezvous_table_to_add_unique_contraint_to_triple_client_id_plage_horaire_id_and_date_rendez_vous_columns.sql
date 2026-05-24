ALTER TABLE appointments
ADD CONSTRAINT uk_appointment_clt_plage_date UNIQUE (client_id, plage_horaire_id, date_rendez_vous);