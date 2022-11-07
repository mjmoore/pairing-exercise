package io.billie.organisations.data

import io.billie.countries.data.Country
import io.billie.organisations.viewmodel.LegalEntityType
import java.time.LocalDate
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "organisations")
@NamedEntityGraph(
    name = "organisation",
    attributeNodes = [
        NamedAttributeNode("contactDetails"),
        NamedAttributeNode("country"),
        NamedAttributeNode("address")
    ]
)
data class Organisation(

    @Column
    val name: String,

    @Column
    val dateFounded: LocalDate,

    @Column
    @Enumerated(EnumType.STRING)
    val legalEntityType: LegalEntityType,

    @OneToOne(
        cascade = [CascadeType.MERGE]
    )
    @JoinColumn
    val contactDetails: ContactDetails,

    @ManyToOne
    @JoinColumn(
        name = "country_code",
        referencedColumnName = "countryCode"
    )
    val country: Country,

    @Column
    val vatNumber: String? = null,

    @Column
    val registrationNumber: String? = null,

    @OneToOne(
        cascade = [CascadeType.MERGE]
    )
    @JoinColumn
    val address: OrganisationAddress? = null
) {

    @Id
    val id: UUID = UUID.randomUUID()
}
