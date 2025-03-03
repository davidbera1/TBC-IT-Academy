package com.example.learnandroid.presentation.ui.recipe_details_bottom_sheet

import android.annotation.SuppressLint
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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
    private var recipeId: Int = 0
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var recipe: Recipe
    private lateinit var favoriteIds: List<Int>

    override fun start() {
        recipeId = args.recipeId
        viewModel.searchFoodById(recipeId)
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
            if(::recipe.isInitialized) {
                if (favoriteIds.contains(recipe.id)) {
                    viewModel.removeFavoriteId(recipe.id)
                    requireContext().showToast(getString(R.string.removed_from_favorites))
                } else {
                    viewModel.saveFavoriteId(recipe.id)
                    requireContext().showToast(getString(R.string.added_in_favorites))
                }
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
                tvCuisines.text = "${tvCuisines.text} ${recipe.cuisines?.joinToString(", ")}"
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
            Linkify.addLinks(binding.tvSourceUrl, Linkify.WEB_URLS)

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

    private fun setUpRecyclerView() {
        ingredientsAdapter = IngredientsAdapter()
        binding.recyclerView.adapter = ingredientsAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
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
                    setUpViews()
                    setUpRecyclerView()
                    ingredientsAdapter.submitList(recipe.extendedIngredients)
                }
            }
        }
    }

    private fun observeFavoriteIdList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteIdList.collect { favoriteIdList ->
                favoriteIds = favoriteIdList
                if (favoriteIdList.contains(recipeId)) {
                    binding.imgFavorite.setImageResource(R.drawable.heart_red)
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.heart)
                }
            }
        }
    }
}