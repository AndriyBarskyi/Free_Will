package com.example.freewill

class InformationAboutRooms {
    var info = mapOf( "237" to arrayOf("0","300","550","750" ))
    fun search(x:String,y:String,onSuccess:(String)->Unit)
    {
        for(r in info) {
            val numberRoom=r.key
            val room = info[numberRoom]
            val info =
                when (room?.getOrNull(0)!! < x && room.get(1) > x && room.get(2) < y && room.get(3) > y) {
                    true -> "237"
                    else -> "238"
                }
            onSuccess(info)
        }
    }
}