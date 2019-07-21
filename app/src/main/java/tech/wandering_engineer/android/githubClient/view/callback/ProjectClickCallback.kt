package tech.wandering_engineer.android.githubClient.view.callback

import tech.wandering_engineer.android.githubClient.service.model.GithubProject

/**
 * クリック操作を伝えるinterface
 * @link onClick(GithubProject project) 詳細画面に移動
 */
interface ProjectClickCallback {
    fun onClick(githubProject: GithubProject)
}
