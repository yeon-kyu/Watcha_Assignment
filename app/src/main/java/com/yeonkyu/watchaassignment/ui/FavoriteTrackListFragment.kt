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

        mFavoriteViewModel.liveFavoriteTrackList.observe(mBinding.lifecycleOwner!!,{
            for(track in it){
                //trackAdapter.addLast(track)
            }
        })

        mFavoriteViewModel.getFavoriteTrackList()
    }

}