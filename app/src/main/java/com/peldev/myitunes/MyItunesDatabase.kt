package com.peldev.myitunes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Music constructor(var name: String, var artWork: String,
                                @PrimaryKey(autoGenerate = true) val id: Int?)

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(music: Music)

    @get:Query("SELECT * FROM music")
    val musicList : LiveData<List<Music>?>

    @Query("DELETE FROM music")
    suspend fun deleteAll()
}

@Database(entities = [Music::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract val musicDao: MusicDao
}

private lateinit var INSTANCE: MusicDatabase

fun getDatabase(context: Context) : MusicDatabase {
    synchronized(MusicDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context, MusicDatabase::class.java, "musics_db")
                    .build()
        }
    }
    return INSTANCE
}
