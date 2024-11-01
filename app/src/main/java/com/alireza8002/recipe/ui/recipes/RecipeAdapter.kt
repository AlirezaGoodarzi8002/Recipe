package com.alireza8002.recipe.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alireza8002.recipe.R
import com.alireza8002.recipe.data.model.Recipe
import com.alireza8002.recipe.databinding.ListItemRecipeBinding
import com.alireza8002.recipe.utils.Constants.ITEMS_ANIMATION_DURATION

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
        fun bind(recipe: Recipe) {
            binding.apply {
                tvRecipeName.isSelected = true
                this.recipe = recipe
                root.initView(recipe)
                executePendingBindings()
            }
        }
    }

    private fun View.initView(recipe: Recipe) {
        setOnClickListener { onItemClicked(recipe.id) }
        val animation =
            AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down).apply {
                duration = ITEMS_ANIMATION_DURATION
            }
        setAnimation(animation)
    }
}