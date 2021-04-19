package com.yeonkyu.watchaassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.adapter.TrackAdapter
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.databinding.FragmentFavoriteTrackListBinding
import com.yeonkyu.watchaassignment.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTrackListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTrackListBinding
    private val favoriteViewModel : FavoritesViewModel by viewModel()
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite_track_list, container, false)
        binding.lifecycleOwner = activity
        binding.viewModel = favoriteViewModel

        setupView()
        setupViewModel()
        setTrackStarClickListener()

        favoriteViewModel.getFavoriteTrackList()
        return binding.root
    }

    //viwe 초기화
    private fun setupView(){
        val favoriteTrackRecyclerView: RecyclerView = binding.favoriteTrackListRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        favoriteTrackRecyclerView.layoutManager = linearLayoutManager

        trackAdapter = TrackAdapter(requireContext())
        favoriteTrackRecyclerView.adapter = trackAdapter
    }

    //viewModel 초기화
    private fun setupViewModel(){
        favoriteViewModel.liveFavoriteTrackList.observe(binding.lifecycleOwner!!, {
            trackAdapter.removeAll() //화면 회전 등과 같은 상황에서 중첩 방지

            for (favorite in it) {
                val track: TrackResult = TrackResult(
                    favorite.trackId,
                    favorite.trackName,
                    favorite.collectionName,
                    favorite.artistName,
                    favorite.imgUrl,
                    true
                )
                trackAdapter.addLast(track)
            }
            trackAdapter.notifyDataSetChanged()

            if(it.isEmpty()){
                binding.favoriteTrackListGuideText.visibility = View.VISIBLE
            }
            else{
                binding.favoriteTrackListGuideText.visibility = View.GONE
            }
        })
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
                    favoriteViewModel.deleteFavorite(favorite)
                }
                else{ //favoriteTrack에 없을 때 -> room에 추가
                    favoriteViewModel.insertFavorite(favorite)
                }
            }
        })
    }

}