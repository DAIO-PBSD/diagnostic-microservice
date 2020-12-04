package daio.diagnosticmicroservice.diagnostico

import daio.diagnosticmicroservice.model.Read
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.web.client.RestTemplate
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/reads")
class ServicioDiagnostico: ApplicationContextAware {

    private lateinit var herrDiagnostico: HerramientaDiagnostico

    override fun setApplicationContext(context: ApplicationContext) {
        this.herrDiagnostico = context.getBean(HerramientaDiagnostico::class.java)
    }

    @PostMapping
    fun receiveRead(@RequestBody read: Read, restTemplate: RestTemplate) {
        read.sanitize()
        if (!read.isValid())
            return

        herrDiagnostico.receiveRead(read)
    }
}