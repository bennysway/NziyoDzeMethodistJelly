package com.seven.clip.nziyodzemethodist;

import android.media.MediaPlayer;

import java.io.IOException;

public class playRec {
    private String AudioSavePathInDevice = "";
    private MediaPlayer mediaPlayer;

    playRec(String data){
        AudioSavePathInDevice = data;
    }

    void play(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

    }

    void stop(){

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }



}
