package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Gregorian(
    @SerializedName("date")
    var date: String? = "",
    @SerializedName("day")
    var day: String? = "",
    @SerializedName("designation")
    var designation: Designation? = Designation(),
    @SerializedName("format")
    var format: String? = "",
    @SerializedName("month")
    var month: Month? = Month(),
    @SerializedName("weekday")
    var weekday: Weekday? = Weekday(),
    @SerializedName("year")
    var year: String? = ""
)