package com.example.learnandroid.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RvItemBinding
import com.example.learnandroid.model.Field

class FieldGroupAdapter : ListAdapter<List<Field>, FieldGroupAdapter.FieldGroupViewHolder>(FieldGroupDiffUtil()) {

    private val fieldValues: MutableMap<String, String> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldGroupViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldGroupViewHolder, position: Int) {
        val field = getItem(position)
        holder.onBind(field)
    }

    inner class FieldGroupViewHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(field: List<Field>) {
            val context = binding.root.context

            field.forEach {
                if (it.isActive && it.fieldType == "input") {
                    createEditText(context, it)
                }
                else if (it.isActive && it.fieldType == "chooser") {
                    createChooser(context, it)
                }
            }
        }

        private fun createEditText(context: Context, field: Field) {
            val editText = AppCompatEditText(context)

            editText.layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                120
            ).also { it.setMargins(30, 10, 30, 10) }

            editText.hint = field.hint

            editText.inputType = when(field.keyboard) {
                "text" -> InputType.TYPE_CLASS_TEXT
                "number" -> InputType.TYPE_CLASS_NUMBER
                else -> InputType.TYPE_NULL
            }

            editText.setBackgroundResource(android.R.color.transparent)

            editText.addTextChangedListener {
                fieldValues.put(field.hint, editText.text.toString())
            }

            binding.root.addView(editText)

            val materialDivider = com.google.android.material.divider.MaterialDivider(context).apply {
                layoutParams = MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    3
                ).also { it.setMargins(30, 0, 30, 0) }

            }

            binding.root.addView(materialDivider)
        }

        private fun createChooser(context: Context, field: Field) {
            val spinner = AppCompatSpinner(context)

            spinner.layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                120,
            ).also { it.setMargins(10, 10, 10, 10) }

            val choicesList = mutableListOf(field.hint)
            when(field.hint) {
                "Gender" -> choicesList.addAll(choicesList.size, listOf("Male", "Female"))
            }

            val adapter = object : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, choicesList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    if (view is TextView) {
                        if (position == 0) {
                            view.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                        }
                    }
                    return view
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    if (view is TextView) {
                        if (position == 0) {
                            view.setEnabled(false)
                            view.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                        }
                    }
                    return view
                }
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                    if (pos != 0) {
                        fieldValues.put(field.hint, choicesList[pos])
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    fieldValues.remove(field.hint)
                }
            }

            spinner.adapter = adapter

            binding.root.addView(spinner)

            val materialDivider = com.google.android.material.divider.MaterialDivider(context).apply {
                layoutParams = MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    3
                ).also { it.setMargins(30, 0, 30, 0) }

            }

            binding.root.addView(materialDivider)
        }
    }

    fun getFieldValues() : Map<String, String> {
        return fieldValues
    }
}

class FieldGroupDiffUtil : DiffUtil.ItemCallback<List<Field>>() {
    override fun areItemsTheSame(oldItem: List<Field>, newItem: List<Field>): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: List<Field>, newItem: List<Field>): Boolean {
        return oldItem == newItem
    }
}
