package kz.alseco.core.network.models

import kotlinx.serialization.Serializable


@Serializable
data class ErrorModel(
    val timestamp: String? = null,
    val status: Int? = null,
    val message: String? = null,
    val customMessage: String? = null,
)
