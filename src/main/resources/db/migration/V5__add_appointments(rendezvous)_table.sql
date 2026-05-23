CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    ref VARCHAR(50) NOT NULL UNIQUE,
    motif VARCHAR(100) NOT NULL,
    client_id BIGINT,
    service_id BIGINT,
    responsable_id BIGINT,

    -- Define the foreign key constraint
    CONSTRAINT fk_appointments_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE RESTRICT,

    -- Define the foreign key constraint
    CONSTRAINT fk_appointments_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE RESTRICT,

    -- Define the foreign key constraint
    CONSTRAINT fk_appointments_responsable
        FOREIGN KEY (responsable_id)
        REFERENCES responsables(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_appointment_service ON appointments(service_id);
CREATE INDEX idx_appointment_client ON appointments(client_id);
CREATE INDEX idx_appointment_responsable ON appointments(responsable_id);