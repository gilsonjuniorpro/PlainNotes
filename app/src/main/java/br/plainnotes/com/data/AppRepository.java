package br.plainnotes.com.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.plainnotes.com.model.Note;
import br.plainnotes.com.utilities.SampleData;

public class AppRepository {

    public static AppRepository ourInstance;

    public LiveData<List<Note>> mNotes;
    private AppDatabase mDB;

    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context){
        mDB = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDAO().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<Note>> getAllNotes(){
        return mDB.noteDAO().getAll();
    }


    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDAO().deleteAll();
            }
        });
    }

    public Note getNoteById(int noteId) {
        return mDB.noteDAO().getNoteById(noteId);
    }

    public void insertNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDAO().insertNote(note);
            }
        });
    }

    public void deleteNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDAO().deleteNote(note);
            }
        });
    }
}
