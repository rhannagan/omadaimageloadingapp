package com.rhannagan.omadaimageloadingapp.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetails(
    @SerializedName("id") val id: String = "",
    @SerializedName("owner") val owner: String = "",
    @SerializedName("secret") val secret: String = "",
    @SerializedName("server") val server: String = "",
    @SerializedName("farm") val farm: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("ispublic") val ispublic: Int = 0,
    @SerializedName("isfriend") val isfriend: Int = 0,
    @SerializedName("isfamily") val isfamily: Int = 0,
)
