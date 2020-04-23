package com.example.soundboard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter internal constructor(context: Context, private var categoryViewModel: CategoryViewModel) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var categories = emptyList<Category>()

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryItemView: TextView = itemView.findViewById(R.id.tv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = inflater.inflate(R.layout.rv_categories, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = categories[position]
        holder.categoryItemView.text = current.categoryName

        holder.itemView.setOnLongClickListener {
            removeCategory(position)
            true
        }
    }

    private fun removeCategory(position: Int) {
        Log.println(Log.DEBUG, "CATEGORY PRESSED:", categories[position].categoryName)
        this.categoryViewModel.delete(categories[position])
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, categories.size)
        notifyDataSetChanged()
    }

    internal fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size

}