package com.example.learnandroid.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentReviewBottomSheetDialogBinding
import com.example.learnandroid.model.OrderItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var order: OrderItem
    private val args: ReviewBottomSheetDialogFragmentArgs by navArgs()
    private var _binding: FragmentReviewBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private var selectedStars = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        order = args.order
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun setUpViews() {
        with(binding){
            imgPhoto.setImageResource(order.image)
            tvName.text = order.name
            tvColor.text = order.color
            tvQuantity.text = "${binding.tvQuantity.text}${order.quantity}"
            btnStatus.text = order.status
            tvPrice.text = String.format("%.2f", order.price)
        }

        // set circle colors according to orderItem color
        when(order.color.lowercase()) {
            "black" -> binding.imgColor.setImageResource(R.drawable.circle_black)
            "brown" -> binding.imgColor.setImageResource(R.drawable.circle_brown)
            "blue grey" -> binding.imgColor.setImageResource(R.drawable.circle_blue_grey)
        }

        // set status texts
        when(order.status) {
            "Completed" -> binding.btnStatus.text = order.status
            "Active" -> binding.btnStatus.text = getString(R.string.in_delivery)
        }
    }

    private fun setupListeners() {
        with(binding) {
            imgStar1.setOnClickListener { selectStars(1).also { selectedStars = 1 } }
            imgStar2.setOnClickListener { selectStars(2).also { selectedStars = 2 } }
            imgStar3.setOnClickListener { selectStars(3).also { selectedStars = 3 } }
            imgStar4.setOnClickListener { selectStars(4).also { selectedStars = 4 } }
            imgStar5.setOnClickListener { selectStars(5).also { selectedStars = 5 } }

            btnCancel.setOnClickListener {
                // close the dialog
                dismiss()
            }

            btnSubmit.setOnClickListener {
                val reviewText = binding.etReview.text.toString()
                // if field is not empty, show toast and dismiss dialog
                if (reviewText.isNotEmpty() && selectedStars != 0 ){
                    Toast.makeText(context, getString(R.string.thank_you_for_your_review), Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                else if(reviewText.isEmpty()) {
                    Toast.makeText(context, getString(R.string.please_write_your_review), Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, getString(R.string.please_select_at_least_one_star), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun selectStars(star: Int) {
        when(star) {
            1 -> {
                with(binding) {
                    imgStar1.setImageResource(R.drawable.selected_star)
                    imgStar2.setImageResource(R.drawable.unselected_star)
                    imgStar3.setImageResource(R.drawable.unselected_star)
                    imgStar4.setImageResource(R.drawable.unselected_star)
                    imgStar5.setImageResource(R.drawable.unselected_star)
                }
            }
            2 -> {
                with(binding) {
                    imgStar1.setImageResource(R.drawable.selected_star)
                    imgStar2.setImageResource(R.drawable.selected_star)
                    imgStar3.setImageResource(R.drawable.unselected_star)
                    imgStar4.setImageResource(R.drawable.unselected_star)
                    imgStar5.setImageResource(R.drawable.unselected_star)
                }
            }
            3 -> {
                with(binding) {
                    imgStar1.setImageResource(R.drawable.selected_star)
                    imgStar2.setImageResource(R.drawable.selected_star)
                    imgStar3.setImageResource(R.drawable.selected_star)
                    imgStar4.setImageResource(R.drawable.unselected_star)
                    imgStar5.setImageResource(R.drawable.unselected_star)
                }
            }
            4 -> {
                with(binding) {
                    imgStar1.setImageResource(R.drawable.selected_star)
                    imgStar2.setImageResource(R.drawable.selected_star)
                    imgStar3.setImageResource(R.drawable.selected_star)
                    imgStar4.setImageResource(R.drawable.selected_star)
                    imgStar5.setImageResource(R.drawable.unselected_star)
                }
            }
            5 -> {
                with(binding) {
                    imgStar1.setImageResource(R.drawable.selected_star)
                    imgStar2.setImageResource(R.drawable.selected_star)
                    imgStar3.setImageResource(R.drawable.selected_star)
                    imgStar4.setImageResource(R.drawable.selected_star)
                    imgStar5.setImageResource(R.drawable.selected_star)
                }
            }
        }
    }

}