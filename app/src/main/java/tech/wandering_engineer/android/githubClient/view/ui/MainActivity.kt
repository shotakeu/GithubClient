package tech.wandering_engineer.android.githubClient.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tech.wandering_engineer.android.githubClient.R
import tech.wandering_engineer.android.githubClient.service.model.GithubProject

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
    fun show(githubProject: GithubProject) {
        val projectFragment = ProjectFragment.forProject(githubProject.name)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("githubProject")
                .replace(R.id.fragment_container, projectFragment, null)
                .commit()
    }
}
