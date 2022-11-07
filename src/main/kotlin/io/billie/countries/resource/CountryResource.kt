package io.billie.countries.resource

import io.billie.countries.model.CityResponse
import io.billie.countries.model.CountryResponse
import io.billie.countries.service.CountryService
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("countries")
class CountryResource(
    private val service: CountryService
) : CountryResourceApi {

    @GetMapping
    override fun index(): List<CountryResponse> = service.findCountries()

    @GetMapping("/{countryCode}/cities")
    override fun cities(@PathVariable("countryCode") countryCode: String): List<CityResponse> {
        val cities = service.findCities(countryCode.uppercase())
        if (cities.isEmpty()) {
            throw ResponseStatusException(
                NOT_FOUND,
                "No cities found for $countryCode"
            )
        }
        return cities
    }

}
