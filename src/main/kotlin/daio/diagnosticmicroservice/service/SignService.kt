package daio.diagnosticmicroservice.service

import daio.diagnosticmicroservice.model.Sign
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/signs")
class SignService() {
    @PostMapping
    fun postSigns(@RequestBody sign: Sign) {
        println(sign)
    }
}