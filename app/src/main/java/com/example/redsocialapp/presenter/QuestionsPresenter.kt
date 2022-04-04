package com.example.redsocialapp.presenter

import com.example.redsocialapp.api.BasePresenter
import com.example.redsocialapp.api.RetrofitClient
import com.example.redsocialapp.model.ResponseQuestions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsPresenter constructor(view: QuestionsContract.View, retrofitClient: RetrofitClient) :
    BasePresenter(), QuestionsContract.Presenter {
    var view: QuestionsContract.View? = null
    var retrofitClient: RetrofitClient? = null

    init {
        this.view = view
        this.retrofitClient = retrofitClient
    }

    override fun getGetQuestions() {
        view?.loadingData()
        retrofitClient?.provideAPIService()?.getQuestions()?.enqueue(object:
            Callback<ResponseQuestions> {
            override fun onFailure(call: Call<ResponseQuestions>, t: Throwable) {
                view?.failGetQuestionsServer("Error de servicio")
                view?.hideDataLoad()
            }
            override fun onResponse(call: Call<ResponseQuestions>, response: Response<ResponseQuestions>) {
                if(response.isSuccessful){
                    view?.succesGetQuestions(response)
                    view?.hideDataLoad()
                }else{
                    view?.failGetQuestions("Datos no encontrados")
                    view?.hideDataLoad()
                }
            }
        })
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<ResponseQuestions>) {

}
