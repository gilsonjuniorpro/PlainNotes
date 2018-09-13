package br.plainnotes.com;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.plainnotes.com.data.AppDatabase;
import br.plainnotes.com.data.NoteDAO;
import br.plainnotes.com.model.Note;
import br.plainnotes.com.utilities.SampleData;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    public static final String TAG = "Junit";

    private AppDatabase mDB;

    private NoteDAO noteDAO;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDB = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        noteDAO = mDB.noteDAO();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb(){
        mDB.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveNotes(){
        noteDAO.insertAll(SampleData.getNotes());
        int count = noteDAO.getCount();
        Log.i(TAG, "createAndRetrieveNotes: count->"+count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings(){
        noteDAO.insertAll(SampleData.getNotes());
        Note original = SampleData.getNotes().get(0);
        Note fromDB = noteDAO.getNoteById(1);
        Log.i(TAG, "compareStrings");
        assertEquals(original.getText(), fromDB.getText());
    }

}
