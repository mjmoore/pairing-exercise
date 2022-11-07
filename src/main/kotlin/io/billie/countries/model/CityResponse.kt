package io.billie.countries.model

import java.util.UUID
import javax.validation.constraints.Size

data class CityResponse(
    val id: UUID,
    val name: String,
    val countryCode: String
)
