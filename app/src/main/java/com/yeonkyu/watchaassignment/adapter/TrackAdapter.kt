package com.yeonkyu.watchaassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.data.entities.TrackResult

class TrackAdapter(context: Context) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>(){
    private val mContext = context
    private val trackList = ArrayList<TrackResult>()
    private var starClickListener: OnStartClickListener? = null

    interface OnStartClickListener{
        fun onClick(track: TrackResult)
    }

    fun setStarClickListener(listener: OnStartClickListener){
        starClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_track_list,parent,false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.onBind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun addLast(track:TrackResult){
        trackList.add(track)
        notifyDataSetChanged()
    }

    fun removeAll(){
        trackList.clear()
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val trackImage = itemView.findViewById<ImageView>(R.id.track_list_img)
        private val trackNameText = itemView.findViewById<TextView>(R.id.track_list_track_name_text)
        private val collectionNameText = itemView.findViewById<TextView>(R.id.track_list_collection_name_text)
        private val artistNameText = itemView.findViewById<TextView>(R.id.track_list_artist_name_text)
        private val starImage = itemView.findViewById<ImageButton>(R.id.track_list_star_button)

        fun onBind(track: TrackResult){
            trackNameText.text = track.trackName
            collectionNameText.text = track.collectionName
            artistNameText.text = track.artistName

        }
    }
}