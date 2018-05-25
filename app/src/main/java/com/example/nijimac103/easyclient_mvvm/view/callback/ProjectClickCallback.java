package com.example.nijimac103.easyclient_mvvm.view.callback;


import com.example.nijimac103.easyclient_mvvm.service.model.Project;

/**
 * クリック操作を伝えるinterface
 * @link onClick(Project project) 詳細画面に移動
 */
public interface ProjectClickCallback {
    void onClick(Project project);
}
