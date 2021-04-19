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
import com.yeonkyu.watchaassignment.utils.GlideUtil
class TrackAdapter : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>(){
    //코틀린 네이밍 컨벤션 쓰기
    private val trackList = ArrayList<TrackResult>()
    private var starClickListener: OnStartClickListener? = null
    private var touchEndScrollListener: OnTouchEndScrollListener? = null

    interface OnStartClickListener{
        fun onClick(track: TrackResult)
    }

    interface OnTouchEndScrollListener{
        fun onTouchEndScroll()
    }

    fun setStarClickListener(listener: OnStartClickListener){
        starClickListener = listener
    }

    fun setTouchEndScroll(listener: OnTouchEndScrollListener){
        touchEndScrollListener = listener
    }

    fun setTrackList(list: ArrayList<TrackResult>){
        trackList.clear()
        trackList.addAll(list)
        notifyDataSetChanged()
        //notifyDataSetChanged말고 다른 notify 함수들 잘 쓰는게 좋다
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_list,parent,false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.onBind(trackList[position])

        if(trackList.size - position <= 1){
            touchEndScrollListener?.onTouchEndScroll()
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun addLast(track:TrackResult){
        trackList.add(track)
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
        private val starButton = itemView.findViewById<ImageButton>(R.id.track_list_star_button)

        fun onBind(track: TrackResult){
            trackNameText.text = track.trackName
            collectionNameText.text = track.collectionName
            artistNameText.text = track.artistName

            starButton.setOnClickListener {
                starClickListener?.onClick(track)
                track.isFavorite = !track.isFavorite
                if(track.isFavorite){
                    starButton.setImageResource(R.drawable.icon_star_gold_32)
                }
                else{
                    starButton.setImageResource(R.drawable.icon_star_white_32)
                }
            }
            GlideUtil.displayImageFromUrl(trackImage.context,track.ImageUrl,trackImage)

            if(track.isFavorite){
                starButton.setImageResource(R.drawable.icon_star_gold_32)
            }
            else{
                starButton.setImageResource(R.drawable.icon_star_white_32)
            }
        }
    }
}