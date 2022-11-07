package io.billie.organisations.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrganisationAddressRepository : CrudRepository<OrganisationAddress, UUID> {

    override fun findAll(): List<OrganisationAddress>
}
