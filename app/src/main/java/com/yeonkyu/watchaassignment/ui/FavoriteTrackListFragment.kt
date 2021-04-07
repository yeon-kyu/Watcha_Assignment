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

    private lateinit var mBinding: FragmentFavoriteTrackListBinding
    private val mFavoriteViewModel : FavoritesViewModel by viewModel()
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite_track_list, container, false)
        mBinding.lifecycleOwner = activity
        mBinding.viewModel = mFavoriteViewModel

        setupView()
        setupViewModel()
        setTrackStarClickListener()

        mFavoriteViewModel.getFavoriteTrackList()
        return mBinding.root
    }

    private fun setupView(){
        val favoriteTrackRecyclerView: RecyclerView = mBinding.favoriteTrackListRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        favoriteTrackRecyclerView.layoutManager = linearLayoutManager

        trackAdapter = TrackAdapter(requireContext())
        favoriteTrackRecyclerView.adapter = trackAdapter
    }

    private fun setupViewModel(){
        mFavoriteViewModel.liveFavoriteTrackList.observe(mBinding.lifecycleOwner!!, {
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
                mBinding.favoriteTrackListGuideText.visibility = View.VISIBLE
            }
            else{
                mBinding.favoriteTrackListGuideText.visibility = View.GONE
            }
        })
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

                if(track.isFavorite){ //이미 favoriteTrack에 있을 경우 -> room에서 삭제
                    mFavoriteViewModel.deleteFavorite(favorite)
                }
                else{ //favoriteTrack에 없을 때 -> room에 추가
                    mFavoriteViewModel.insertFavorite(favorite)
                }
            }
        })
    }

}