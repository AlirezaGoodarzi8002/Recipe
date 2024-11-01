package com.security_wave.recipe.ui.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.security_wave.recipe.data.model.Recipe
import com.security_wave.recipe.databinding.ListItemRecipeBinding
import com.security_wave.recipe.utils.RecipeDiffCallback

class RecipeAdapter(
    private val recipes: List<Recipe>, private val onItemClicked: (String) -> Unit
) : Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    inner class RecipeViewHolder(private val binding: ListItemRecipeBinding) :
        ViewHolder(binding.root) {
        fun bind(recipeMeal: Recipe) {
            binding.apply {
                this.recipe = recipeMeal
                onItemClicked(recipeMeal.id)
                executePendingBindings()
            }
        }
    }
}