package com.mingle.mingle.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mingle.mingle.MyBaseClass
import com.mingle.mingle.databinding.ActivityAuthenticationBinding
import com.mingle.mingle.extensions.printLog
import java.util.concurrent.TimeUnit


private lateinit var binding: ActivityAuthenticationBinding

class AuthenticationActivity : MyBaseClass() {

    private var phoneNumber: String = ""
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        addCommonViews(view, this)
        setOnClickListeners()
        setCallbacks()
    }

    private fun setCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showUnknownError()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                "code_sent".printLog("onCodeSent:$verificationId")

                val intent = Intent(
                    this@AuthenticationActivity,
                    AuthenticationVerifyActivity::class.java
                )
                intent.putExtra("verificationId", verificationId)
                intent.putExtra("phoneNumber", verificationId)

                hideLoading()

                startActivity(intent)
            }

        }
    }

    private fun sendOtp() {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun setOnClickListeners() {
        binding.nextButton.setOnClickListener {
            phoneNumber = "+1" + binding.phoneNumberEditText.text.toString()
            showLoading()
            sendOtp()
        }

    }
}