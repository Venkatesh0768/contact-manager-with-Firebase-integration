package com.example.contactmanager

data class User(val name : String , val email : String , val password : String , val usernmae : String )

data class  contactdetails(val name : String , val email :String , val number : String)

data class userdata(var name: String? = "",
                    var email: String? = "",
                    var number: String? = "")
