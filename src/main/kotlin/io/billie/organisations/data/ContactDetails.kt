package io.billie.organisations.data

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "contact_details")
class ContactDetails(

    @Column
    val phoneNumber: String? = null,

    @Column
    val fax: String? = null,

    @Column
    val email: String? = null
) {

    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID()
}
