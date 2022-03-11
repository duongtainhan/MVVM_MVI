package com.dienty.structure.data.response

import com.google.gson.annotations.SerializedName

open class Error(
    @SerializedName("result")
    val result: String?,
    @SerializedName("code", alternate = ["status_code", "errorcode"])
    val code: String?,
    @SerializedName("message", alternate = ["status_message", "error"])
    val message: String?
)