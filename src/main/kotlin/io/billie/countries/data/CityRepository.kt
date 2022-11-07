package io.billie.countries.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CityRepository : CrudRepository<City, UUID> {
    fun findByCountryCode(countryCode: String): List<City>

    fun findByCountryCodeAndName(countryCode: String, name: String): Optional<City>
}
