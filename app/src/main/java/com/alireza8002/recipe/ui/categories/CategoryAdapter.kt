package com.alireza8002.recipe.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alireza8002.recipe.R
import com.alireza8002.recipe.data.model.Category
import com.alireza8002.recipe.databinding.ListItemCategoryBinding
import com.alireza8002.recipe.utils.Constants.ITEMS_ANIMATION_DURATION

class CategoryAdapter(
    private val categories: List<Category>, private val onItemClicked: (String) -> Unit
) : Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(private val binding: ListItemCategoryBinding) :
        ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                this.category = category
                root.initView(category)
                executePendingBindings()
            }
        }

        private fun View.initView(category: Category) {
            setOnClickListener { onItemClicked(category.categoryName) }
            val animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down).apply {
                    duration = ITEMS_ANIMATION_DURATION
                }
            setAnimation(animation)
        }
    }
}
