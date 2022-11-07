package io.billie.organisations.mapper

import io.billie.countries.data.City
import io.billie.countries.data.Country
import io.billie.countries.mapper.CityMapper
import io.billie.countries.mapper.CountryMapper
import io.billie.organisations.data.OrganisationAddress
import io.billie.organisations.viewmodel.OrganisationAddressRequest
import io.billie.organisations.viewmodel.OrganisationAddressResponse
import org.springframework.stereotype.Component

@Component
class OrganisationAddressMapper(
    private val cityMapper: CityMapper,
    private val countryMapper: CountryMapper,
) {

    fun toNullableEntity(
        orgAddress: OrganisationAddressRequest?,
        country: Country,
        orgCity: City?
    ): OrganisationAddress? = orgAddress?.let { address ->
        orgCity?.let { city -> toEntity(address, country, city) }
    }

    fun toEntity(
        address: OrganisationAddressRequest,
        country: Country,
        city: City
    ): OrganisationAddress =
        with(address) {
            OrganisationAddress(addressLineOne, addressLineTwo, stateOrProvince, postalCode, city, country)
        }

    fun toResponse(orgAddress: OrganisationAddress?): OrganisationAddressResponse? = orgAddress?.let { address ->
        with(address) {
            OrganisationAddressResponse(
                id,
                addressLineOne,
                addressLineTwo,
                stateOrProvince,
                postalCode,
                cityMapper.toResponse(address.city),
                countryMapper.toResponse(address.country)
            )
        }
    }
}
