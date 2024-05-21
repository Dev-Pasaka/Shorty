package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.dto.project.CreateProject
import com.example.domain.usecase.project.CreateProjectUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.createProject(){
    authenticate {
        post("${ServerConfig.apiVersion}/project"){
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val project = call.receive<CreateProject>()
            val createProjectResult = CreateProjectUseCase().createProject(
                email = email,
                project = project
            )
            call.respond(
                status = HttpStatusCode(value = createProjectResult.httpStatusCode, description = createProjectResult.message),
                createProjectResult
            )
        }

    }
}