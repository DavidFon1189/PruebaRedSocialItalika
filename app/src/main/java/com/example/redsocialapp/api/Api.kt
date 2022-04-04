package com.example.redsocialapp.api

import com.example.redsocialapp.api.EndpointServices.GET_QUESTIONS
import com.example.redsocialapp.model.ResponseQuestions
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET(GET_QUESTIONS)
    fun getQuestions(): Call<ResponseQuestions>
}