alter table organisations_schema.organisations
    alter column contact_details_id type uuid using contact_details_id::uuid;
