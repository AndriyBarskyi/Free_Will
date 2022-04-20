package com.example.freewill.models

//class User {
//    var userName: String = "";
//    var groupName: String = "";
//    var email: String = "";
//    var password: String = "";
//
//    constructor(userName: String, groupName: String, email: String, password: String){
//        this.userName = userName
//        this.groupName = groupName
//        this.email = email
//        this.password = password
//    }
//}

data class User(val userName: String? = null,
                val groupName: String? = null,
                val email: String? = null,
                val password: String? = null){

}
