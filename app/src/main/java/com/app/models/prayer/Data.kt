package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("date")
    var date: Date? = Date(),
    @SerializedName("meta")
    var meta: Meta? = Meta(),
    @SerializedName("timings")
    var timings: Timings? = Timings()
)