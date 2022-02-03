package com.example.android.devbyteviewer

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.android.devbyteviewer.database.VideosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltAppModule {
    @Singleton
    @Provides
    fun provideVideoDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            VideosDatabase::class.java,
            "video_database"
        ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideVideoDao(videosDatabase: VideosDatabase) = videosDatabase.videoDao
}