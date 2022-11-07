package io.billie.country.data

import io.billie.countries.data.CountryRepository
import io.billie.generators.EntityGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
class CountryRepositoryTest(
    @Autowired private val countryRepository: CountryRepository
) {

    @Test
    fun `Can validate country exists`() {

        val country = countryRepository.save(EntityGenerator.country())

        assertThat(countryRepository.existsByCountryCode(country.countryCode)).isTrue

        assertThat(countryRepository.existsByCountryCode("Not a country code")).isFalse
    }
}
