CREATE TABLE organisations_schema.organisation_addresses
(
    id                UUID PRIMARY KEY,
    address_line_one  VARCHAR NOT NULL,
    address_line_two  VARCHAR NOT NULL,
    state_or_province VARCHAR NOT NULL,
    postal_code       VARCHAR NOT NULL,
    city_id           UUID references organisations_schema.cities (id),
    country_id        UUID references organisations_schema.countries (id)
);

alter table organisations_schema.organisations
    add column address_id UUID references organisations_schema.organisation_addresses (id)
