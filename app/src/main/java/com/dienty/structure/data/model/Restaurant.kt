package com.dienty.structure.data.model

import com.dienty.structure.base.BaseObject

data class Restaurant(
    override val id: String = "",
    override val name: String = "",
    override val logo: String = "",
    val type: String = "",
    val address: String = "",
    val phone: String = ""
) : BaseObject(id, name, logo)