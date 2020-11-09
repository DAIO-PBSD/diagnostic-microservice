package daio.diagnosticmicroservice.model

import java.io.Serializable
import java.time.LocalDateTime

class SignPersistedKey (
        var type: String = "",
        var timeEmitted: LocalDateTime = LocalDateTime.now(),
        var patient: String = ""
): Serializable