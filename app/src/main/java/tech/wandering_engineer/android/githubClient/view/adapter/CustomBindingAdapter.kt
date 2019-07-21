package tech.wandering_engineer.android.githubClient.view.adapter

import android.databinding.BindingAdapter
import android.view.View

object CustomBindingAdapter {
    //xmlに定義する際のBindingAdapter

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}
