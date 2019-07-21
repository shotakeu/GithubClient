package tech.wandering_engineer.android.githubClient.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import tech.wandering_engineer.android.githubClient.R
import tech.wandering_engineer.android.githubClient.databinding.ProjectListItemBinding
import tech.wandering_engineer.android.githubClient.service.model.Project
import tech.wandering_engineer.android.githubClient.view.callback.ProjectClickCallback


class ProjectAdapter(private val projectClickCallback: ProjectClickCallback?) :
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var projectList: List<Project>? = null

    //現状との差分をListとしてRecyclerViewにセットする
    fun setProjectList(projectList: List<Project>) {

        if (this.projectList == null) {
            this.projectList = projectList

            //positionStartの位置からitemCountの範囲において、データの変更があったことを登録されているすべてのobserverに通知する。
            notifyItemRangeInserted(0, projectList.size)

        } else {

            //2つのListの差分を計算するユーティリティー。Support Library 24.2.0で追加された。
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return requireNotNull(this@ProjectAdapter.projectList).size
                }

                override fun getNewListSize(): Int {
                    return projectList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return requireNotNull(this@ProjectAdapter.projectList)[oldItemPosition].id == projectList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = projectList[newItemPosition]
                    val old = projectList[oldItemPosition]

                    return project.id == old.id && project.git_url == old.git_url
                }
            })
            this.projectList = projectList

            //DiffUtilのメソッド=>差分を元にRecyclerViewAderpterのnotify系が呼ばれ、いい感じにアニメーションなどをやってくれます。
            result.dispatchUpdatesTo(this)
        }
    }

    //継承したインナークラスのViewholderをレイアウトとともに生成
    //bindするビューにコールバックを設定 -> ビューホルダーを返す
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ProjectViewHolder {
        val binding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.project_list_item, parent,
                        false) as ProjectListItemBinding

        binding.callback = projectClickCallback

        return ProjectViewHolder(binding)
    }

    //ViewHolderをDataBindする
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.binding.project = requireNotNull(projectList)[position]
        holder.binding.executePendingBindings()
    }

    //リストのサイズを返す
    override fun getItemCount(): Int {
        return if (projectList == null) 0 else requireNotNull(projectList).size
    }

    //インナークラスにViewHolderを継承し、project_list_item.xml に対する Bindingを設定
    open class ProjectViewHolder(val binding: ProjectListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
