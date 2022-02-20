package com.mingle.mingle.models

object MyUser {

    var name: String? = null
    var phoneNumber: String? = null
    var email: String? = null
    var interests: ArrayList<String>? = null
    var city: String? = null
    var dateOfBirth: String? = null
    var image: String? = null
    var description: String? = null


    fun initUser(
        name: String?,
        phoneNumber: String?,
        email: String?,
        interests: ArrayList<String>?,
        city: String?,
        dateOfBirth: String?,
        image: String?,
        description: String?
    ) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.email = email
        this.interests = interests
        this.city = city
        this.dateOfBirth = dateOfBirth
        this.image = image
        this.description = description
    }

}