package io.billie.organisations.data

import io.billie.countries.data.City
import io.billie.countries.data.Country
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.Table

@Entity
@Table(name = "organisation_addresses")
@NamedEntityGraph(
    name = "address",
    attributeNodes = [
        NamedAttributeNode("city"),
        NamedAttributeNode("country")
    ]
)
class OrganisationAddress(
    @Column
    val addressLineOne: String,

    @Column
    val addressLineTwo: String,

    @Column
    val stateOrProvince: String,

    @Column
    val postalCode: String,

    @ManyToOne(cascade = [CascadeType.MERGE])
    val city: City,

    @ManyToOne(cascade = [CascadeType.MERGE])
    val country: Country
) {
    @Id
    val id: UUID = UUID.randomUUID()
}
