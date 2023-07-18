package com.ics342.labs

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "id") val id: Int,
    @Json(name = "give_name") val givenName: String,
    @Json(name = "family_name") val familyName: String,
    @Json(name = "age") val age: Int
) {
}