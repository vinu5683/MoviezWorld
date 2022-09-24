package com.vinod.moviezworld.viewcomponents.homecategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vinod.moviezworld.R

class CategoryAdapter(
    private val list: List<HomeCategoryModel>,
    private val categoryItemClickListener: CategoryItemClickListener
) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.setData(list[position], position)
        holder.itemView.setOnClickListener {
            categoryItemClickListener.onCategoryItemClickLister(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
    private val cvCategory: CardView = itemView.findViewById(R.id.cvCategory)

    fun setData(item: HomeCategoryModel, pos: Int) {
        tvCategory.text = item.title
        when {
            item.isSelected -> {
                cvCategory.elevation = tvCategory.context.resources.getDimension(R.dimen.sp_12)
                cvCategory.setCardBackgroundColor(tvCategory.context.resources.getColor(R.color.category_highlight_dark_gray))
                tvCategory.setTextColor(tvCategory.context.resources.getColor(R.color.white))
            }
            else -> {
                cvCategory.elevation = 0f
                cvCategory.setCardBackgroundColor(tvCategory.context.resources.getColor(R.color.search_dark_gray_bg))
                tvCategory.setTextColor(tvCategory.context.resources.getColor(R.color.search_dark_gray_text))
            }
        }
    }

}

interface CategoryItemClickListener {

    fun onCategoryItemClickLister(pos: Int)

}

data class HomeCategoryModel(val title: String, val isSelected: Boolean)