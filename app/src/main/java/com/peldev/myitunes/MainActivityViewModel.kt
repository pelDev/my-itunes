package com.peldev.myitunes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: MusicRepository) : ViewModel() {

    companion object {
        val FACTORY =
                singleArgViewModelFactory(::MainActivityViewModel)
    }

    val searchValue = MutableLiveData<String>()

    private val _spinner = MutableLiveData<Boolean>(false)

    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _snackBar = MutableLiveData<String?>()

    val snackbar: LiveData<String?>
        get() = _snackBar

    val musicList = repository.musicList

    private fun launchLoad(block: suspend () -> Unit) : Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (e: Throwable) {
                _snackBar.value = e.message
            } finally {
                _spinner.value = false
            }
        }
    }

    fun loadMusic() {
        searchValue.value?.let {
            launchLoad {
                val artisteName = it.replace("\\s".toRegex(), "+")
                repository.searchForMusic(artisteName.toLowerCase())
            }
        }
    }

}