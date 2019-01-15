package com.example.nijimac103.easyclient_mvvm.view.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.nijimac103.easyclient_mvvm.R
import com.example.nijimac103.easyclient_mvvm.databinding.FragmentProjectListBinding
import com.example.nijimac103.easyclient_mvvm.service.model.Project
import com.example.nijimac103.easyclient_mvvm.view.adapter.ProjectAdapter
import com.example.nijimac103.easyclient_mvvm.view.callback.ProjectClickCallback
import com.example.nijimac103.easyclient_mvvm.viewModel.ProjectListViewModel

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
