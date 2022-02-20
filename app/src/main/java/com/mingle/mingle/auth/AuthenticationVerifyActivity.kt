package com.mingle.mingle.auth


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.mingle.mingle.*
import com.mingle.mingle.databinding.ActivityAuthenticationVerifyBinding
import com.mingle.mingle.extensions.showToastShort
import com.mingle.mingle.models.MyUser
import com.mukesh.OnOtpCompletionListener


private lateinit var binding: ActivityAuthenticationVerifyBinding


class AuthenticationVerifyActivity : MyBaseClass() {

    private var verificationID: String = ""
    private var phoneNumber: String = ""
    private var code: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationVerifyBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        addCommonViews(view, this)
        binding.enterVerificationCodeTitle.text =
            "Enter the verification code sent to ${phoneNumber}"
        verificationID = intent.getStringExtra("verificationId") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        setOnClickListener()
        setOtpListener()
    }

    private fun setOtpListener() {
        binding.otpView.setOtpCompletionListener {
            code = it
        }
    }

    private fun setOnClickListener() {
        binding.verifyButton.setOnClickListener {
            if (code.isNotEmpty()) {
                val credential = PhoneAuthProvider.getCredential(verificationID, code)
                showLoading()
                signInWithPhoneAuthCredential(credential)
            } else {
                showCustomError("Please enter the otp code")
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result?.user
                    "Signed in".showToastShort(this)
                    getUserDetails(user!!.uid)

                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showCustomError(ErrorsAndMessages.invalidCode)
                    } else {
                        showUnknownError()
                    }
                }
            }
    }


    private fun getUserDetails(userID: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Users").document(userID)
        docRef.get().addOnCompleteListener { task ->
            var userState: SplashScreenActivity.UserState? = null
            if (task.isSuccessful) {
                val document = task.result
                userState = if (document.exists()) {
                    addDataToMyUser(document)
                    SplashScreenActivity.UserState.COMPLETE
                } else {
                    SplashScreenActivity.UserState.INCOMPLETE
                }

                startActivityAccordingly(userState)

            } else {
                showUnknownError()
            }
        }
    }

    private fun addDataToMyUser(document: DocumentSnapshot) {
        MyUser.initUser(
            document.getString("name"),
            document.getString("phone_number"),
            document.getString("email"),
            document.get("interests") as ArrayList<String>?,
            document.getString("city"),
            document.getString("date_of_birth"),
            document.getString("profile_image"),
            document.getString("description")
        )
    }

    private fun startActivityAccordingly(userState: SplashScreenActivity.UserState) {

        val activityToStart: Class<*>? = when (userState) {
            SplashScreenActivity.UserState.NEW -> AuthenticationActivity::class.java
            SplashScreenActivity.UserState.COMPLETE -> HomeActivity::class.java
            SplashScreenActivity.UserState.INCOMPLETE -> UserInfoSignUpActivity::class.java
        }
        startActivity(
            Intent(
                this,
                activityToStart
            )
        )
        finish()
    }
}