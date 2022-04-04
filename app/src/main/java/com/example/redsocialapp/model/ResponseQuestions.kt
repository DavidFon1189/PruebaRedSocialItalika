package com.example.redsocialapp.model

import com.google.gson.annotations.SerializedName

class ResponseQuestions {
    @SerializedName("colors")
    var colors: ArrayList<String> = ArrayList()
    @SerializedName("questions")
    var questions: ArrayList<Questions> = ArrayList()
}