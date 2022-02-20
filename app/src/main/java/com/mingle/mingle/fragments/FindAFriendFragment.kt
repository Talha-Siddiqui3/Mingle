package com.mingle.mingle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.firebase.firestore.FirebaseFirestore
import com.mingle.mingle.MyBaseClass
import com.mingle.mingle.R
import com.mingle.mingle.adaptors.FindAFriendMainRvAdaptor
import com.mingle.mingle.extensions.printLog
import com.mingle.mingle.models.MyUser
import com.mingle.mingle.models.UserModel


class FindAFriendFragment : Fragment() {

    val userObjectsList = ArrayList<UserModel>()
    var rvAdaptor: FindAFriendMainRvAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val screenLayout = inflater.inflate(R.layout.fragment_find_a_friend, container, false)
        initViews(screenLayout)
        fetchData()
        return screenLayout
    }

    private fun fetchData() {
        (context as MyBaseClass).showLoading()
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                (context as MyBaseClass).hideLoading()
                if (task.isSuccessful) {
                    for (document in task.result) {

                        if (document.getString("name") != MyUser.name) {
                            userObjectsList.add(
                                UserModel(
                                    document.getString("name"),
                                    document.getString("phone_number"),
                                    document.getString("email"),
                                    document.get("interests") as ArrayList<String>?,
                                    document.getString("city"),
                                    document.getString("date_of_birth"),
                                    document.getString("profile_image"),
                                    document.getString("description")
                                )
                            )
                        }


                    }
                    "size".printLog(userObjectsList.size)
                    rvAdaptor!!.notifyDataSetChanged()

                } else {
                    "users".printLog("error fetching data")
                    (context as MyBaseClass).showUnknownError()
                }
            }
    }

    private fun initViews(screenLayout: View?) {
        val rv = screenLayout?.findViewById<RecyclerView>(R.id.rv)
        rvAdaptor = context?.let { FindAFriendMainRvAdaptor(it, userObjectsList) }
        rv?.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        rv?.adapter = rvAdaptor

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv)
    }

    companion object {
        fun newInstance() = FindAFriendFragment()
    }
}


/*
val obj1 = MatchInterestModel("photography", true)
val obj2 = MatchInterestModel("music", false)
val obj3 = MatchInterestModel("art", false)
val obj4 = MatchInterestModel("dance", true)


interestsList.add(obj1)
interestsList.add(obj2)
interestsList.add(obj3)
interestsList.add(obj4)

val layoutManager = FlexboxLayoutManager(context)
layoutManager.flexDirection = FlexDirection.ROW
layoutManager.justifyContent = JustifyContent.FLEX_START
rv?.layoutManager = layoutManager
rv?.adapter = rvAdaptor*/
