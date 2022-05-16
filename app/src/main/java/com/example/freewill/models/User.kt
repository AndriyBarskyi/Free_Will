package com.example.freewill.models

class User(groupName: String){
    val GroupName: String = groupName
    //var Password: String = ""

    constructor(groupName: String, password: String) : this(groupName){
        //Password = password
    }
}
