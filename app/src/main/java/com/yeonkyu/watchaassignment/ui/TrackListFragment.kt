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
import com.yeonkyu.watchaassignment.databinding.FragmentTrackListBinding
import com.yeonkyu.watchaassignment.viewmodels.TrackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackListFragment: Fragment(), TrackListListener {

    private lateinit var mBinding: FragmentTrackListBinding
    private val mTrackViewModel: TrackViewModel by viewModel()
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

        mTrackViewModel.isLoading.observe(mBinding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","progressbar 변화 : $it")
            if(it){
                mBinding.trackListProgressbar.visibility = View.VISIBLE
            }
            else{
                mBinding.trackListProgressbar.visibility = View.INVISIBLE
            }
        })

        mTrackViewModel.liveTrackList.observe(mBinding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","track list 변화")
            trackAdapter.setTrackList(it)
        })
        mTrackViewModel.setTrackListener(this)

        mTrackViewModel.searchTrack()

    }

    private fun setTrackStarClickListener(){
        trackAdapter.setStarClickListener(object : TrackAdapter.OnStartClickListener{
            override fun onClick(track: TrackResult) {
                Log.e("CHECK_TAG","start clicked : ${track.trackName}")
            }
        })
    }

    override fun makeToast(msg: String) {
        activity?.runOnUiThread{
            Toast.makeText(activity,msg,Toast.LENGTH_LONG).show()
        }
    }

}