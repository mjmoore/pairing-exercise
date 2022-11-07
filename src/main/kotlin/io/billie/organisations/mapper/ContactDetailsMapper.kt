package io.billie.organisations.mapper

import io.billie.organisations.data.ContactDetails
import io.billie.organisations.viewmodel.ContactDetailsRequest
import io.billie.organisations.viewmodel.ContactDetailsResponse
import org.springframework.stereotype.Component

@Component
class ContactDetailsMapper {

    fun toEntity(details: ContactDetailsRequest): ContactDetails = with(details) {
        ContactDetails(phoneNumber, fax, email)
    }

    fun toResponse(contactDetails: ContactDetails): ContactDetailsResponse = with(contactDetails) {
        ContactDetailsResponse(id, phoneNumber, fax, email)
    }
}

