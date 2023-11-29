package com.example.kotlin.project.mine.login

data class UserInfo(
    var admin: Boolean,
    var chapterTops: IntArray,
    var coinCount: Int,
    var collectIds: IntArray,
    var email: String,
    var icon: String,
    var id: String,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)
