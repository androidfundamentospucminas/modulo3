package com.walker.modulo3projetoicontatos

import java.io.Serializable

data class ContactModel(
    val id: Int,
    val name: String,
    val relationship: String,
    val phoneNumber: String,
    val instagram: String? = null,
    val facebook: String? = null,
    val email: String? = null,
    var contactImage: String? = null
): Serializable