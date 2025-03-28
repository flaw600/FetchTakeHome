package com.example.fetchtakehome.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class HiringResponseItem(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("listId")
    val listId: Int = 0,
    @SerialName("name")
    val name: String? = null
)