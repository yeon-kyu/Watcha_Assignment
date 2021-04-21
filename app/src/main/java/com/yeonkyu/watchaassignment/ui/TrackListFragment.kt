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
import com.yeonkyu.watchaassignment.databinding.FragmentTrackListBinding
import com.yeonkyu.watchaassignment.viewmodels.TrackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackListFragment: Fragment(), TrackListListener {

    private lateinit var binding: FragmentTrackListBinding
    private val trackViewModel: TrackViewModel by viewModel()
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_track_list, container, false)
        binding.lifecycleOwner = activity
        binding.viewModel = trackViewModel

        setupView()
        setupViewModel()
        setTrackStarClickListener()

        trackViewModel.searchNextTrack()

        return binding.root
    }

    private fun setupView(){ //view초기화
        val trackRecyclerView: RecyclerView = binding.trackListRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        trackRecyclerView.layoutManager = linearLayoutManager

        trackAdapter = TrackAdapter()
        trackAdapter.setTouchEndScroll(object: TrackAdapter.OnTouchEndScrollListener{
            override fun onTouchEndScroll() {
                trackViewModel.searchNextTrack()
            }
        })

        trackRecyclerView.adapter = trackAdapter
    }

    private fun setupViewModel(){ //viewmModel 초기화
        //화면 회전과 같은 reCreate 일때 adapter 내 중복 쌓임을 방지하기 위해 trackList를 clear()합니다
        trackViewModel.resetTrackList()

        trackViewModel.liveTrackList.observe(binding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","track list change observed")
            trackAdapter.setTrackList(it)
        })
        // ListAdapter 알아보기
        //submitList 사용 - 라사이클러뷰가 비교
        //paged list adapter 가 페이징도 해준다.

        trackViewModel.setTrackListener(this)
    }

    //recyclerview 내의 Star를 클릭했을때 앱 내 DB 수정
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

                if(track.isFavorite){ //이미 favoriteTrack에 있을 경우 -> room에서 삭제
                    trackViewModel.deleteFavorite(favorite)
                }
                else{ //favoriteTrack에 없을 때 -> room에 추가
                    trackViewModel.insertFavorite(favorite)
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