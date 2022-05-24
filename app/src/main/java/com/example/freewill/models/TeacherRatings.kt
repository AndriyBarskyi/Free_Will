package com.example.freewill.models

import com.google.firebase.database.Exclude
import kotlin.math.round
import kotlin.math.roundToInt

class TeacherRatings {
    var modernity: Float = 0f
    var demanding: Float = 0f
    var loyalty: Float = 0f
    var teachingSkills: Float = 0f
    var ratingsCount: Int = 0
    var avgRating: Float = 0f

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
    }


    private fun getNewAvg(avg: Float, newAvg: Float): Float {
        return avg + ((newAvg - avg) / (ratingsCount + 1))
    }

    fun updateRatings(newModernity: Float,
                              newDemanding: Float,
                              newLoyalty: Float,
                              newTeachingSkills: Float) {
        val meanRating: Float = (newModernity + newDemanding + newLoyalty + newTeachingSkills) / 4.0f
        avgRating = getNewAvg(avgRating, meanRating)
        modernity = getNewAvg(modernity, newModernity)
        demanding = getNewAvg(demanding, newDemanding)
        loyalty = getNewAvg(loyalty, newLoyalty)
        teachingSkills = getNewAvg(teachingSkills, newTeachingSkills)
        ratingsCount += 1
    }

/*    @Exclude
    fun toMap(): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["modernity"] = modernity
        result["demanding"] = demanding
        result["loyalty"] = loyalty
        result["teachingSkills"] = teachingSkills
        result["ratingsCount"] = ratingsCount
        result["avgRating"] = avgRating.toString()
        return result
    }*/
}