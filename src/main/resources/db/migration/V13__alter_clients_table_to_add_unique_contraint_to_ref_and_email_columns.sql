ALTER TABLE clients DROP CONSTRAINT IF EXISTS clients_ref_key;
ALTER TABLE clients ADD CONSTRAINT uk_clients_ref UNIQUE (ref);

ALTER TABLE clients DROP CONSTRAINT IF EXISTS clients_email_key;
ALTER TABLE clients ADD CONSTRAINT uk_clients_email UNIQUE (email);