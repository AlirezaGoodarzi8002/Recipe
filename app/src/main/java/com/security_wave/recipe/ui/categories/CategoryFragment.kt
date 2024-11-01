package com.security_wave.recipe.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.security_wave.recipe.R
import com.security_wave.recipe.data.model.Category
import com.security_wave.recipe.databinding.FragmentCategoryBinding
import com.security_wave.recipe.ui.recipes.CATEGORY_NAME_KEY
import com.security_wave.recipe.utils.SlideDownFadeAnimator
import com.security_wave.recipe.utils.UiState
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
        binding.apply {
            image = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                R.drawable.ic_sun
            else R.drawable.ic_moon
            ivNightLight.setOnClickListener {
                changeTheme()
            }
        }

        viewModel.apply {
            categories.observe(viewLifecycleOwner) { categoryUiState ->
                when (categoryUiState) {
                    is UiState.Success -> {
                        initAdapter(categoryUiState)
                        hideShimmer()
                    }

                    is UiState.Error -> {
                        binding.root.showErrorSnackBar(categoryUiState.message) { fetchCategories() }
                        hideShimmer()
                    }

                    is UiState.Loading -> showShimmer()
                }
            }
            fetchCategories()
        }
    }

    private fun changeTheme() {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun initRecyclerView(categoryUiState: UiState.Success<List<Category>>) {
        val adapter = CategoryAdapter(categoryUiState.data) { categoryName ->
            val bundle = Bundle().apply { putString(CATEGORY_NAME_KEY, categoryName) }
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