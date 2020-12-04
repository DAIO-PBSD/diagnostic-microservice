package daio.diagnosticmicroservice.manejadorEventos

import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.net.URI

class SolicitudEventos {

    private val apiReadURI: String = "http://ec2-18-191-67-195.us-east-2.compute.amazonaws.com:8080/events"
    private val restTemplate: RestTemplate = RestTemplate()

    fun sendNormalRead(read: Read) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/patients/${read.patientID}/reads/normal"), requestEntity, Read::class.java)
    }

    fun sendWarningRead(read: Read) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/patients/${read.patientID}/reads/warning"), requestEntity, Read::class.java)
    }

    fun sendDangerRead(read: Read) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$apiReadURI/patients/${read.patientID}/reads/danger"), requestEntity, Read::class.java)
    }

    fun propagateSignChange(patientId: String, sign: Sign) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.postForEntity(URI("$apiReadURI/patients/$patientId/signs/${sign.name}"), requestEntity, Sign::class.java)
    }
}