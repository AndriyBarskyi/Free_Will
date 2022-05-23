package com.example.freewill.models

import com.google.firebase.database.Exclude
import kotlin.math.round
import kotlin.math.roundToInt

class TeacherRatings {
    private var modernity: Float = 0f
    private var demanding: Float = 0f
    private var loyalty: Float = 0f
    private var teachingSkills: Float = 0f
    private var ratingsCount: Int = 0
    private var avgRating: Float = 0f

    constructor()

    constructor(
        modernity: Float,
        demanding: Float,
        loyalty: Float,
        teachingSkills: Float,
        ratingsCount: Int,
        meanRating: Float
    ) {
        this.modernity = modernity
        this.demanding = demanding
        this.loyalty = loyalty
        this.teachingSkills = teachingSkills
        this.ratingsCount = ratingsCount
        this.avgRating = meanRating
        calcNewAvg()
    }

    private fun calcNewAvg() {
        val meanRating: Float = (modernity + demanding + loyalty + teachingSkills) / 4.0f
        avgRating += ((meanRating - avgRating) / (ratingsCount + 1))
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