package io.billie.organisations.viewmodel

import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class OrganisationRequest(
    @field:NotBlank
    val name: String,
    val dateFounded: LocalDate,
    @field:NotBlank
    val countryCode: String,
    val vatNumber: String?,
    val registrationNumber: String?,
    val legalEntityType: LegalEntityType,
    val contactDetails: ContactDetailsRequest,
    val address: OrganisationAddressRequest? = null
)
