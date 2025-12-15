package com.rhannagan.omadaimageloadingapp.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photos(
    @SerializedName("page") val page: Int = 1,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("perpage") val perpage: Int = 0,
    @SerializedName("total") val total: Int = 0,
    @SerializedName("photo") val photo: List<PhotoDetails> = emptyList()
)
