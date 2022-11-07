package io.billie.countries.mapper

import io.billie.countries.data.Country
import io.billie.countries.model.CountryResponse
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface CountryMapper {

    fun toResponse(country: Country): CountryResponse
}
