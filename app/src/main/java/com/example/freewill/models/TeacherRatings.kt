package com.example.freewill.models

import com.google.firebase.database.Exclude
import kotlin.math.roundToInt

class TeacherRatings {
    private var modernity: Float? = null
    private var demanding: Float? = null
    private var loyalty: Float? = null
    private var teachingSkills: Float? = null
    private var ratingsCount: Int = 0
    private var avgRating: Float? = null

    constructor(
        modernity: Float?,
        demanding: Float?,
        loyalty: Float?,
        teachingSkills: Float?,
        ratingsCount: Int,
        meanRating: Float?
    ) {
        this.modernity = modernity
        this.demanding = demanding
        this.loyalty = loyalty
        this.teachingSkills = teachingSkills
        this.ratingsCount = ratingsCount
        this.avgRating = meanRating
    }


    fun addRatings() {

        ratingsCount++
        calcAvgRating()
    }

    private fun getNextAvg(meanValue: Int, nextValue: Int): Int {
        return meanValue + ((nextValue - meanValue) / (ratingsCount + 1))
    }

/*    fun getAvgRating(): Int {
        return avgRating
    }*/

    private fun calcAvgRating() {
        avgRating = 0.0f
        avgRating = (modernity!! + demanding!! + loyalty!! + teachingSkills!!) / 4.0f

    }

    @Exclude
    fun toMap(): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["modernity"] = modernity!!
        result["demanding"] = demanding!!
        result["loyalty"] = loyalty!!
        result["teachingSkills"] = teachingSkills!!
        result["ratingsCount"] = ratingsCount
        result["avgRating"] = avgRating!!
        return result
    }

}