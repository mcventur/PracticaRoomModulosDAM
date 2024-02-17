package com.mpd.pmdm.practicaroommodulos.di

import android.content.Context
import androidx.room.Room
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideModuleDatabase(@ApplicationContext context: Context): ModuleDatabase {
        return Room.databaseBuilder(
            context,
            ModuleDatabase::class.java,
            "app_database"
        ).build()
    }
}