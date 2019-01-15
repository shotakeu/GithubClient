package com.example.nijimac103.easyclient_mvvm.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import com.example.nijimac103.easyclient_mvvm.service.model.Project

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ViewModelに対するデータプロバイダ
 * レスポンスをLiveData Objectにラップする
 * コンストラクタでRetrofitインスタンスを生成
 */
class ProjectRepository private constructor() {

    //Retrofitインターフェース
    private var githubService: GithubService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        githubService = retrofit.create(GithubService::class.java)
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(一覧)
    fun getProjectList(userId: String): LiveData<List<Project>> {
        val data = MutableLiveData<List<Project>>()

        //Retrofitで非同期リクエスト->Callbackで(自分で実装したModel)型ListのMutableLiveDataにセット
        githubService.getProjectList(userId).enqueue(object : Callback<List<Project>> {
            override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>?) {
                if (response != null) {
                    data.postValue(response.body())
                    Log.d("logs:", "getProjectList" + response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                //TODO: null代入良くない + エラー処理
                data.postValue(null)
                Log.d("logs:", "getProjectList:onFailure")
            }
        })

        return data
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(詳細)
    //うまくenqueueでのCallbackをOverrideできない場合、Retrofitインターフェースの型指定など間違えて居る可能性あり
    fun getProjectDetails(userID: String, projectName: String): LiveData<Project> {
        val data = MutableLiveData<Project>()

        githubService.getProjectDetails(userID, projectName).enqueue(object : Callback<Project> {
            override fun onResponse(call: Call<Project>, response: Response<Project>?) {
                if (response != null) {
                    simulateDelay()
                    data.postValue(response.body())
                    Log.d("logs:", "getProjectDetails" + response.body().toString())
                }
            }

            override fun onFailure(call: Call<Project>, t: Throwable) {
                //TODO: null代入良くない + エラー処理
                data.postValue(null)

                Log.d("logs:", "getProjectDetails:onFailure")
            }
        })
        return data
    }


    //引用：マルチスレッドの並列処理で無限ループを実行していた場合、CPUの負荷が大きくなりリソースを消費してメモリリークなどPCの動作が重くなる要因となってしまいます。
    //そのため、マルチスレッドの処理中にsleepメソッドを使用して、処理を一時停止すればCPUの負荷を抑えられることができます。
    private fun simulateDelay() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    companion object Factory {
        //singletonでRepositoryインスタンスを取る
        //synchronized : オブジェクトに鍵をかけて、他のスレッドに邪魔されないようにして作業をする
        val instance: ProjectRepository
            @Synchronized get() {
                return ProjectRepository()
            }
    }

}
