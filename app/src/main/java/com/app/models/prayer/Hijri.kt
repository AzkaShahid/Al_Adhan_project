package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Hijri(
    @SerializedName("date")
    var date: String? = "",
    @SerializedName("day")
    var day: String? = "",
    @SerializedName("designation")
    var designation: Designation? = Designation(),
    @SerializedName("format")
    var format: String? = "",
    @SerializedName("holidays")
    var holidays: List<Any>? = listOf(),
    @SerializedName("month")
    var month: MonthX? = MonthX(),
    @SerializedName("weekday")
    var weekday: WeekdayX? = WeekdayX(),
    @SerializedName("year")
    var year: String? = ""
)