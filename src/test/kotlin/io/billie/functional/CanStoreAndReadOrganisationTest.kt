package io.billie.functional

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.billie.countries.data.CityRepository
import io.billie.countries.data.CountryRepository
import io.billie.functional.data.Fixtures.orgRequestJson
import io.billie.functional.data.Fixtures.orgRequestJsonCountryCodeBlank
import io.billie.functional.data.Fixtures.orgRequestJsonCountryCodeIncorrect
import io.billie.functional.data.Fixtures.orgRequestJsonNameBlank
import io.billie.functional.data.Fixtures.orgRequestJsonNoContactDetails
import io.billie.functional.data.Fixtures.orgRequestJsonNoCountryCode
import io.billie.functional.data.Fixtures.orgRequestJsonNoLegalEntityType
import io.billie.functional.data.Fixtures.orgRequestJsonNoName
import io.billie.generators.RequestGenerator
import io.billie.organisations.data.ContactDetails
import io.billie.organisations.data.ContactDetailsRepository
import io.billie.organisations.data.Organisation
import io.billie.organisations.data.OrganisationAddressRepository
import io.billie.organisations.data.OrganisationRepository
import io.billie.organisations.viewmodel.EntityResponse
import io.billie.organisations.viewmodel.OrganisationResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import javax.transaction.Transactional


@Transactional
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = DEFINED_PORT)
class CanStoreAndReadOrganisationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mapper: ObjectMapper,
    @Autowired private val organisationRepository: OrganisationRepository,
    @Autowired private val contactDetailsRepository: ContactDetailsRepository,
    @Autowired private val addressRepository: OrganisationAddressRepository,
    @Autowired private val cityRepository: CityRepository,
    @Autowired private val countryRepository: CountryRepository

) {

    @LocalServerPort
    private val port = 8080


    @Test
    fun orgs() {
        mockMvc.perform(
            get("/organisations")
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
    }

    @Test
    fun cannotStoreOrgWhenNameIsBlank() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonNameBlank())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenNameIsMissing() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonNoName())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenCountryCodeIsMissing() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonNoCountryCode())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenCountryCodeIsBlank() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonCountryCodeBlank())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenCountryCodeIsNotRecognised() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonCountryCodeIncorrect())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenNoLegalEntityType() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonNoLegalEntityType())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun cannotStoreOrgWhenNoContactDetails() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJsonNoContactDetails())
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun canStoreOrg() {
        val result = mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJson())
        )
            .andExpect(status().isOk)
            .andReturn()

        val response: EntityResponse = mapper.readValue(result.response.contentAsString)

        val orgContactDetails: ContactDetails = response.id
            .let(organisationRepository::findById)
            .map(Organisation::contactDetails)
            .orElseThrow()

        val contactDetails = contactDetailsRepository.findById(orgContactDetails.id)
            .orElseThrow()

        assertThat(contactDetails).usingRecursiveComparison().isEqualTo(orgContactDetails)
    }

    @Test
    fun `Organisation address is not mandatory`() {
        mockMvc.perform(
            post("/organisations").contentType(APPLICATION_JSON).content(orgRequestJson())
        )
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `Can add an address to a new organisation`() {
        val organisation = RequestGenerator.org(address = RequestGenerator.address())
            .let(mapper::writeValueAsString)
            .let { request ->
                mockMvc.perform(
                    post("/organisations").contentType(APPLICATION_JSON).content(request)
                )
            }
            .andExpect(status().isOk)
            .andReturn()
            .readValue<EntityResponse>()
            .id
            .let(organisationRepository::findById)
            .orElseThrow()

        assertThat(organisation.address).isNotNull

        organisation.address?.id
            ?.let(addressRepository::findById)
            ?.orElseThrow()
            .also { address -> assertThat(address).isNotNull }
    }

    @Test
    fun `Can add an address to an existing organisation`() {
        val orgResult: EntityResponse = RequestGenerator.org()
            .let(mapper::writeValueAsString)
            .let { request ->
                mockMvc.perform(
                    post("/organisations").contentType(APPLICATION_JSON).content(request)
                )
            }
            .andExpect(status().isOk)
            .andReturn()
            .readValue()

        val country = countryRepository.findByCountryCode("DE").orElseThrow()
        val city = cityRepository.findByCountryCodeAndName("DE", "Berlin").orElseThrow()

        val addressJson = RequestGenerator.address(city = city.name, country = country.countryCode)
            .let(mapper::writeValueAsString)

        val organisation: OrganisationResponse = mockMvc.perform(
            post("/organisations/${orgResult.id}/address")
                .contentType(APPLICATION_JSON)
                .content(addressJson)
        )
            .andExpect(status().isOk)
            .andReturn()
            .readValue()

        assertThat(organisation.address).isNotNull

    }


    private inline fun <reified T> MvcResult.readValue(): T = mapper.readValue(response.contentAsString)
}
