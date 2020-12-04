package daio.diagnosticmicroservice.manejadorEventos

import daio.diagnosticmicroservice.diagnostico.HerramientaDiagnostico
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/events")
class ServicioEventos: ApplicationContextAware {

    private lateinit var solicitudEventos: SolicitudEventos
    private lateinit var herrDiagnostico: HerramientaDiagnostico

    override fun setApplicationContext(context: ApplicationContext) {
        this.solicitudEventos = context.getBean(SolicitudEventos::class.java)
        this.herrDiagnostico = context.getBean(HerramientaDiagnostico::class.java)
    }

    @GetMapping("/patients/{patient_id}/signs")
    fun getPatientSigns(@PathVariable patient_id: String) = herrDiagnostico.getPatientSigns(patient_id)

    @PutMapping("/patients/{patient_id}/signs/{sign_name}")
    fun putPatientSign(@PathVariable patient_id: String, @PathVariable sign_name: String, @RequestBody sign: Sign): Unit? {
        sign.name = sign_name
        sign.sanitize()
        if (!sign.isValid())
            return null
        println("Putting Patient Sign")
        herrDiagnostico.putPatientSign(patient_id, sign)
        return solicitudEventos.propagateSignChange(patient_id, sign)
    }
}