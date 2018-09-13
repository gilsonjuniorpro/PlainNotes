package br.plainnotes.com.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.plainnotes.com.model.Note;
import br.plainnotes.com.utilities.Constants;

@Dao
public interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE id = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.COL_DATE + " DESC")
    LiveData<List<Note>> getAll();

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    int deleteAll();

    @Query("SELECT COUNT(*) FROM " + Constants.TABLE_NAME)
    int getCount();

}
