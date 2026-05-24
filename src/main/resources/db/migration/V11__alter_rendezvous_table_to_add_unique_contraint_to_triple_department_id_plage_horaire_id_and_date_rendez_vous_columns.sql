ALTER TABLE appointments
ADD CONSTRAINT uk_appointment_dept_plage_date UNIQUE (service_id, plage_horaire_id, date_rendez_vous);

ALTER TABLE appointments DROP CONSTRAINT IF EXISTS appointments_ref_key;
ALTER TABLE appointments ADD CONSTRAINT uk_appointment_ref UNIQUE (ref);