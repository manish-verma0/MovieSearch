package com.manish.moviesearch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ratings (

  @SerializedName("Source")
  var Source : String? = null,
  @SerializedName("Value")
  var Value  : String? = null

):Parcelable