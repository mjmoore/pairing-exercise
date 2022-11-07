package io.billie.generators

import io.billie.countries.data.City
import io.billie.countries.data.Country
import io.billie.organisations.data.ContactDetails
import io.billie.organisations.data.Organisation
import io.billie.organisations.data.OrganisationAddress
import io.billie.organisations.viewmodel.LegalEntityType
import java.time.LocalDate
import java.time.Month

object EntityGenerator {

    fun org(
        name: String = "test-org",
        dateFounded: LocalDate = LocalDate.of(2022, Month.NOVEMBER, 3),
        legalEntityType: LegalEntityType = LegalEntityType.NONPROFIT_ORGANIZATION,
        contactDetails: ContactDetails = contactDetails(),
        country: Country = country(),
        registrationNumber: String? = null,
        taxNumber: String? = null,
        address: OrganisationAddress? = null //address()
    ): Organisation =
        Organisation(name, dateFounded, legalEntityType, contactDetails, country, registrationNumber, taxNumber, address)

    fun address(
        addressLine1: String = "address line one",
        addressLine2: String = "address line two",
        stateOrProvince: String = "Berlin",
        postalCode: String = "10000",
        city: City,
        country: Country
    ): OrganisationAddress = OrganisationAddress(addressLine1, addressLine2, stateOrProvince, postalCode, city, country)

    fun country(
        name: String = "Germany",
        countryCode: String = "DE"
    ): Country = Country(name, countryCode)

    fun city(
        name: String = "Berlin",
        countryCode: String = "DE"
    ): City = City(name, countryCode)

    fun contactDetails(): ContactDetails = ContactDetails(
        email = "test@email.org"
    )
}
