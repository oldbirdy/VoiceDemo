package com.demo.voicedemo;
import android.support.annotation.RawRes;


/**
 * xulc语音单元
 */

public class VoiceUnit {
    private int[] voices;   //采用数组 ，主要是为了拼接声音 当传数字声音的声音，可以一个一个传入


    public VoiceUnit(@RawRes int[] voices) {
        this.voices = voices;
    }

    public int[] getVoices() {
        return voices;
    }

    public void setVoices(int[] voices) {
        this.voices = voices;
    }


}
