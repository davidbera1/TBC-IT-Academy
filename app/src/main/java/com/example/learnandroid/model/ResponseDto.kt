package com.example.learnandroid.model

import com.example.learnandroid.room.entities.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val status: Boolean,
    @SerialName("additional_data")
    val additionalData: String? = null,
    val options: String? = null,
    val permissions: List<String?>,
    val users: List<UserDto>
) {
    @Serializable
    data class UserDto(
        val id: Int,
        val avatar: String?,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        val about: String?,
        @SerialName("activation_status")
        val activationStatus: Double
    ) {
        fun toUserEntity(): User {
            return User(
                id = this.id,
                firstName = this.firstName,
                lastName = this.lastName,
                profileImage = this.avatar,
                about = this.about,
                activationStatus = when {
                    activationStatus <= 0 -> 0
                    activationStatus == 1.0 -> 1
                    activationStatus in 2.0..23.0 -> 2
                    else -> 24
                }
            )
        }

    }
}
