package br.edu.ifsp.agendacompose.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.agendacompose.domain.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {

    abstract val contactDao: ContactDao

    companion object {
        const val DATABASE_NAME = "contacts_db"
    }
}