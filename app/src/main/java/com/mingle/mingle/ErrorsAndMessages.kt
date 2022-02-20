package com.mingle.mingle

object ErrorsAndMessages {
    const val noEmailError =
        "Please enter an email address!"  // Don't need it for now, but we might use it later
    const val noPasswordError = "Please enter password!"
    const val noFirstNameError = "Please enter First Name!"
    const val noLastNameError = "Please enter Last Name!"
    const val noLocationError = "Please enter location!"
    const val locationUnknownError = "Unfortunately we couldn't get your location, please try again"
    const val incorrectCredentialsError =
        "It seems like you have entered an invalid email or password. Please try again."

    const val successfulSignUp = "Successfully signed up"

    const val noPhoneNumberError = "We need your phone number to log you in! .-."

    const val noOtpError = "Please enter otp!"

    const val otpConfirmationMessage = "Are you sure you want to sent an OTP to this number "

    const val incompleteCode =
        "Please enter all six digits of your code."

    const val unknownError =
        "An unknown error just occurred. Please try again in a bit. :("

    const val invalidCode = "Your verification code seems to be incorrect."

    const val networkError =
        "There seems to be an error connecting to the server. Please try again in a while"

    const val quotaExceeded = "Woaah! You've tried too many times. Give it a break for now"

    const val invalidNumber =
        "The phone number you've entered seems to be incorrect.\nMake sure you type your number with your country code"

    const val dataIncomplete = "Please fill in all the required fields."

    const val invalidEmail = "Please enter a valid email address."


    const val nullLocation =
        "Unfortunately we can't fetch your location. Make sure the device location is on and you are connected to the internet."
}
