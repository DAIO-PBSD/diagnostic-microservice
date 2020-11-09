package daio.diagnosticmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiagnosticMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<DiagnosticMicroserviceApplication>(*args)
}
