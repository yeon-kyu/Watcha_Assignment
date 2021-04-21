package com.yeonkyu.watchaassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.databinding.ItemTrackListBinding

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
        val binding:ItemTrackListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_track_list,parent,false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.onBind(trackList[position])

        if(trackList.size - position == 1){
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

    inner class TrackViewHolder(private val binding: ItemTrackListBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(track: TrackResult){
            binding.trackItem = track

            binding.trackListStarButton.setOnClickListener {
                starClickListener?.onClick(track)
                track.isFavorite = !track.isFavorite
                if(track.isFavorite){
                    binding.trackListStarButton.setImageResource(R.drawable.icon_star_gold_32)
                }
                else{
                    binding.trackListStarButton.setImageResource(R.drawable.icon_star_white_32)
                }
            }

            if(track.isFavorite){
                binding.trackListStarButton.setImageResource(R.drawable.icon_star_gold_32)
            }
            else{
                binding.trackListStarButton.setImageResource(R.drawable.icon_star_white_32)
            }
            binding.executePendingBindings()
        }
    }
}