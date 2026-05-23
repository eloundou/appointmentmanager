CREATE TABLE responsables (
    id BIGSERIAL PRIMARY KEY,
    ref VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone INTEGER,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    service_id BIGINT,

    -- Define the foreign key constraint
    CONSTRAINT fk_responsable_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_responsable_service ON responsables(service_id);