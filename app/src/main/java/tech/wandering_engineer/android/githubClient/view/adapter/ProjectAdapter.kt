package tech.wandering_engineer.android.githubClient.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import tech.wandering_engineer.android.githubClient.R
import tech.wandering_engineer.android.githubClient.databinding.ProjectListItemBinding
import tech.wandering_engineer.android.githubClient.service.model.GithubProject
import tech.wandering_engineer.android.githubClient.view.callback.ProjectClickCallback


class ProjectAdapter(private val projectClickCallback: ProjectClickCallback?) :
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var githubProjectList: List<GithubProject>? = null

    //現状との差分をListとしてRecyclerViewにセットする
    fun setProjectList(githubProjectList: List<GithubProject>) {

        if (this.githubProjectList == null) {
            this.githubProjectList = githubProjectList

            //positionStartの位置からitemCountの範囲において、データの変更があったことを登録されているすべてのobserverに通知する。
            notifyItemRangeInserted(0, githubProjectList.size)

        } else {

            //2つのListの差分を計算するユーティリティー。Support Library 24.2.0で追加された。
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return requireNotNull(this@ProjectAdapter.githubProjectList).size
                }

                override fun getNewListSize(): Int {
                    return githubProjectList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return requireNotNull(this@ProjectAdapter.githubProjectList)[oldItemPosition].id == githubProjectList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = githubProjectList[newItemPosition]
                    val old = githubProjectList[oldItemPosition]

                    return project.id == old.id && project.git_url == old.git_url
                }
            })
            this.githubProjectList = githubProjectList

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
        holder.binding.project = requireNotNull(githubProjectList)[position]
        holder.binding.executePendingBindings()
    }

    //リストのサイズを返す
    override fun getItemCount(): Int {
        return if (githubProjectList == null) 0 else requireNotNull(githubProjectList).size
    }

    //インナークラスにViewHolderを継承し、project_list_item.xml に対する Bindingを設定
    open class ProjectViewHolder(val binding: ProjectListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
