package com.example.jetreader.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class MBook(
    @Exclude var docId : String? = null,
    var Uid: String? = null,
    var title: String? = null,
    var Author: List<String>? = null,
    var notes: String? = null,
    var category: List<String>? = null,
    var images: ImageLinks? = null,
    var description: String? = null,

    @get:PropertyName("start_reading")
    @set:PropertyName("start_reading")
    var startReading : com.google.firebase.Timestamp? = null,

    @get:PropertyName("end_reading")
    @set:PropertyName("end_reading")
    var endReading : com.google.firebase.Timestamp? = null,

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: Int? = null,

    @get:PropertyName("average_rating")
    @set:PropertyName("average_rating")
    var averageRating: Float? = 0.0f,

    @get:PropertyName("ratings_count")
    @set:PropertyName("ratings_count")
    var ratingsCount: Int? = 0,

    var subtitle: String? = null,

    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String? = null,
    val bookID: String? = null,
)
