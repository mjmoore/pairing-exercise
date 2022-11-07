package io.billie.countries.service

import io.billie.countries.data.CityRepository
import io.billie.countries.data.CountryRepository
import io.billie.countries.mapper.CityMapper
import io.billie.countries.mapper.CountryMapper
import io.billie.countries.model.CityResponse
import io.billie.countries.model.CountryResponse
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class CountryService(
    private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository,
) {

    private val countryMapper = Mappers.getMapper(CountryMapper::class.java)
    private val cityMapper = Mappers.getMapper(CityMapper::class.java)

    fun findCountries(): List<CountryResponse> = countryRepository
        .findAll()
        .map(countryMapper::toResponse)

    fun findCities(countryCode: String): List<CityResponse> = cityRepository
        .findByCountryCode(countryCode)
        .map(cityMapper::toResponse)

}
