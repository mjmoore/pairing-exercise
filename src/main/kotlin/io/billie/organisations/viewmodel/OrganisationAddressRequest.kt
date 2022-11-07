package io.billie.organisations.viewmodel

import javax.validation.constraints.NotBlank

data class OrganisationAddressRequest(
    @field:NotBlank val addressLineOne: String,
    val addressLineTwo: String,
    val stateOrProvince: String,
    val postalCode: String,
    @field:NotBlank val city: String,
    @field:NotBlank val country: String
)
