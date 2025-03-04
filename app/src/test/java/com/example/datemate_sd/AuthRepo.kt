package com.example.datemate_sd

interface AuthRepo {

    fun login( email:String,password:String,callback:(Boolean,String) ->Unit)

}