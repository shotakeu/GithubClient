package com.example.nijimac103.easyclient_mvvm.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nijimac103.easyclient_mvvm.R;
import com.example.nijimac103.easyclient_mvvm.databinding.FragmentProjectListBinding;
import com.example.nijimac103.easyclient_mvvm.service.model.Project;
import com.example.nijimac103.easyclient_mvvm.view.adapter.ProjectAdapter;
import com.example.nijimac103.easyclient_mvvm.view.callback.ProjectClickCallback;
import com.example.nijimac103.easyclient_mvvm.viewModel.ProjectListViewModel;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public class ProjectListFragment extends Fragment {
    public static final String TAG = "ProjectListFragment";
    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding binding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //dataBinding用のレイアウトリソースをセット
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        //イベントのcallbackをadapterに伝達
        projectAdapter = new ProjectAdapter(projectClickCallback);
        //上記adapterをreclclerViewに適用
        binding.projectList.setAdapter(projectAdapter);
        //Loading開始
        binding.setIsLoading(true);
        //rootViewを取得
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProjectListViewModel viewModel =
                ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    //observe開始
    private void observeViewModel(ProjectListViewModel viewModel){

        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if(projects != null){
                    binding.setIsLoading(false);
                    projectAdapter.setProjectList(projects);
                }
            }
        });
    }

    //callbackに操作イベントを設定
    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) Objects.requireNonNull(getActivity())).show(project);
            }
        }
    };

}
