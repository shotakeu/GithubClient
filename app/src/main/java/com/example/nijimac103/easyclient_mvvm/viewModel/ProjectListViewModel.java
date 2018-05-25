package com.example.nijimac103.easyclient_mvvm.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;
import com.example.nijimac103.easyclient_mvvm.service.repository.ProjectRepository;

import java.util.List;

/**
 * List<Project>のrepositoryから送られてくるデータとuiイベントに責務を持つViewModel
 * 引用：実際のケースでは、結果データをObserving Viewに渡す前に変換が必要な場合があります。
 * 変換を行うには、以下のドキュメントに示すTransformationクラスを使用できます。
 * https : //developer.android.com/topic /libraries/architecture/livedata.html#transformations_of_livedata
 */
public class ProjectListViewModel extends AndroidViewModel {

    //監視対象のLiveData
    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(Application application){
        super(application);

        //Repositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
        //変換が必要な場合、これをTransformationsクラスで単純に行うことができます。
        projectListObservable = ProjectRepository.getInstance().getProjectList("Tsutou");
    }

    //UIが観察できるようにコンストラクタで取得したLiveDataを公開する
    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}
