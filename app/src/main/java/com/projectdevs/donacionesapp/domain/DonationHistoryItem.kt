package com.projectdevs.donacionesapp.domain

data class DonationHistoryItem(
    val id: Int,
    val nombre: String,
    val fecha: String,
    val categoria: String, // 💡 Necesario para el filtrado en DonationHistoryScreen
    val estado: String,
    val receptor: String
)

// Y una lista de prueba para el historial
val historialDonaciones = listOf(
    DonationHistoryItem(101, "Alimentos Varios", "12/09/2025", "Alimentos", "Finalizada","Juan Perez"),
    DonationHistoryItem(102, "Televisor antiguo", "05/08/2025", "Electrodomésticos", "Finalizada","Juan Perez"),
    DonationHistoryItem(103, "Pantalones de niño", "01/10/2025", "Indumentaria", "Finalizada","Juan Perez"),
    DonationHistoryItem(104, "Leche y arroz", "15/10/2025", "Alimentos", "Finalizada","Juan Perez"),
)