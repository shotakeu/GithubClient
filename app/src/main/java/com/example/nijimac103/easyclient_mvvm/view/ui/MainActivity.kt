package com.example.nijimac103.easyclient_mvvm.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.nijimac103.easyclient_mvvm.R
import com.example.nijimac103.easyclient_mvvm.service.model.Project

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            //プロジェクト一覧のFragment
            val fragment = ProjectListFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, TAG_OF_PROJECT_LIST_FRAGMENT)
                    .commit()
        }
    }

    //詳細画面への遷移
    fun show(project: Project) {
        val projectFragment = ProjectFragment.forProject(project.name)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment, null)
                .commit()
    }
}
