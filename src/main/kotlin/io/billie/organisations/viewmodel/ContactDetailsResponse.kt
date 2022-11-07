package io.billie.organisations.viewmodel

import java.util.UUID

data class ContactDetailsResponse(
    val id: UUID,
    val phoneNumber: String?,
    val fax: String?,
    val email: String?
)
