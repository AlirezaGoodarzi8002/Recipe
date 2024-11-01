package com.alireza8002.recipe.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alireza8002.recipe.R
import com.alireza8002.recipe.databinding.FragmentRecipeDetailBinding
import com.alireza8002.recipe.utils.Constants.ITEMS_ANIMATION_DURATION
import com.alireza8002.recipe.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private val viewModel: RecipeDetailViewModel by viewModels()
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding: FragmentRecipeDetailBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRecipe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentRecipeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@RecipeDetailFragment.viewModel
            setLifecycleOwner(viewLifecycleOwner)

            this@RecipeDetailFragment.viewModel.recipeUiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Loading -> {
                        shimmerRecipeDetail.visibility = View.VISIBLE
                        errorContainer.visibility = View.INVISIBLE
                    }

                    is UiState.Success -> {
                        shimmerRecipeDetail.visibility = View.GONE
                        svContainer.apply {
                            val animation = AnimationUtils.loadAnimation(
                                context, R.anim.item_animation_fall_down
                            ).apply { duration = ITEMS_ANIMATION_DURATION }
                            setAnimation(animation)
                            visibility = View.VISIBLE
                        }
                        recipe = state.data
                    }

                    is UiState.Error -> {
                        shimmerRecipeDetail.visibility = View.GONE
                        errorContainer.visibility = View.VISIBLE
                        errorMessageText = state.message
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
