package com.projectdevs.donacionesapp.domain

import android.R.attr.category
import com.projectdevs.donacionesapp.R

data class Donation(
    val id: Int,
    val title: String,
    val description: String,
    val donorName: String,
    val donorId: Int,
    val deliveryPreference: String,
    val imagen: Int? = null,
    val location: String,
    val unit: Int,
    val condition: String,
    val donorScore: Double,
    val category: String
)

val donaciones = listOf(
    Donation(
        id = 1,
        title = "Estufa mediana Liliana - Como nueva",
        description = "Estufa Liliana, usada solo una vez. Excelente funcionamiento ideal para ambientes pequeños.",
        donorName = "Ana Mendoza",
        donorId = 1,
        deliveryPreference = "Retiro en San Justo, cerca de la sucursal Central Oeste",
        imagen = R.drawable.estufa,
        location = "San Justo",
        unit = 1,
        condition = "Nuevo",
        donorScore = 4.5,
        category = "Electronica"
    ),
    Donation(
        id = 2,
        title = "Viandas x50 unidades variadas",
        description = "Pack de 50 viandas listas para entregar. Incluyen carnes, pastas y opciones vegetarianas.",
        donorName = "Cocina Solidaria",
        donorId = 2,
        deliveryPreference = "Entrega en punto acordado",
        imagen = R.drawable.viandasx50,
        location = "San Justo",
        unit = 50,
        condition = "Nuevo",
        donorScore = 4.8,
        category = "Gatronomia"
    ),
    Donation(
        id = 3,
        title = "Ropa mujer – XS a XL",
        description = "Conjunto de prendas en excelente estado. Vestidos, remeras y jeans.",
        donorName = "María López",
        donorId = 3,
        deliveryPreference = "Retiro por domicilio",
        imagen = R.drawable.ropa_mujer,
        location = "San Justo",
        unit = 12,
        condition = "Usado",
        donorScore = 4.7,
        category = "Indumentaria"
    ),
    Donation(
        id = 4,
        title = "Caja de viandas x12 unidades",
        description = "Comidas caseras listas para consumir. Incluye opciones sin TACC.",
        donorName = "Comedor Esperanza",
        donorId = 4,
        deliveryPreference = "Entrega inmediata",
        imagen = R.drawable.viandasx12,
        location = "San Justo",
        unit = 12,
        condition = "Nuevo",
        donorScore = 4.9,
        category = "Gatronomia"
    ),
    Donation(
        id = 5,
        title = "Pava eléctrica Peabody",
        description = "Poco uso. En perfectas condiciones.",
        donorName = "Juan Torres",
        donorId = 5,
        deliveryPreference = "Retiro por Ramos Mejía",
        imagen = R.drawable.pava_electrica,
        location = "Ramos Mejía",
        unit = 1,
        condition = "Usado",
        donorScore = 4.2,
        category = "Electronica"
    ),
    Donation(
        id = 6,
        title = "Camperas de invierno – Talles surtidos",
        description = "Abrigos gruesos ideales para bajas temperaturas. Muy buen estado.",
        donorName = "Ropero Comunitario",
        donorId = 6,
        deliveryPreference = "Entrega en plaza principal",
        imagen = R.drawable.camperas_invierno,
        location = "San Justo",
        unit = 8,
        condition = "Usado",
        donorScore = 4.9,
        category = "Indumentaria"
    ),
    Donation(
        id = 7,
        title = "Bolsón de verduras frescas",
        description = "Incluye papas, zanahorias, zapallos, espinaca y más.",
        donorName = "Huerta La Unión",
        donorId = 7,
        deliveryPreference = "Entrega a coordinar",
        imagen = R.drawable.bolson_verduras,
        location = "Ciudad Evita",
        unit = 1,
        condition = "Nuevo",
        donorScore = 4.6,
        category = "Gatronomia"
    ),
    Donation(
        id = 8,
        title = "Mesa de luz de madera",
        description = "En buen estado con un solo detalle menor en la superficie.",
        donorName = "Romina García",
        donorId = 8,
        deliveryPreference = "Retiro a domicilio",
        imagen = R.drawable.mesa_luz,
        location = "San Justo",
        unit = 1,
        condition = "Usado",
        donorScore = 4.1,
        category = "Otros"
    ),
    Donation(
        id = 9,
        title = "Celular Motorola E6 – Funcionando",
        description = "Incluye cargador. Batería en buen estado.",
        donorName = "Carlos Benítez",
        donorId = 9,
        deliveryPreference = "Entrega en estación de tren",
        imagen = R.drawable.motorola_e6,
        location = "Lomas del Mirador",
        unit = 1,
        condition = "Usado",
        donorScore = 4.3,
        category = "Electronica"
    ),
    Donation(
        id = 10,
        title = "Set de utensillos de cocina",
        description = "Incluye pincel, pinza, batidora, contenedor, cucharas, espatulas, cucharon y servidor",
        donorName = "Familia Acosta",
        donorId = 10,
        deliveryPreference = "Retiro en domicilio",
        imagen = R.drawable.utensillos_cocina,
        location = "San Justo",
        unit = 10,
        condition = "Nuevo",
        donorScore = 4.6,
        category = "Gatronomia"
    )
)
