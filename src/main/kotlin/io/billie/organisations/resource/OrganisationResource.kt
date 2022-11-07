package io.billie.organisations.resource

import io.billie.organisations.service.OrganisationService
import io.billie.organisations.service.UnableToFindCity
import io.billie.organisations.service.UnableToFindCountry
import io.billie.organisations.service.UnableToFindOrganisation
import io.billie.organisations.viewmodel.*
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("organisations")
class OrganisationResource(
    private val service: OrganisationService
) : OrganisationResourceApi {

    @GetMapping
    override fun index(): List<OrganisationResponse> = service.findOrganisations()

    @PostMapping
    override fun post(@Valid @RequestBody organisation: OrganisationRequest): EntityResponse {
        try {
            return EntityResponse(service.createOrganisation(organisation))
        } catch (e: UnableToFindCountry) {
            throw ResponseStatusException(BAD_REQUEST, e.message)
        }
    }

    @PostMapping("/{organisationId}/address")
    override fun addAddress(
        @PathVariable organisationId: UUID,
        @Valid @RequestBody addressRequest: OrganisationAddressRequest
    ): OrganisationResponse = try {
        service.addAddress(organisationId, addressRequest)
    } catch (e: RuntimeException) {
        when (e) {
            is UnableToFindCity,
            is UnableToFindCountry,
            is UnableToFindOrganisation -> throw ResponseStatusException(BAD_REQUEST, e.message)
            else -> throw e
        }

    }

}
