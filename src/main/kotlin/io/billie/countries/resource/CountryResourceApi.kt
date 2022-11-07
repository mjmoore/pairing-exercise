package io.billie.countries.resource

import io.billie.countries.model.CityResponse
import io.billie.countries.model.CountryResponse
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.PathVariable

interface CountryResourceApi {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "All countries",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CountryResponse::class)))
                    ))]
            )]
    )
    fun index(): List<CountryResponse>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Found cities for country",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CityResponse::class)))
                    ))]
            ),
            ApiResponse(responseCode = "404", description = "No cities found for country code", content = [Content()])]
    )
    fun cities(countryCode: String): List<CityResponse>
}
