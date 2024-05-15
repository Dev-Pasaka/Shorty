package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.common.JWTConfig
import com.example.data.responses.UnauthorizedResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = JWTConfig.jwtAudience
    val jwtDomain = JWTConfig.jwtDomain
    val jwtRealm = JWTConfig.jwtRealm
    val jwtSecret = JWTConfig.jwtSecret
    authentication {
        jwt {
            realm = jwtRealm
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode(value = HttpStatusCode.Unauthorized.value, description = "Unauthorized"),
                    message = UnauthorizedResponse()
                )
            }
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
