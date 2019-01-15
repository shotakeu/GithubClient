package com.example.nijimac103.easyclient_mvvm.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nijimac103.easyclient_mvvm.R;
import com.example.nijimac103.easyclient_mvvm.databinding.FragmentProjectDetailsBinding;
import com.example.nijimac103.easyclient_mvvm.service.model.Project;
import com.example.nijimac103.easyclient_mvvm.viewModel.ProjectViewModel;

import java.util.Objects;


public class ProjectFragment extends Fragment {

    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // DataBinding対象のレイアウトをinflateする
        binding = DataBindingUtil.<FragmentProjectDetailsBinding>inflate(inflater, R.layout.fragment_project_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //DI
        assert getArguments() != null;
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(
                Objects.requireNonNull(getActivity()).getApplication(), getArguments().getString(KEY_PROJECT_ID)
        );

        //project_idをキーに注入してViewModelインスタンスを取得
        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory).get(ProjectViewModel.class);

        //ViewにViewModelをセット
        binding.setProjectViewModel(viewModel);
        //app:visibleGone="@{isLoading}"をtrueに
        binding.setIsLoading(true);

        //データ監視を開始 -> 差分を監視して、ViewModelに伝える
        observeViewModel(viewModel);

    }

    //Modelのデータを監視するメソッド
    public void observeViewModel(final ProjectViewModel viewModel){
       viewModel.getObservableProject().observe(this, new Observer<Project>() {
           @Override
           public void onChanged(@Nullable Project project) {
               if (project != null){

                   binding.setIsLoading(false);

                   viewModel.setProject(project);
               }
           }
       });
    }


    //fragmentTofragmentでidを渡す
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

}
