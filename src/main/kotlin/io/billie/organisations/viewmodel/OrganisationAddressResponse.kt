package io.billie.organisations.viewmodel

import io.billie.countries.model.CityResponse
import io.billie.countries.model.CountryResponse
import java.util.UUID

data class OrganisationAddressResponse(
    val id: UUID,
    val addressLine1: String,
    val addressLine2: String,
    val stateOrProvince: String,
    val postalCode: String,
    val city: CityResponse,
    val country: CountryResponse
)
