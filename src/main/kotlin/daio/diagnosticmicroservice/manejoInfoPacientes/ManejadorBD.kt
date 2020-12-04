package daio.diagnosticmicroservice.manejoInfoPacientes

import com.fasterxml.jackson.databind.ObjectMapper
import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

class ManejadorBD {

    private val dbURI = "http://ec2-18-221-171-183.us-east-2.compute.amazonaws.com:8080/api"

    val restTemplate: RestTemplate = RestTemplate()
    val mapper: ObjectMapper = ObjectMapper()

    fun postRead(read: Read) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(read, headers)
        restTemplate.postForEntity(URI("$dbURI/patients/${read.patientID}/signs/${read.signName}/reads"), requestEntity, Read::class.java)
    }

    fun getPatientSigns(patientID: String): List<Sign>? {
        return restTemplate.getForEntity(URI("$dbURI/patients/${patientID}/signs"), List::class.java).body?.map { mapper.convertValue(it, Sign::class.java) }
    }

    fun putPatientSign(patientID: String, sign: Sign) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.put(URI("$dbURI/patients/${patientID}/signs/${sign.name}"), requestEntity)
    }
}
