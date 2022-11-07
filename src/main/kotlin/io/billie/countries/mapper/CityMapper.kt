package io.billie.countries.mapper

import io.billie.countries.data.City
import io.billie.countries.model.CityResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CityMapper {

    fun toResponse(city: City): CityResponse
}
