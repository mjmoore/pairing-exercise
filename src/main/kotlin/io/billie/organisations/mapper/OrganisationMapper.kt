package io.billie.organisations.mapper

import io.billie.countries.data.City
import io.billie.countries.data.Country
import io.billie.countries.mapper.CountryMapper
import io.billie.organisations.data.Organisation
import io.billie.organisations.viewmodel.OrganisationRequest
import io.billie.organisations.viewmodel.OrganisationResponse
import org.springframework.stereotype.Component

@Component
class OrganisationMapper(
    private val addressMapper: OrganisationAddressMapper,
    private val countryMapper: CountryMapper,
    private val contactDetailsMapper: ContactDetailsMapper
) {

    fun toResponse(org: Organisation): OrganisationResponse = with(org) {
        OrganisationResponse(
            id,
            name,
            dateFounded,
            countryMapper.toResponse(country),
            vatNumber,
            registrationNumber,
            legalEntityType,
            contactDetailsMapper.toResponse(contactDetails),
            addressMapper.toResponse(address)
        )
    }

    fun toEntity(organisationRequest: OrganisationRequest, country: Country, city: City?): Organisation =
        with(organisationRequest) {
            Organisation(
                name,
                dateFounded,
                legalEntityType,
                contactDetailsMapper.toEntity(contactDetails),
                country,
                vatNumber,
                registrationNumber,
                addressMapper.toNullableEntity(address, country, city)
            )
        }
}
