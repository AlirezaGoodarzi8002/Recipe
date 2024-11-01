package com.security_wave.recipe.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.security_wave.recipe.data.model.Category
import com.security_wave.recipe.databinding.ListItemCategoryBinding

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
                executePendingBindings()
                root.setOnClickListener {
                    onItemClicked(category.categoryName)
                }
            }
        }
    }
}
