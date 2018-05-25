package com.example.nijimac103.easyclient_mvvm.service.repository;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GithubService {

    //Retrofitインターフェース(APIリクエストを管理)
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<List<Project>> getProjectDetails(@Path("user") String user,@Path("reponame") String projectName);
}
