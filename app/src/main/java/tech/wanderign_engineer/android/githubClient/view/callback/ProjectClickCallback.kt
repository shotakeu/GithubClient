package tech.wandering_engineer.android.githubClient.view.callback

import tech.wandering_engineer.android.githubClient.service.model.Project

/**
 * クリック操作を伝えるinterface
 * @link onClick(Project project) 詳細画面に移動
 */
interface ProjectClickCallback {
    fun onClick(project: Project)
}
