package com.example.simulation

import com.example.model.Vehicle
import kotlinx.coroutines.*

data class Stop(
    val latitude: Double,
    val longitude: Double
)

class SimulatedVehicle(
    val vehicle: Vehicle,
    val route: List<Stop>,
    var targetIndex: Int = 0
)

object VehicleSimulator {

    private val stopsRoute = listOf(
        // 1. Morningside Gardens
        Stop(
            55.922597,
            -3.222114
        ),

        // 2. Royal Observatory
        Stop(
            55.9229,
            -3.18807
        ),

        // 3. Craigmillar Castle Gardens
        Stop(
            55.931823,
            -3.145821
        ),

        // 4. Northfield Road
        Stop(
            55.9531,
            -3.13824
        ),

        // 5. Scottish Parliament
        Stop(
            55.95247,
            -3.17566
        ),

        // 6. Newhaven Road
        Stop(
            55.970585,
            -3.187032
        ),

        // 7. Fishmarket Square
        Stop(
            55.98089,
            -3.19429
        ),

        // 8. Ocean Drive
        Stop(
            55.980164,
            -3.177332
        ),

        // 9. Granton Park Avenue
        Stop(
            55.98064,
            -3.22936
        ),

        // 10. Drylaw Police Station
        Stop(
            55.96734,
            -3.24655
        )
    )

    private val simulatedVehicles = (1..10).map { index ->

        SimulatedVehicle(
            vehicle = Vehicle(
                id = "bus_$index",

                latitude = stopsRoute[
                    index % stopsRoute.size
                ].latitude,

                longitude = stopsRoute[
                    index % stopsRoute.size
                ].longitude
            ),

            route = stopsRoute,

            targetIndex = (index + 1) % stopsRoute.size
        )
    }

    fun getVehicles(): List<Vehicle> {
        return simulatedVehicles.map { it.vehicle }
    }

    fun start() {
        CoroutineScope(Dispatchers.Default).launch {

            while (true) {

                delay(2000)

                simulatedVehicles.forEach { sim ->
                    moveTowardsTarget(sim)
                }
            }
        }
    }

    private fun moveTowardsTarget(sim: SimulatedVehicle) {

        val target = sim.route[sim.targetIndex]

        val step = 0.15

        val newLat = sim.vehicle.latitude +
                (target.latitude - sim.vehicle.latitude) * step

        val newLng = sim.vehicle.longitude +
                (target.longitude - sim.vehicle.longitude) * step

        sim.vehicle.latitude = newLat
        sim.vehicle.longitude = newLng

        val distance =
            Math.abs(target.latitude - newLat) +
                    Math.abs(target.longitude - newLng)

        if (distance < 0.0005) {

            sim.targetIndex =
                (sim.targetIndex + 1) % sim.route.size
        }
    }
}