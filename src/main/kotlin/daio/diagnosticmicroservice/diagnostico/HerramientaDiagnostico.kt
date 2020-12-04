package daio.diagnosticmicroservice.diagnostico

import daio.diagnosticmicroservice.manejadorEventos.SolicitudEventos
import daio.diagnosticmicroservice.manejoInfoPacientes.ManejadorBD
import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class HerramientaDiagnostico: ApplicationContextAware {

    private lateinit var manejadorBD: ManejadorBD
    private lateinit var solicitudEventos: SolicitudEventos

    override fun setApplicationContext(context: ApplicationContext) {
        this.manejadorBD = context.getBean(ManejadorBD::class.java)
        this.solicitudEventos = context.getBean(SolicitudEventos::class.java)
    }

    private val patientSigns: HashMap<String, List<Sign>> = HashMap()

    fun getPatientSigns (patientID: String): List<Sign>? {
        if (patientSigns.containsKey(patientID))
            return patientSigns.get(patientID)
        val signs = manejadorBD.getPatientSigns(patientID)
        if (signs != null)
            patientSigns.set(patientID, signs)
        return signs
    }

    fun getPatientSign (patientID: String, signName: String): Sign? {
        val signs: List<Sign> = getPatientSigns(patientID) ?: return null
        return signs.find { it.name == signName}
    }

    fun verifyEmergencyLevels(read: Read) {
        val sign = getPatientSign(read.patientID, read.signName)
        if (sign == null) {
            println("Couldn't find Sign for ${read.signName} in DB")
            solicitudEventos.sendNormalRead(read)
            return
        }
        if (sign.extremeLow == sign.low && sign.low == sign.high && sign.high == sign.extremeHigh) {
            solicitudEventos.sendNormalRead(read)
            return
        }
        when (read.value) {
            in sign.extremeLow..sign.low -> solicitudEventos.sendWarningRead(read)
            in sign.high..sign.extremeHigh -> solicitudEventos.sendWarningRead(read)
            in sign.low..sign.high -> solicitudEventos.sendNormalRead(read)
            else -> solicitudEventos.sendDangerRead(read)
        }
    }

    fun receiveRead (read: Read) {
        verifyEmergencyLevels(read)
        manejadorBD.postRead(read)
    }

    fun putPatientSign(patientId: String, sign: Sign) {
        return manejadorBD.putPatientSign(patientId, sign)
    }
}