package com.kkt1019.tpmylocationkakao.model

data class NaverUserInfoResponse(var resultcode:String, var message:String, var response:NidUser)

data class NidUser(var id:String, var email:String)
