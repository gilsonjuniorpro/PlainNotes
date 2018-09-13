package br.plainnotes.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.plainnotes.com.data.AppDatabase;
import br.plainnotes.com.data.AppRepository;
import br.plainnotes.com.model.Note;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<Note> mLiveNote =
            new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());

    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Note note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }


    public void saveNote(String noteText) {
        Note note = mLiveNote.getValue();

        if (note == null) {
            if(TextUtils.isEmpty(noteText.trim())){
                return;
            }
            note = new Note(new Date(), noteText.trim());
        }else{
            note.setText(noteText.trim());
        }

        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
