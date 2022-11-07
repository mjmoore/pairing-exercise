package io.billie.organisations.data

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
class OrganisationRepositoryTest(
    @Autowired private val organisationRepository: OrganisationRepository
) {

    @Test
    fun `Supplied ID is passed to DB`() {
        val newOrg = EntityGenerator.org()
        val savedOrg = organisationRepository.save(newOrg)

        assertThat(newOrg.id).isEqualTo(savedOrg.id)
    }

    @Test
    fun `Can retrieve all organisations`() {

        generateSequence(EntityGenerator::org)
            .take(2)
            .toList()
            .let(organisationRepository::saveAll)

        organisationRepository.findAll()
            .let { assertThat(it.size).isEqualTo(2) }

    }

    @Test
    fun `Children are persisted on save`() {

        val newOrg = EntityGenerator.org()

        val savedOrg = organisationRepository.save(newOrg)
            .let(Organisation::id)
            .let(organisationRepository::findById)
            .orElseThrow()

        assertThat(savedOrg.contactDetails).isNotNull
        assertThat(savedOrg.country).isNotNull

        assertThat(newOrg.country.countryCode).isEqualTo(savedOrg.country.countryCode)
        assertThat(newOrg.contactDetails.email).isEqualTo(savedOrg.contactDetails.email)
    }
}
