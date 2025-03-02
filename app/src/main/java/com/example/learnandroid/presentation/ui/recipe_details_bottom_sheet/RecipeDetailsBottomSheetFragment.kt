package com.example.learnandroid.presentation.ui.recipe_details_bottom_sheet

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentRecipeDetailsBottomSheetBinding
import com.example.learnandroid.presentation.base.BaseBottomSheetDialogFragment
import com.example.learnandroid.presentation.model.Recipe
import com.example.learnandroid.utils.loadImage
import com.example.learnandroid.utils.setHtmlText
import com.example.learnandroid.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailsBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentRecipeDetailsBottomSheetBinding>(
        FragmentRecipeDetailsBottomSheetBinding::inflate
    ) {

    private val viewModel: RecipeDetailsViewModel by viewModels()
    private val args: RecipeDetailsBottomSheetFragmentArgs by navArgs()
    private lateinit var recipe: Recipe
    private lateinit var favoriteIds: List<Int>

    override fun start() {
        recipe = args.recipe
        viewModel.searchFoodById(recipe.id)
        observeSearchByIdResult()
        observeFavoriteIdList()
        disableBottomSheetDragging()
        setUpListeners()
    }

    private fun disableBottomSheetDragging() {
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.isDraggable = false
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun setUpListeners() {
        binding.imgBack.setOnClickListener {
            dismiss()
        }
        binding.imgFavorite.setOnClickListener {
            if (favoriteIds.contains(recipe.id)) {
                viewModel.removeFavoriteId(recipe.id)
            } else {
                viewModel.saveFavoriteId(recipe.id)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        with(binding) {
            setTextOrHideView(tvTitle, recipe.title)
            image.loadImage(recipe.image)
            tvSummary.setHtmlText(recipe.summary)
            tvInstructions.setHtmlText(recipe.instructions)

            if (recipe.cuisines?.isNotEmpty() == true) {
                tvCuisines.text =
                    getString(R.string.cuisines).plus(recipe.cuisines?.joinToString(", ").plus('.'))
            } else {
                tvCuisines.visibility = View.GONE
            }

            if (recipe.readyInMinutes != null) {
                tvReadyInMinutes.text = "${tvReadyInMinutes.text} ${recipe.readyInMinutes} minutes"
            } else {
                tvReadyInMinutes.visibility = View.GONE
            }

            setTextOrHideView(tvServings, recipe.servings)
            setTextOrHideView(tvSourceUrl, recipe.sourceUrl)
            setTextOrHideView(tvVegetarian, recipe.vegetarian)
            setTextOrHideView(tvVegan, recipe.vegan)
            setTextOrHideView(tvGlutenFree, recipe.glutenFree)
            setTextOrHideView(tvDairyFree, recipe.dairyFree)
            setTextOrHideView(tvCheap, recipe.cheap)
            setTextOrHideView(tvVeryPopular, recipe.veryPopular)
            setTextOrHideView(tvHealthScore, recipe.healthScore)
            setTextOrHideView(tvPricePerServing, recipe.pricePerServing)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTextOrHideView(view: TextView, input: Any?) {
        when (input) {
            is Int, is Double -> {
                view.text = "${view.text} $input"
            }

            is String -> {
                if (input.isEmpty()) {
                    view.visibility = View.GONE
                } else {
                    view.text = "${view.text} $input"
                }
            }

            is Boolean -> {
                if (input) {
                    view.text = "${view.text} ✅"
                } else {
                    view.text = "${view.text} ❌"
                }
            }

            else -> {
                view.visibility = View.GONE
            }
        }
    }

    private fun observeSearchByIdResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResult.collect {
                binding.progressBar.visibility = if (it.loader) View.VISIBLE else View.GONE

                if (it.error != null) {
                    requireContext().showToast(it.error)
                }

                if (it.searchByIdResult != null) {
                    recipe = it.searchByIdResult
                    Log.d("!!!", "recipe fragment: $recipe")
                    setUpViews()
                }
            }
        }
    }

    private fun observeFavoriteIdList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteIdList.collect { favoriteIdList ->
                favoriteIds = favoriteIdList
                if (favoriteIdList.contains(recipe.id)) {
                    binding.imgFavorite.setImageResource(R.drawable.heart_red)
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.heart)
                }
            }
        }
    }
}