package com.example.nijimac103.easyclient_mvvm.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    //APIにリクエストし、レスポンスをLiveDataで返す
    public LiveData<List<Project>> getProjectList(String userId) {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        //Retrofitで非同期リクエスト->Callbackで(自分で実装したModel)型ListのMutableLiveDataにセット
        githubService.getProjectList(userId).enqueue(new Callback<List<Project>>(){
            @Override
            public void onResponse(Call<List<Project>> call,@Nullable Response<List<Project>> response){
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t){
                //TODO: null代入良くない + エラー処理
                data.setValue(null);
            }
        });

        return data;
    }

    //遅延オプション
    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
