package net.starry.cleanarch.data.model

data class ForkModel(
    val name: String,
    val full_name: String,
    val owner: UserModel
)
