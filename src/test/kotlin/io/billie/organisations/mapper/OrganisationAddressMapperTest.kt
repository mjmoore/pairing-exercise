package io.billie.organisations.mapper

import io.billie.countries.mapper.CityMapper
import io.billie.countries.mapper.CountryMapper
import io.billie.generators.EntityGenerator
import io.billie.generators.RequestGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class OrganisationAddressMapperTest {

    private val cityMapper = Mappers.getMapper(CityMapper::class.java)
    private val countryMapper = Mappers.getMapper(CountryMapper::class.java)

    private val addressMapper = OrganisationAddressMapper(cityMapper, countryMapper)

    private val country = EntityGenerator.country()
    private val city = EntityGenerator.city()
    private val addressRequest = RequestGenerator.address()
    private val addressEntity = EntityGenerator.address(city = city, country = country)

    @Test
    fun `Can map from request to entity`() {

        addressMapper.toEntity(addressRequest, country, city)
            .also { address ->
                assertThat(address.country.id).isEqualTo(country.id)
                assertThat(address.city.id).isEqualTo(city.id)
            }
    }

    @Test
    fun `Can map from entity to response`() {

        addressMapper.toResponse(addressEntity)
            .also { address ->
                assertThat(address?.city?.name).isEqualTo(addressEntity.city.name)
                assertThat(address?.country?.name).isEqualTo(addressEntity.country.name)
            }

    }
}
