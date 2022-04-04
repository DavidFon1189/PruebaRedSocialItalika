package com.example.redsocialapp.presenter

import com.example.redsocialapp.model.ResponseQuestions
import retrofit2.Response

class QuestionsContract {

    interface View{
        fun getGetQuestions()
        fun succesGetQuestions(response: Response<ResponseQuestions>)
        fun failGetQuestions(message: String?)
        fun failGetQuestionsServer(message: String?)
        fun loadingData()
        fun hideDataLoad()
    }

    interface Presenter {
        fun getGetQuestions()
    }
}