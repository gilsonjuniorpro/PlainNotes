package br.plainnotes.com.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.plainnotes.com.model.Note;
import br.plainnotes.com.utilities.DateConverter;

@Database(entities = {Note.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "AppDatabase.db";

    private static volatile AppDatabase instance;

    private static final Object LOCK = new Object();

    public abstract NoteDAO noteDAO();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
            }
        }

        return instance;
    }
}
