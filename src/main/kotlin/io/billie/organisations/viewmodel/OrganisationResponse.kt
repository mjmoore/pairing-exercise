package io.billie.organisations.viewmodel

import io.billie.countries.model.CountryResponse
import java.time.LocalDate
import java.util.UUID

data class OrganisationResponse(
    val id: UUID,
    val name: String,
    val dateFounded: LocalDate,
    val country: CountryResponse,
    val vatNumber: String?,
    val registrationNumber: String?,
    val legalEntityType: LegalEntityType,
    val contactDetails: ContactDetailsResponse,
    val address: OrganisationAddressResponse?
)
