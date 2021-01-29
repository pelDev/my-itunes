package com.peldev.myitunes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.snackbar.Snackbar
import com.peldev.myitunes.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val database = getDatabase(this)
        val repository = MusicRepository(MusicApi.retrofitService, database.musicDao)
        ViewModelProvider(this, MainActivityViewModel.FACTORY(repository))
                .get(MainActivityViewModel::class.java)
    }

    private val adapter by lazy {
        MusicRecyclerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.viewModel, viewModel)

        viewModel.musicList.observe(this) {value ->
            value?.let {
                if (value.isNotEmpty()) {
                    list_music.visibility = View.VISIBLE
                    no_result.visibility = View.GONE
                    adapter.setMusics(value)
                } else {
                    list_music.visibility = View.GONE
                    no_result.visibility = View.VISIBLE
                }
            }
        }

        viewModel.snackbar.observe(this) {message ->
            message?.let {
                showSnackBar(message)
            }
        }

        viewModel.spinner.observe(this) {
            showOrHideSpinner(it)
        }

        setRecyclerView()
    }

    private fun showOrHideSpinner(show: Boolean) {
        if (show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(list_music, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setRecyclerView() {
        list_music.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(
            this, 2,
            GridLayoutManager.HORIZONTAL, false
        )

        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }

        list_music.layoutManager = layoutManager
        list_music.adapter = adapter

        val smallPadding = resources
            .getDimensionPixelSize(R.dimen.mi_staggered_music_grid_spacing_small)
        val largePadding = resources
            .getDimensionPixelSize(R.dimen.mi_staggered_music_grid_spacing_large)
        list_music.addItemDecoration(MusicGridItemDecoration(largePadding, smallPadding))
    }

}