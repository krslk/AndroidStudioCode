package com.example.musicplayer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.musicplayer.entity.Music;

public class MusicViewModel extends ViewModel {
    private final MutableLiveData<Music> selectedMusic = new MutableLiveData<>();

    public void selectMusic(Music music) {
        selectedMusic.setValue(music);
    }

    public LiveData<Music> getSelectedMusic() {
        return selectedMusic;
    }
}
