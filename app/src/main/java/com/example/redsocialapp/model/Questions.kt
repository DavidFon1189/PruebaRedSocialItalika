package com.example.redsocialapp.model

import com.google.gson.annotations.SerializedName

class Questions {
    @SerializedName("total")
    var total: Int = 0
    @SerializedName("text")
    var text: String = ""
    @SerializedName("chartData")
    var chartData: ArrayList<Chardata> = ArrayList()
}