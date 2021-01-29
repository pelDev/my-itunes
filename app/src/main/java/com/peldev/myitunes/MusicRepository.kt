package com.peldev.myitunes

import androidx.lifecycle.LiveData
import org.json.JSONObject

class MusicRepository(private val service: MusicSearchService, private val musicDao: MusicDao) {

    val musicList: LiveData<List<Music>?> = musicDao.musicList

    suspend fun searchForMusic(artisteName: String) {
        try {
            service.getMusicList(artisteName).also {
                if (it.isNotEmpty())
                    convertRelevantJsonToMusicThenAddToDatabase(it)
                else
                    throw Throwable("No Result Found")
            }
        } catch (e: Throwable) {
            throw Throwable("Unable load music", e)
        }
    }

    private suspend fun convertRelevantJsonToMusicThenAddToDatabase(result: String) {
        val resultJson = JSONObject(result)
        val jsonArray = resultJson.getJSONArray("results")
        musicDao.deleteAll()
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            if (jsonObject.getString("kind") == "song") {
                val musicTitle = jsonObject.getString("trackName")
                val artWork = jsonObject.getString("artworkUrl100")
                musicDao.insertMusic(Music(musicTitle, artWork, null))
            }
        }
    }

}