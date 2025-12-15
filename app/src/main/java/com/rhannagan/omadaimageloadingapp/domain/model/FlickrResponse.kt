package com.rhannagan.omadaimageloadingapp.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
    @SerializedName("photos") val photos: Photos = Photos(),
    @SerializedName("stat") val stat: String = ""
)