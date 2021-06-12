package com.niubo.mynote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.niubo.mynote.dao.NoteDao;
import com.niubo.mynote.entities.Note;

/**
 * The following code defines an AppDatabase class to hold the database.
 * AppDatabase defines the database configuration and serves as the app's main access point to the persisted data.
 * The database class must satisfy the following conditions:
 *
 * The class must be annotated with a @Database annotation
 * that includes an entities array that lists all of the data entities associated with the database.
 *
 * The class must be an abstract class that extends RoomDatabase.
 *
 * For each DAO class that is associated with the database,
 * the database class must define an abstract method
 * that has zero arguments and returns an instance of the DAO class.
 */

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase notesDatabase;

    /**
     * In simple words a static synchronized method will lock the class instead of the object,
     * and it will lock the class because the keyword static means: "class instead of instance".
     *
     * The keyword synchronized means that only one thread can access the method at a time.
     * And static synchronized mean:
     *
     * Only one thread can access the class at one time.
     */

    public static synchronized NotesDatabase getNotesDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();
}
