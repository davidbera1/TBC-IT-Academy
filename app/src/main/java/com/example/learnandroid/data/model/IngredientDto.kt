package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val name: String,
    val amount: Double,
    val measures: MeasuresDto
) {
    @Serializable
    data class MeasuresDto(
        val us: MeasureDto,
        val metric: MeasureDto
    )

    @Serializable
    data class MeasureDto(
        val amount: Double,
        val unitShort: String,
        val unitLong: String
    )
}