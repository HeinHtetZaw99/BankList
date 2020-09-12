package com.daniel.banklist.models.vos.network

import com.google.gson.annotations.SerializedName


data class BankListResponse(
	@SerializedName("code")
	val code: Int = 0,

	@SerializedName("data")
	val data: List<DataItem>? = ArrayList(),

	@SerializedName("message")
	val message: String? = null
)


data class DataItem(
	@SerializedName("instruction2")
	val instruction2: String? = "",

	@SerializedName("endColor")
	val endColor: String? = null,

	@SerializedName("instruction1")
	val instruction1: String? = "",

	@SerializedName("length")
	val length: Int? = null,

	@SerializedName("logoThumbnail")
	val logoThumbnail: String? = "",

	@SerializedName("description")
	val description: String? = "",

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("logoUrl")
	val logoUrl: String? = null,

	@SerializedName("enabled")
	val enabled: Int? = null,

	@SerializedName("startColor")
	val startColor: String? = null,

	@SerializedName("centerColor")
	val centerColor: String? = null,

	@SerializedName("bgColor")
	val bgColor: String? = null,

	@SerializedName("is_major")
	val isMajor: Boolean? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("maxAmount")
	val maxAmount: String? = "",

	@SerializedName("fontColor")
	val fontColor: String? = null
) 
