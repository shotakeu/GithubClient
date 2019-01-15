package jp.sample.tsutou.githubClient.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.sample.tsutou.githubClient.R
import jp.sample.tsutou.githubClient.service.model.Project

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
