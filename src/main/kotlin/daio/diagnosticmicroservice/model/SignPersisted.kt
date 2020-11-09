package daio.diagnosticmicroservice.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(SignPersistedKey::class)
@Table(name = "Sign")
class SignPersisted (
        @Id var type: String,
        @Id var timeEmitted: LocalDateTime,
        @Id @ManyToOne @JsonManagedReference var patient: Patient,
        var value: Double
)