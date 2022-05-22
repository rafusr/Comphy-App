package com.comphy.photo.data.source.remote.response.post.feed


import com.google.gson.annotations.SerializedName

data class FeedResponseUserPost(
    @SerializedName("created_date")
    val createdDate: Long? = null,
    @SerializedName("deleted_date")
    val deletedDate: String? = null,
    @SerializedName("description")
    val description: String,
    @SerializedName("experiences")
    val experiences: List<Any> = listOf(),
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("job")
    val job: String,
    @SerializedName("lengthFollowers")
    val lengthFollowers: Int,
    @SerializedName("lengthFollowing")
    val lengthFollowing: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("numberPhone")
    val numberPhone: String? = null,
    @SerializedName("profileBannerLink")
    val profileBannerLink: String? = null,
    @SerializedName("profilePhotoLink")
    val profilePhotoLink: String? = null,
    @SerializedName("socialMedia")
    val socialMedia: String? = null,
    @SerializedName("subscription")
    val subscription: FeedResponseSubscription,
    @SerializedName("updated_date")
    val updatedDate: String? = null,
    @SerializedName("username")
    val username: String
)