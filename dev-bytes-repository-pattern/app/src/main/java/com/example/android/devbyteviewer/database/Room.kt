/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface VideoDao {
    @Insert(onConflict = REPLACE)
    fun insert(databaseVideo: DatabaseVideo)

    @Insert(onConflict = REPLACE)
    fun insertAll(videos: List<DatabaseVideo>)

    @Update
    fun update(databaseVideo: DatabaseVideo)

    @Query("Select * from database_video")
    fun getAllVideos(): LiveData<List<DatabaseVideo>>
}

@Database(entities = [DatabaseVideo::class], version = 1, exportSchema = false)
abstract class VideosDatabase: RoomDatabase() {
    abstract val videoDao : VideoDao
}