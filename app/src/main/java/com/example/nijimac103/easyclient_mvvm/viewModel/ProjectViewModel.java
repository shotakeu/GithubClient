package com.example.nijimac103.easyclient_mvvm.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.nijimac103.easyclient_mvvm.service.model.Project;
import com.example.nijimac103.easyclient_mvvm.service.repository.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {
    private final LiveData<Project> projectObservable;
    private final String projectID;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull Application application, final String projectID) {
        super(application);
        this.projectID = projectID;
        projectObservable = ProjectRepository.getInstance().getProjectDetails("Tsutou",projectID);
    }

    //ゲッター
    public LiveData<Project> getObservableProject(){
        return projectObservable;
    }

    //セッター
    public void setProject(Project project){
        this.project.set(project);
    }

    /**
     *IDの(DI)依存性注入クラス
     *Architecture ComponentsとDagger2の合わせ技(参考記事:https://qiita.com/satorufujiwara/items/f42b176404287690f1d0)
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final String projectID;

        public Factory(@NonNull Application application, String projectID) {
            this.application = application;
            this.projectID = projectID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProjectViewModel(application, projectID);
        }
    }
}
