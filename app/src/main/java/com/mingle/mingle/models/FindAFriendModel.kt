package com.mingle.mingle.models

data class FindAFriendModel(
    var name: String?,
    var interests: ArrayList<MatchInterestModel>?,
    var dateOfBirth: String?,
    var university: String?,
    var email: String?,
    var city: String?,
    var phoneNumber: String?,
    var image: String?

)
