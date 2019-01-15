package com.example.nijimac103.easyclient_mvvm.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ViewModelに対するデータプロバイダ
 * レスポンスをLiveData Objectにラップする
 */
public class ProjectRepository {

    //Retrofitインターフェース
    private GithubService githubService;

    //staticに提供できるRepository
    private static ProjectRepository projectRepository;

    //コンストラクタでRetrofitインスタンスを生成
    private ProjectRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        githubService = retrofit.create(GithubService.class);
    }

    //singletonでRepositoryインスタンスを取る
    //synchronized : オブジェクトに鍵をかけて、他のスレッドに邪魔されないようにして作業をする
    public synchronized static ProjectRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(一覧)
    public LiveData<List<Project>> getProjectList(String userId) {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        //Retrofitで非同期リクエスト->Callbackで(自分で実装したModel)型ListのMutableLiveDataにセット
        githubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull Call<List<Project>> call, @Nullable Response<List<Project>> response) {
                data.postValue(response.body());
                Log.d("logs:", "getProjectList" + String.valueOf(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Project>> call, Throwable t) {
                //TODO: null代入良くない + エラー処理
                data.postValue(null);
                Log.d("logs:", "getProjectList:onFailure");
            }
        });

        return data;
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(詳細)
    //うまくenqueueでのCallbackをOverrideできない場合、Retrofitインターフェースの型指定など間違えて居る可能性あり
    public LiveData<Project> getProjectDetails(String userID, String projectName) {
        final MutableLiveData<Project> data = new MutableLiveData<>();

        githubService.getProjectDetails(userID, projectName).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(@NonNull Call<Project> call, @NonNull Response<Project> response) {
                simulateDelay();
                data.postValue(response.body());
                Log.d("logs:", "getProjectDetails" + String.valueOf(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<Project> call, @NonNull Throwable t) {
                //TODO: null代入良くない + エラー処理
                data.postValue(null);

                Log.d("logs:", "getProjectDetails:onFailure");
            }
        });
        return data;
    }


    //引用：マルチスレッドの並列処理で無限ループを実行していた場合、CPUの負荷が大きくなりリソースを消費してメモリリークなどPCの動作が重くなる要因となってしまいます。
    //そのため、マルチスレッドの処理中にsleepメソッドを使用して、処理を一時停止すればCPUの負荷を抑えられることができます。
    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
