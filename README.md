# Basic Ktor API

<a href="https://ktor.io/" target="_blank">
  <img src="https://learn.vonage.com/content/blog/build-a-2fa-server-with-kotlin-and-ktor/kotlin_ktor_2fa_1200x600_white.png" width="100%"  />
</a>

## Models :

```kotlin
@Serializable
data class User(val id: Int, val name:String, val age: Int , val email:String)
```

## Basic CRUD
 
 ### GetAll
```kotlin
      get {
          if(users.isNotEmpty()){
              call.respond(users)
          }else{
              call.respondText("No hay usuarios",status=HttpStatusCode.OK)
          }
      }
```

 ### GetById
```kotlin
     get("{id?}"){
          val id = call.parameters["id"] ?: return@get call.respondText("id no encontrada",status = HttpStatusCode.BadRequest)
          val user = users.find{it.id == id.toInt()} ?: return@get call.respondText("Usuario con id $id no encontrado", status = HttpStatusCode.NotFound)
         call.respond(user)
    }
```

 ### Post(Add User)
```kotlin
 post {
      val user = call.receive<User>()
      users.add(user)
      call.respondText("Usuario creado correctamente", status = HttpStatusCode.Created)
      }
```

 ### Put(Mod User)
```kotlin
 put("{id?}") {
      val user = call.receive<User>()
      val id = (call.parameters["id"] ?: return@put call.respondText("Id introducida no valida", status = HttpStatusCode.BadRequest))
      val userFound = users.find { it.id == id.toInt() }?:return@put call.respondText(
      "Usuario no encontrado",
      status = HttpStatusCode.NotFound
  )
      users[users.indexOf(userFound)]  = user

      call.respondText("Usuario con id $id ha sido cambiado", status = HttpStatusCode.Accepted)
    }
```

 ### Delete
```kotlin
 delete("{id?}"){
        val id = call.parameters["id"] ?: return@delete call.respondText("id no encontrada", status = HttpStatusCode.BadRequest)
        if(users.removeIf({it.id == id.toInt()})){
            call.respondText("Usuario eliminado correctamente", status = HttpStatusCode.Accepted)
        }else{
            call.respondText("Usuario con id $id no encontrado",status = HttpStatusCode.NotFound)
        }
    }
```
