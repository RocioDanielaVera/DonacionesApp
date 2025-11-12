package com.projectdevs.donacionesapp.domain

import com.projectdevs.donacionesapp.R

data class Donation(
    val id: Int,
    val title: String,
    val description: String,
    val donorName: String,
    val deliveryPreference: String,
    val imagen: Int? = null
)

val donaciones =
    listOf(
        Donation(1, "Ropa de abrigo", "Camperas en buen estado", "Ulises", "Retirar en CABA"),
        Donation(2, "Electrodoméstico", "Microondas funcional", "Ana", "Coordinar envío"),
        Donation(3, "Ejemplo", "Camperas en buen estado", "Ulises", "Retirar en CABA"),
        Donation(4, "Ejemplo", "Microondas funcional", "Ana", "Coordinar envío"),
        Donation(5, "Ejemplo", "Camperas en buen estado", "Ulises", "Retirar en CABA"),
        Donation(6, "Ejemplo", "Microondas funcional", "Ana", "Coordinar envío"),
    )
