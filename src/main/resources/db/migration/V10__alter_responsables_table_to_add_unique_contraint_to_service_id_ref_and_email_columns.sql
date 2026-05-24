ALTER TABLE responsables DROP CONSTRAINT IF EXISTS responsables_ref_key;
ALTER TABLE responsables ADD CONSTRAINT uk_responsable_ref UNIQUE (ref);

ALTER TABLE responsables DROP CONSTRAINT IF EXISTS responsables_email_key;
ALTER TABLE responsables ADD CONSTRAINT uk_responsable_email UNIQUE (email);

ALTER TABLE responsables ADD CONSTRAINT uk_responsable_service_id UNIQUE (service_id);