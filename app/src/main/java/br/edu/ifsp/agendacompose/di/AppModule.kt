package br.edu.ifsp.agendacompose.di

import android.app.Application
import androidx.room.Room
import br.edu.ifsp.agendacompose.data.datasource.ContactDatabase
import br.edu.ifsp.agendacompose.data.repository.ContactRepositoryImpl
import br.edu.ifsp.agendacompose.domain.repository.ContactRepository
import br.edu.ifsp.agendacompose.domain.usecase.ContactUseCases
import br.edu.ifsp.agendacompose.domain.usecase.DeleteContact
import br.edu.ifsp.agendacompose.domain.usecase.GetContact
import br.edu.ifsp.agendacompose.domain.usecase.GetContacts
import br.edu.ifsp.agendacompose.domain.usecase.InsertContact
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            ContactDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(db: ContactDatabase): ContactRepository {
        return ContactRepositoryImpl(db.contactDao)
    }

    @Provides
    @Singleton
    fun provideContactUseCases(repository: ContactRepository): ContactUseCases {
        return ContactUseCases(
            getContacts = GetContacts(repository),
            deleteContact = DeleteContact(repository),
            insertContact = InsertContact(repository),
            getContact = GetContact(repository)
        )
    }
}