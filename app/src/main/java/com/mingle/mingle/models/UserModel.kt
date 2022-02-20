package com.mingle.mingle.models

class UserModel(
    var name: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var interests: ArrayList<String>? = null,
    var city: String? = null,
    var dateOfBirth: String? = null,
    var image: String? = null,
    var description: String? = null
) {
}