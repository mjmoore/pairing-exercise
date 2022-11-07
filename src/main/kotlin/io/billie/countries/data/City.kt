package io.billie.countries.data

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "cities")
class City(

    @Column
    val name: String,

    @Column
    @Size(min = 2, max = 2)
    val countryCode: String,

) {

    @Id
    val id: UUID = UUID.randomUUID()
}
