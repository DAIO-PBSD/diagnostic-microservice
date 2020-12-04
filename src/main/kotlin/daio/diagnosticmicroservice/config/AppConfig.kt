package daio.diagnosticmicroservice.config

import daio.diagnosticmicroservice.diagnostico.HerramientaDiagnostico
import daio.diagnosticmicroservice.manejadorEventos.SolicitudEventos
import daio.diagnosticmicroservice.manejoInfoPacientes.ManejadorBD
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun manejadorBD(): ManejadorBD = ManejadorBD()
    @Bean
    fun herramientaDiagnostico(): HerramientaDiagnostico = HerramientaDiagnostico()
    @Bean
    fun solicitudEventos(): SolicitudEventos = SolicitudEventos()
}