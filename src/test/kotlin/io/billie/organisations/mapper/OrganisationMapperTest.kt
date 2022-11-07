package io.billie.organisations.mapper

import io.billie.countries.mapper.CityMapper
import io.billie.countries.mapper.CountryMapper
import io.billie.generators.EntityGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class OrganisationMapperTest {
    private val cityMapper = Mappers.getMapper(CityMapper::class.java)
    private val countryMapper = Mappers.getMapper(CountryMapper::class.java)

    private val contactDetailsMapper = ContactDetailsMapper()
    private val addressMapper = OrganisationAddressMapper(cityMapper, countryMapper)
    private val orgMapper = OrganisationMapper(addressMapper, countryMapper, contactDetailsMapper)


    @Test
    fun `Can map entity to response`() {
        val orgEntity = EntityGenerator.org()
        orgMapper.toResponse(orgEntity)
            .also {
                assertThat(it.country.name).isEqualTo(orgEntity.country.name)
            }
    }

    @Test
    fun `Can map entity to response when no address exists`() {
        val orgEntity = EntityGenerator.org(address = null)
        orgMapper.toResponse(orgEntity)
            .also {
                assertThat(it.address).isNull()
            }
    }

    @Test
    fun `Can map response to entity`() {

    }

    @Test
    fun `Can map response to entity when no address is given`() {

    }
}
