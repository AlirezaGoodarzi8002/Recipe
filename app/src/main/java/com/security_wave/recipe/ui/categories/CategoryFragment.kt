package com.security_wave.recipe.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.security_wave.recipe.R
import com.security_wave.recipe.databinding.FragmentCategoryBinding
import com.security_wave.recipe.ui.recipes.CATEGORY_NAME_KEY
import com.security_wave.recipe.utils.SlideDownFadeAnimator
import com.security_wave.recipe.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment @Inject constructor() : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            categories.observe(viewLifecycleOwner) { categoryUiState ->
                when (categoryUiState) {
                    is CategoryViewModel.CategoryUiState.Success -> {
                        initAdapter(categoryUiState)
                        hideShimmer()
                    }

                    is CategoryViewModel.CategoryUiState.Error -> {
                        binding.root.showErrorSnackBar(categoryUiState.message) { fetchCategories() }
                        hideShimmer()
                    }

                    is CategoryViewModel.CategoryUiState.Loading -> {
                        showShimmer()
                    }
                }
            }
            fetchCategories()
        }
    }

    private fun initAdapter(categoryUiState: CategoryViewModel.CategoryUiState.Success) {
        val adapter = CategoryAdapter(categoryUiState.categories) { categoryName ->
            val bundle = Bundle().apply {
                putString(CATEGORY_NAME_KEY, categoryName)
            }
            findNavController().navigate(R.id.action_categoryFragment_to_recipeFragment, bundle)
        }
        binding.apply {
            rcCategories.apply {
                this.adapter = adapter
                itemAnimator = SlideDownFadeAnimator()
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