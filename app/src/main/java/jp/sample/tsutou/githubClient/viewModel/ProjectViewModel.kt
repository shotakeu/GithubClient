package jp.sample.tsutou.githubClient.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField

import jp.sample.tsutou.githubClient.R
import jp.sample.tsutou.githubClient.service.model.Project
import jp.sample.tsutou.githubClient.service.repository.ProjectRepository

class ProjectViewModel(application: Application, mProjectID: String) : AndroidViewModel(application) {

    val observableProject: LiveData<Project> =
            ProjectRepository
                    .instance
                    .getProjectDetails(application.getString(R.string.github_user_name), mProjectID)

    var project = ObservableField<Project>()

    //セッター
    fun setProject(project: Project) {
        this.project.set(project)
    }

    /**
     * IDの(DI)依存性注入クラス
     * Architecture ComponentsとDagger2の合わせ技(参考記事:https://qiita.com/satorufujiwara/items/f42b176404287690f1d0)
     */
    class Factory(private val application: Application, private val projectID: String) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ProjectViewModel(application, projectID) as T
        }
    }
}
