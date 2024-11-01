package com.security_wave.recipe.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.security_wave.recipe.databinding.FragmentRecipeBinding
import com.security_wave.recipe.utils.SlideDownFadeAnimator
import com.security_wave.recipe.utils.UiState
import com.security_wave.recipe.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment @Inject constructor() : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

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
                        val adapter = RecipeAdapter(recipesUiState.data) { id ->
//                            TODO("On recipe click listener is not yet implemented.")
                        }
                        binding.apply {
                            recyclerView.apply {
                                this.adapter = adapter
                                itemAnimator = SlideDownFadeAnimator()
                            }
                        }
                        hideShimmer()
                    }

                    is UiState.Error -> {
                        binding.root.showErrorSnackBar(recipesUiState.message) { fetchRecipes() }
                        hideShimmer()
                    }

                    is UiState.Loading -> {
                        showShimmer()
                    }
                }
            }
            fetchRecipes()
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