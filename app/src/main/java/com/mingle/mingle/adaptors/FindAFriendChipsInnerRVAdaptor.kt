package com.mingle.mingle.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mingle.mingle.R
import com.mingle.mingle.extensions.printLog
import com.mingle.mingle.models.MatchInterestModel

class FindAFriendChipsInnerRVAdaptor(
    private val context: Context,
    private val interests: ArrayList<MatchInterestModel>
) :
    RecyclerView.Adapter<FindAFriendChipsInnerRVAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(context, inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chipText?.text = interests[position].interest
        if (interests[position].matched) {
            "aa".printLog("YAS")
            holder.rootView?.setCardBackgroundColor(context.resources.getColor(R.color.secondary))
        }
        else{
            "aa".printLog("NO")
            holder.rootView?.setCardBackgroundColor(context.resources.getColor(R.color.primary))
        }
    }

    override fun getItemCount(): Int {
        return interests.size
    }


    inner class MyViewHolder(
        private val context: Context,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.chip_layout, parent, false)) {

        var chipText: TextView? = null
        var rootView: CardView? = null

        init {
            chipText = itemView.findViewById<TextView>(R.id.chipText)
            rootView = itemView as CardView
        }

    }

}