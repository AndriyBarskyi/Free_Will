package com.example.freewill.models

import com.google.firebase.database.Exclude
import kotlin.math.roundToInt

class Teacher {
    private var fullName
            : String?
    private var ratings
            : MutableMap<String, Int>? = null
    private var ratingsCount: Int = 0
    private var reviews: MutableMap<String, String>? = null
    private var department: String? = null
    private var avgRating: Int = 0

    constructor(fullName: String?, department: String?) {
        this.fullName = fullName
        this.department = department
    }

    constructor(
        fullName: String?,
        ratings: MutableMap<String, Int>?,
        ratingsCount: Int,
        reviews: MutableMap<String, String>?,
        department: String?,
        meanRating: Int
    ) {
        this.fullName = fullName
        this.ratings = ratings
        this.ratingsCount = ratingsCount
        this.reviews = reviews
        this.department = department
        this.avgRating = meanRating
    }

    fun getFullName(): String? {
        return fullName
    }

    fun addRatings(ratings: MutableMap<String, Int>) {
        if (this.ratings == null) {
            this.ratings = ratings
        } else {
            ratings.keys.forEach { key ->
                this.ratings?.set(
                    key,
                    getNextAvg(ratings.getValue(key), this.ratings!!.getValue(key))
                )
            }
        }
        ratingsCount++
        calcAvgRating()
    }

    private fun getNextAvg(meanValue: Int, nextValue: Int): Int {
        return meanValue + ((nextValue - meanValue) / (ratingsCount + 1))
    }

    fun getAvgRating(): Int {
        return avgRating
    }

    private fun calcAvgRating() {
        avgRating = 0
        this.ratings?.values?.forEach { value ->
            avgRating += value
        }
        avgRating = (avgRating.toFloat() / 4).roundToInt()
    }

    @Exclude
    fun toMap(): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["department"] = department!!
        result["ratings"] = ratings!!
        result["ratingsCount"] = ratingsCount
        result["reviews"] = reviews!!
        result["avgRating"] = avgRating
        return result
    }

}