package com.example.freewill

import android.util.Log

class InformationAboutRooms {
    var info = mapOf( "237" to arrayOf("0","300","630","792" ),"238" to arrayOf("0","300","0","629" ),"239" to arrayOf("0","300","980","1020" )
        ,"240" to arrayOf("0","300","292","403" ),"241" to arrayOf("0","300","211","291" ),"241a" to arrayOf("0","180","130","210" ),"242" to arrayOf("0","300","0","129" ))
    fun search(x:String,y:String,onSuccess:(String)->Unit)
    {
        Log.d("in",y)
        var info_=""
        for(r in info) {
            val numberRoom=r.key
            val room = info[numberRoom]
            info_ =
                when (room?.getOrNull(0)!! < x && room.get(1) > x && room.get(2) < y && room.get(3) > y) {
                    true -> numberRoom
                    else -> "238"
                }
            if(info_!="238")
                break

        }
        onSuccess(info_)
    }
}