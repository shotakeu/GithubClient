package jp.sample.tsutou.githubClient.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import jp.sample.tsutou.githubClient.R
import jp.sample.tsutou.githubClient.databinding.FragmentProjectDetailsBinding
import jp.sample.tsutou.githubClient.viewModel.ProjectViewModel

import java.util.Objects

private const val KEY_PROJECT_ID = "project_id"

class ProjectFragment : Fragment() {

    private var binding: FragmentProjectDetailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // DataBinding対象のレイアウトをinflateする
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)

        // Create and set the adapter for the RecyclerView.
        return requireNotNull(this.binding).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //DI
        assert(arguments != null)
        val factory = ProjectViewModel.Factory(
                Objects.requireNonNull<FragmentActivity>(activity).application, requireNotNull(arguments).getString(KEY_PROJECT_ID)!!
        )

        //project_idをキーに注入してViewModelインスタンスを取得
        val viewModel = ViewModelProviders.of(this, factory).get(ProjectViewModel::class.java)

        //ViewにViewModelをセット
        requireNotNull(binding).projectViewModel = viewModel

        //app:visibleGone="@{isLoading}"をtrueに
        requireNotNull(binding).isLoading = true

        //データ監視を開始 -> 差分を監視して、ViewModelに伝える
        observeViewModel(viewModel)

    }

    //Modelのデータを監視するメソッド
    private fun observeViewModel(viewModel: ProjectViewModel) {
        viewModel.observableProject.observe(this, Observer { project ->
            if (project != null) {

                requireNotNull(binding).isLoading = false

                viewModel.setProject(project)
            }
        })
    }

    companion object PUT {

        //fragmentToFragmentでidを渡す
        fun forProject(projectID: String): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()

            args.putString(KEY_PROJECT_ID, projectID)
            fragment.arguments = args

            return fragment
        }
    }

}
