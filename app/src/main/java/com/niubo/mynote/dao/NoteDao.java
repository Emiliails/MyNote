package com.niubo.mynote.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.niubo.mynote.entities.Note;

import java.util.List;

/**
 * Marks the class as a Data Access Object.
 * Data Access Objects are the main classes where you define your database interactions.
 * They can include a variety of query methods.
 */

@Dao
public interface NoteDao {


    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    /**
     * What to do if a conflict happens.
     * Use OnConflictStrategy.ABORT (default) to roll back the transaction on conflict.
     * Use OnConflictStrategy.REPLACE to replace the existing rows with the new rows.
     * Use OnConflictStrategy.IGNORE to keep the existing rows.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
