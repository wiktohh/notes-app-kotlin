package com.example.notatki.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    object NoteDb {
        private var db: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            if(db==null){
                db= Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "note-database"
                ).build()
            }
            return db!!
        }
    }
}

