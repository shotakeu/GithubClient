package com.example.nijimac103.easyclient_mvvm.service.repository;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GithubService {

    //Retrofitインターフェース(APIリクエストを管理)
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    //一覧
    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    //詳細
    @GET("/repos/{user}/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user,@Path("reponame") String projectName);
}
