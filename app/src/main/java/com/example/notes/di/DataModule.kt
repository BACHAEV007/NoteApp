package com.example.notes.di

import android.content.Context
import com.example.notes.data.datasource.NoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideNoteDataSource(@ApplicationContext context: Context): NoteDataSource {
        return NoteDataSource(context)
    }
}