package com.vinod.moviezworld.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinod.moviezworld.R
import com.vinod.moviezworld.mvvm.models.SearchItem

class MoviesPagingAdapter :
    PagingDataAdapter<SearchItem, MoviesPagingAdapter.MoviesViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.titleTextView.text = it.title
            holder.typeTextView.text = it.type
            Glide.with(holder.itemView.context)
                .load(item.poster)
                .placeholder(holder.itemView.context.getDrawable(R.drawable.ic_baseline_more_horiz_24))
                .error(holder.itemView.context.getDrawable(R.drawable.ic_baseline_more_horiz_24))
                .into(holder.posterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val typeTextView: TextView = itemView.findViewById(R.id.tvType)
        val posterImageView: ImageView = itemView.findViewById(R.id.imgMoviePoster)

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return false
            }

        }
    }


}