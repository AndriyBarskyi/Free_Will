package com.example.freewill.models

class Teacher {
    private var fullName
            : String? = null
    private var ratings
            : Map<String, Int>? = null
    private var reviews: Map<String, String>? = null

    constructor(fullName: String?, ratings: Map<String, Int>?, reviews: Map<String, String>?) {
        this.fullName = fullName
        this.ratings = ratings
        this.reviews = reviews
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setName(name: String?) {
        this.fullName = name
    }

    fun getRatings(): Map<String, Int>? {
        return ratings
    }

    fun setRatings(ratings: Map<String, Int>?) {
        this.ratings = ratings
    }

    fun getReviews(): Map<String, String>? {
        return reviews
    }

    fun setReviews(reviews: Map<String, String>?) {
        this.reviews = reviews
    }
}