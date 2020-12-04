package daio.diagnosticmicroservice.manejadorEventos

import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.net.URI

class SolicitudEventos {

    private val apiReadURI: String = "http://localhost:8081/events"
    private val restTemplate: RestTemplate = RestTemplate()

    fun propagateRead(read: Read) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/reads"), requestEntity, Read::class.java)
    }

    fun sendNormalRead(read: Read) {
        propagateRead(read)
    }

    fun sendWarningRead(read: Read) {
        propagateRead(read)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/reads/warning"), requestEntity, Read::class.java)
    }

    fun sendDangerRead(read: Read) {
        propagateRead(read)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/reads/danger"), requestEntity, Read::class.java)
    }

    fun propagateSignChange(patientId: String, sign: Sign) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.postForEntity(URI("$apiReadURI/patients/$patientId/signs/${sign.name}"), requestEntity, Sign::class.java)
    }
}