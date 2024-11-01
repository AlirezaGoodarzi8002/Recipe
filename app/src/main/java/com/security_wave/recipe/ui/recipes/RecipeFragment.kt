package com.security_wave.recipe.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.security_wave.recipe.R
import com.security_wave.recipe.data.model.Recipe
import com.security_wave.recipe.databinding.FragmentRecipeBinding
import com.security_wave.recipe.ui.detail.RECIPE_ID_KEY
import com.security_wave.recipe.utils.UiState
import com.security_wave.recipe.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment @Inject constructor() : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRecipes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            recipes.observe(viewLifecycleOwner) { recipesUiState ->
                when (recipesUiState) {
                    is UiState.Success -> {
                        initRecyclerView(recipesUiState)
                        hideShimmer()
                    }

                    is UiState.Error -> {
                        binding.root.showErrorSnackBar(recipesUiState.message) { fetchRecipes() }
                        hideShimmer()
                    }

                    is UiState.Loading -> showShimmer()
                }
            }
        }
    }

    private fun initRecyclerView(recipesUiState: UiState.Success<List<Recipe>>) {
        val adapter = RecipeAdapter(recipesUiState.data) { id ->
            val bundle = Bundle().apply { putString(RECIPE_ID_KEY, id) }
            findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment, bundle)
        }
        binding.apply {
            rcRecipes.apply {
                this.adapter = adapter
            }
        }
    }

    private fun showShimmer() {
        binding.shimmerFrameLayout.apply {
            startShimmer()
            visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        hideShimmer()
        super.onPause()
    }

    private fun hideShimmer() {
        binding.shimmerFrameLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}