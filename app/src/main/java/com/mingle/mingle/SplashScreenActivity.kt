package com.mingle.mingle

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.mingle.mingle.auth.AuthenticationActivity
import com.mingle.mingle.databinding.ActivitySplashScreenBinding
import com.mingle.mingle.extensions.printLog
import com.mingle.mingle.models.MyUser


const val SPLASH_SCREEN_DURATION = 1500L
private lateinit var binding: ActivitySplashScreenBinding

class SplashScreenActivity : MyBaseClass() {

    enum class UserState {
        NEW, INCOMPLETE, COMPLETE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        addCommonViews(view, this)

        val auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        val signin = currentUser == null



        Handler(Looper.getMainLooper()).postDelayed({
            if (!signin) {
                getUserDetails(auth.currentUser!!.uid)
                return@postDelayed
            }

            startActivityAccordingly(userState = UserState.NEW)
        }, SPLASH_SCREEN_DURATION)

    }

    private fun getUserDetails(userID: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Users").document(userID)

        docRef.get().addOnCompleteListener { task ->
            var userState: UserState? = null
            if (task.isSuccessful) {
                val document = task.result
                "document".printLog(document.toString())
                userState = if (document.exists()) {
                    addDataToMyUser(document)
                    UserState.COMPLETE
                } else {
                    UserState.INCOMPLETE
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

    private fun startActivityAccordingly(userState: UserState) {

        val activityToStart: Class<*>? = when (userState) {
            UserState.NEW -> AuthenticationActivity::class.java
            UserState.COMPLETE -> HomeActivity::class.java
            UserState.INCOMPLETE -> UserInfoSignUpActivity::class.java
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