package com.example

import com.example.simulation.VehicleSimulator
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    VehicleSimulator.start()

    routing {

        get("/") {
            call.respondText("Edinburgh Vehicle Server is running!")
        }

        get("/vehicles") {

            call.respond(
                VehicleSimulator.getVehicles()
            )
        }

    }
}