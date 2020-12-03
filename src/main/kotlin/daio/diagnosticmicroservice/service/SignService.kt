package daio.diagnosticmicroservice.service

import daio.diagnosticmicroservice.model.Sign
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.net.URI

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/signs")
class SignService() {
    @PostMapping
    fun postSigns(@RequestBody sign: Sign, restTemplate: RestTemplate) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.postForEntity(URI("http://ec2-18-191-67-195.us-east-2.compute.amazonaws.com:8080/SMP/signs"), requestEntity, Sign::class.java)
    }
}