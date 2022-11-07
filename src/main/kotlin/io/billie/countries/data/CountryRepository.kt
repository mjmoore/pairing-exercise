package io.billie.countries.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CountryRepository : CrudRepository<Country, UUID> {

    fun existsByCountryCode(countryCode: String): Boolean

    fun findByCountryCode(countryCode: String): Optional<Country>
}
