package es.dylanhurtado.routes

import es.dylanhurtado.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val users = mutableListOf(
    User(1,"Dylan Hurtado",22,"dylanhurtado@dylanhurtado.com"),
    User(2,"Ktor",1,"ktor@ktor.com"),
)

fun Route.userRouting(){
    route("/user"){
        get {
            if(users.isNotEmpty()){
                call.respond(users)
            }else{
                call.respondText("No hay usuarios",status=HttpStatusCode.OK)
            }
        }
        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "id no encontrada",
                status = HttpStatusCode.BadRequest
            )
            val user = users.find{it.id == id.toInt()} ?: return@get call.respondText(
                "Usuario con id $id no encontrado",
                status = HttpStatusCode.NotFound
            )
            call.respond(user)
        }
        post {
            val user = call.receive<User>()
            users.add(user)
            call.respondText("Usuario creado correctamente", status = HttpStatusCode.Created)
        }
        put("{id?}") {
            val user = call.receive<User>()
            val id = (call.parameters["id"] ?: return@put call.respondText(
                "Id introducida no valida",
                status = HttpStatusCode.BadRequest
            ))
            val userFound = users.find { it.id == id.toInt() }?:return@put call.respondText(
            "Usuario no encontrado",
            status = HttpStatusCode.NotFound
        )
            users[users.indexOf(userFound)]  = user

            call.respondText("Usuario con id $id ha sido cambiado", status = HttpStatusCode.Accepted)
        }
        delete("{id?}"){
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "id no encontrada",
                status = HttpStatusCode.BadRequest
            )
            if(users.removeIf({it.id == id.toInt()})){
                call.respondText("Usuario eliminado correctamente", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Usuario con id $id no encontrado",status = HttpStatusCode.NotFound)
            }
        }
    }
}