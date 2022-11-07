package io.billie.organisations.data

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrganisationRepository : CrudRepository<Organisation, UUID> {

    @EntityGraph("organisation")
    override fun findAll(): List<Organisation>
}
