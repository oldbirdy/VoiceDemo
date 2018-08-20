package com.demo.voicedemo;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{
    private MyTTS myTTS;
    private VoicePlayer voicePlayer;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myTTS = MyTTS.getInstance(this);
    }

    @OnClick(R.id.bofang1)
    void bofang1(){
        if(myTTS.isSupportCN()){
            myTTS.getTts().speak("一条测试语音消息",TextToSpeech.QUEUE_ADD,null,null);
        }
    }

    @OnClick(R.id.bofang2)
    void bofang2(){
        voicePlayer = VoicePlayer.getInstance(this);
        voicePlayer.addVoiceUnit(new VoiceUnit(new int[]{R.raw.test}));
        voicePlayer.play();
    }

    @Override
    protected void onDestroy() {
        myTTS.getTts().stop();
        voicePlayer.destory();
        super.onDestroy();
    }
}
