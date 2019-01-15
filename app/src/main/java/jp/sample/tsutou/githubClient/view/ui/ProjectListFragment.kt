package jp.sample.tsutou.githubClient.view.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.sample.tsutou.githubClient.R
import jp.sample.tsutou.githubClient.databinding.FragmentProjectListBinding
import jp.sample.tsutou.githubClient.service.model.Project
import jp.sample.tsutou.githubClient.view.adapter.ProjectAdapter
import jp.sample.tsutou.githubClient.view.callback.ProjectClickCallback
import jp.sample.tsutou.githubClient.viewModel.ProjectListViewModel

/**
 *Project一覧のFragment
 */

const val TAG_OF_PROJECT_LIST_FRAGMENT = "ProjectListFragment"

class ProjectListFragment : Fragment() {

    private var projectAdapter: ProjectAdapter? = null
    private var binding: FragmentProjectListBinding? = null

    //callbackに操作イベントを設定
    private val projectClickCallback = object : ProjectClickCallback {
        override fun onClick(project: Project) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                (activity as MainActivity).show(project)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //dataBinding用のレイアウトリソースをセット
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)

        //イベントのcallbackをadapterに伝達
        projectAdapter = ProjectAdapter(projectClickCallback)

        //上記adapterをreclclerViewに適用
        requireNotNull(binding).projectList.adapter = projectAdapter
        //Loading開始
        requireNotNull(binding).isLoading = true
        //rootViewを取得
        return requireNotNull(binding).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ProjectListViewModel::class.java)
        //監視を開始
        observeViewModel(viewModel)
    }

    //observe開始
    private fun observeViewModel(viewModel: ProjectListViewModel) {

        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
        viewModel.projectListObservable.observe(this, Observer { projects ->
            if (projects != null) {
                requireNotNull(binding).isLoading = false
                projectAdapter!!.setProjectList(projects)
            }
        })
    }
}
