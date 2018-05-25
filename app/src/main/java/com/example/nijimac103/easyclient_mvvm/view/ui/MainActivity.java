package com.example.nijimac103.easyclient_mvvm.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nijimac103.easyclient_mvvm.R;
import com.example.nijimac103.easyclient_mvvm.service.model.Project;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            //プロジェクト一覧のFragment
            ProjectListFragment fragment = new ProjectListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                    .commit();
        }
    }

    //詳細画面への遷移
    public void show(Project project) {
        ProjectFragment projectFragment = ProjectFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment, null)
                .commit();
    }
}
