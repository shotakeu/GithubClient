package tech.wandering_engineer.android.githubClient.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import tech.wandering_engineer.android.githubClient.R
import tech.wandering_engineer.android.githubClient.service.model.Project
import tech.wandering_engineer.android.githubClient.service.repository.ProjectRepository

/**
 * List<Project>のrepositoryから送られてくるデータとuiイベントに責務を持つViewModel
 * 引用：実際のケースでは、結果データをObserving Viewに渡す前に変換が必要な場合があります。
 * 変換を行うには、以下のドキュメントに示すTransformationクラスを使用できます。
 * https : //developer.android.com/topic /libraries/architecture/livedata.html#transformations_of_livedata
</Project> */
class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    //監視対象のLiveData
    //UIが観察できるようにコンストラクタで取得したLiveDataを公開する Repositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
    var projectListObservable: LiveData<List<Project>> =
            ProjectRepository
                    .instance
                    .getProjectList(getApplication<Application>().getString(R.string.github_user_name))

}
