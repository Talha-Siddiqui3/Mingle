package com.mingle.mingle.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.mingle.mingle.R
import com.mingle.mingle.extensions.printLog
import com.mingle.mingle.extensions.showToastLong
import com.mingle.mingle.models.MatchInterestModel
import com.mingle.mingle.models.MyUser
import com.mingle.mingle.models.UserModel

class FindAFriendMainRvAdaptor(
    private val context: Context,
    private val userObjects: ArrayList<UserModel>
) :
    RecyclerView.Adapter<FindAFriendMainRvAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(context, inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val realPosition: Int = position % userObjects.size

        holder.name?.text = userObjects[realPosition].name
        holder.description?.text = userObjects[realPosition].description
        holder.city?.text = userObjects[realPosition].city
        holder.image?.let { Glide.with(context).load(userObjects[realPosition].image).into(it) }

        val rvAdaptor = userObjects[realPosition].interests?.let { getInterestsList(it) }?.let {
            FindAFriendChipsInnerRVAdaptor(
                context,
                it
            )
        }

        rvAdaptor?.let {
            holder.interests?.adapter = it
        }

    }

    private fun getInterestsList(userInterests: ArrayList<String>): ArrayList<MatchInterestModel> {
        val matchedInterestModelList = ArrayList<MatchInterestModel>()

        "myInterests".printLog(MyUser.interests.toString())
        "userInterests".printLog(userInterests.toString())
        val myInterests = MyUser.interests ?: ArrayList()

        for (interest: String in userInterests) {
            matchedInterestModelList.add(
                MatchInterestModel(
                    interest,
                    myInterests.contains(interest)
                )
            )
        }

        "matchedInterestModelList".printLog(matchedInterestModelList.toString())

        return matchedInterestModelList

    }

    override fun getItemCount(): Int {
        if (userObjects.size > 0) {
            return Integer.MAX_VALUE
        }
        return 0

    }


    inner class MyViewHolder(
        private val context: Context,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.find_a_friend_rv_item, parent, false)) {

        var name: TextView? = null
        var image: ImageView? = null
        var interests: RecyclerView? = null
        var description: TextView? = null
        var city: TextView? = null

        var addFriendButton: ImageView? = null
        var shareButton: ImageView? = null
        var messageButton: ImageView? = null


        init {
            name = itemView.findViewById<TextView>(R.id.name)
            image = itemView.findViewById<ImageView>(R.id.profile_image)
            interests = itemView.findViewById<RecyclerView>(R.id.interestsRv)
            description = itemView.findViewById<TextView>(R.id.description)
            city = itemView.findViewById<TextView>(R.id.location)
            addFriendButton = itemView.findViewById<ImageView>(R.id.addFriend)
            shareButton = itemView.findViewById<ImageView>(R.id.shareProfile)
            messageButton = itemView.findViewById<ImageView>(R.id.messageFriend)


            (addFriendButton as ImageView).setOnClickListener {
                "Friend Request Sent!".showToastLong(context)
                userObjects.removeAt(adapterPosition)
                notifyDataSetChanged()
            }

            addLayoutManagerToRv(interests as RecyclerView)

        }

    }

    private fun addLayoutManagerToRv(rv: RecyclerView) {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rv?.layoutManager = layoutManager
    }

}