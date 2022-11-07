package io.billie.organisations.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ContactDetailsRepository : CrudRepository<ContactDetails, UUID>
