package com.example.notes.di

import com.example.notes.data.datasource.NoteDataSource
import com.example.notes.data.repository.NoteRepositoryImpl
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.LoadNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideNoteRepository(noteDataSource: NoteDataSource): NoteRepository {
        return NoteRepositoryImpl(noteDataSource)
    }

    @Provides
    fun provideLoadNotesUseCase(noteRepository: NoteRepository): LoadNotesUseCase {
        return LoadNotesUseCase(noteRepository)
    }

    @Provides
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }
}