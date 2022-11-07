package io.billie.generators

import io.billie.organisations.viewmodel.ContactDetailsRequest
import io.billie.organisations.viewmodel.LegalEntityType
import io.billie.organisations.viewmodel.OrganisationAddressRequest
import io.billie.organisations.viewmodel.OrganisationRequest
import java.time.LocalDate
import java.time.Month

object RequestGenerator {

    fun org(
        name: String = "test-org",
        dateFounded: LocalDate = LocalDate.of(2022, Month.NOVEMBER, 3),
        legalEntityType: LegalEntityType = LegalEntityType.NONPROFIT_ORGANIZATION,
        contactDetails: ContactDetailsRequest = contactDetails(),
        countryCode: String = "DE",
        vatNumber: String = "12345",
        registrationNumber: String = "12345",
        address: OrganisationAddressRequest? = null
    ): OrganisationRequest = OrganisationRequest(
        name,
        dateFounded,
        countryCode,
        vatNumber,
        registrationNumber,
        legalEntityType,
        contactDetails,
        address
    )

    fun contactDetails(
        phoneNumber: String? = null,
        fax: String? = null,
        email: String? = null
    ): ContactDetailsRequest = ContactDetailsRequest(phoneNumber, fax, email)

    fun address(
        addressLine1: String = "address line one",
        addressLine2: String = "address line two",
        stateOrProvince: String = "Berlin",
        postalCode: String = "10000",
        city: String = "Berlin",
        country: String = "DE"
    ): OrganisationAddressRequest =
        OrganisationAddressRequest(addressLine1, addressLine2, stateOrProvince, postalCode, city, country)

}
