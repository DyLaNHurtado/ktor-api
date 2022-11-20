package es.dylanhurtado

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import es.dylanhurtado.plugins.*

fun main() {
    embeddedServer(Netty, port = 8888, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
