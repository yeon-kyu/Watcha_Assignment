package com.yeonkyu.watchaassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.adapter.TrackAdapter
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.listeners.TrackListListener
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
import com.yeonkyu.watchaassignment.databinding.FragmentTrackListBinding
import com.yeonkyu.watchaassignment.viewmodels.TrackViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackListFragment: Fragment(), TrackListListener {

    private lateinit var mBinding: FragmentTrackListBinding
    private val mTrackViewModel: TrackViewModel by viewModel()
    private val mRoomDB : FavoritesDao = get()
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_track_list, container, false)
        mBinding.lifecycleOwner = activity
        mBinding.viewModel = mTrackViewModel

        setupView()
        setupViewModel()
        setTrackStarClickListener()

        return mBinding.root
    }

    private fun setupView(){
        val trackRecyclerView: RecyclerView = mBinding.trackListRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        trackRecyclerView.layoutManager = linearLayoutManager

        trackAdapter = TrackAdapter(requireContext())
        trackRecyclerView.adapter = trackAdapter
    }

    private fun setupViewModel(){

        //화면 회전과 같은 reCreate 일때 adapter 내 중복 쌓임을 방지하기 위해 trackList를 clear()합니다
        mTrackViewModel.resetTrackList()

        mTrackViewModel.isLoading.observe(mBinding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","progressbar observed : $it")
            if(it){
                mBinding.trackListProgressbar.visibility = View.VISIBLE
            }
            else{
                mBinding.trackListProgressbar.visibility = View.INVISIBLE
            }
        })

        mTrackViewModel.liveTrackList.observe(mBinding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","track list observed")
            CoroutineScope(Dispatchers.Default).launch {
                val favoriteTrackIds = mRoomDB.getAllId()
                for(track in it){
                    var isFavorite = false
                    for(favoriteTrackId in favoriteTrackIds){
                        if(track.trackId == favoriteTrackId){
                            isFavorite = true
                            break
                        }
                    }
                    track.isFavorite = isFavorite

                    activity?.runOnUiThread {
                        trackAdapter.addLast(track)
                        trackAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
        mTrackViewModel.setTrackListener(this)

        mTrackViewModel.searchTrack()

    }

    private fun setTrackStarClickListener(){
        trackAdapter.setStarClickListener(object : TrackAdapter.OnStartClickListener {
            override fun onClick(track: TrackResult) {
                val favorite: Favorites = Favorites(
                    track.trackId,
                    trackName = track.trackName,
                    collectionName = track.collectionName,
                    artistName = track.artistName,
                    imgUrl = track.ImageUrl
                )
                CoroutineScope(Dispatchers.Default).launch {
                    mRoomDB.insertTrack(favorite)
                    Log.e("CHECK_TAG", "start clicked : ${track.trackName}")
                }

            }
        })
    }

    override fun makeToast(msg: String) {
        activity?.runOnUiThread{
            Toast.makeText(activity,msg,Toast.LENGTH_LONG).show()
        }
    }

}