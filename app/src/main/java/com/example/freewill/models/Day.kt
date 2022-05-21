package com.example.freewill.models

data class Day(val firstpara: Para? = null,
               val secondpara: Para? = null,
               val thirdpara: Para? = null,
               val fourthpara: Para? = null,
               val fifthpara: Para? = null)
{}

data class Para(val para: String? = null,
                val audutoria: String? = null)
{}