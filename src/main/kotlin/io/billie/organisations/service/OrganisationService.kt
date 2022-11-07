package io.billie.organisations.service

import io.billie.countries.data.City
import io.billie.countries.data.CityRepository
import io.billie.countries.data.Country
import io.billie.countries.data.CountryRepository
import io.billie.organisations.data.OrganisationRepository
import io.billie.organisations.mapper.OrganisationAddressMapper
import io.billie.organisations.mapper.OrganisationMapper
import io.billie.organisations.viewmodel.OrganisationAddressRequest
import io.billie.organisations.viewmodel.OrganisationRequest
import io.billie.organisations.viewmodel.OrganisationResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrganisationService(
    private val organisationRepository: OrganisationRepository,
    private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository,
    private val organisationMapper: OrganisationMapper,
    private val addressMapper: OrganisationAddressMapper
) {

    fun findOrganisations(): List<OrganisationResponse> = organisationRepository
        .findAll()
        .map(organisationMapper::toResponse)

    fun createOrganisation(organisation: OrganisationRequest): UUID {
        val country: Country = findCountry(organisation.countryCode)
        val city: City? = organisation.address?.let(::findCity)

        return organisationMapper.toEntity(organisation, country, city)
            .let { organisationRepository.save(it) }
            .id
    }


    fun addAddress(organisationId: UUID, addressRequest: OrganisationAddressRequest): OrganisationResponse {

        val country = findCountry(addressRequest.country)
        val city = findCity(country.countryCode, addressRequest.city)
        val address = addressMapper.toEntity(addressRequest, country, city)

        return organisationRepository.findById(organisationId)
            .orElseThrow { UnableToFindOrganisation(organisationId) }
            .copy(address = address)
            .let(organisationRepository::save)
            .let(organisationMapper::toResponse)
    }


    private fun findCountry(countryCode: String): Country = countryRepository
        .findByCountryCode(countryCode)
        .orElseThrow { throw UnableToFindCountry(countryCode) }


    private fun findCity(address: OrganisationAddressRequest): City = findCity(address.country, address.city)

    private fun findCity(countryCode: String, cityName: String): City = cityRepository
        .findByCountryCodeAndName(countryCode, cityName)
        .orElseThrow { throw UnableToFindCity(countryCode, cityName) }

}
