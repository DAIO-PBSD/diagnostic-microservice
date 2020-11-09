package daio.diagnosticmicroservice.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class Patient (
        @Id var id: String,
        @OneToMany @JsonBackReference var signs: List<SignPersisted> = listOf()
)