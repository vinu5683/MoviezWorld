package com.vinod.moviezworld.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.vinod.moviezworld.R

class PageLoaderAdapter : LoadStateAdapter<PageLoaderAdapter.PagingLoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PagingLoadViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        return PagingLoadViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagingLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class PagingLoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }


}