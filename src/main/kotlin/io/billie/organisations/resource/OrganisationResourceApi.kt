package io.billie.organisations.resource

import io.billie.organisations.viewmodel.EntityResponse
import io.billie.organisations.viewmodel.OrganisationAddressRequest
import io.billie.organisations.viewmodel.OrganisationRequest
import io.billie.organisations.viewmodel.OrganisationResponse
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

interface OrganisationResourceApi {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "All organisations",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = OrganisationResponse::class)))
                    ))]
            )]
    )
    fun index(): List<OrganisationResponse>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Accepted the new organisation",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = EntityResponse::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    fun post(organisation: OrganisationRequest): EntityResponse


    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Accepted the new organisation address",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = OrganisationResponse::class)))
                    ))]
            ),
            ApiResponse(responseCode = "404", description = "Organisation not found", content = [Content()]),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])
        ]
    )
    fun addAddress(organisationId: UUID, addressRequest: OrganisationAddressRequest): OrganisationResponse
}
