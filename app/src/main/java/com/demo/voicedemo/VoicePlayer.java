package com.demo.voicedemo;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by xulc on 2018/7/3.
 */

public class VoicePlayer {
    private ConcurrentLinkedQueue<VoiceUnit> voiceUnitList;
    private int index = 0;   //播放的下标位置
    private MediaPlayer mediaPlayer;
    private Context mcontext;
    private boolean isPlaying = false;

    private static VoicePlayer instance;
    public static VoicePlayer getInstance(Context context){
        if(instance==null){
            synchronized (VoicePlayer.class){
                instance = new VoicePlayer(context.getApplicationContext());
            }
        }
        return instance;
    }

    private VoicePlayer(Context context){
        voiceUnitList = new ConcurrentLinkedQueue<>();
        mcontext = context;
    }

    public void addVoiceUnit(VoiceUnit unit){
        voiceUnitList.add(unit);
    }

    public void play(){   //调动播放
        if(isPlaying){  //正在播放

        }else{
            isPlaying = true;
            playVoice();
        }
    }


    private void playVoice(){
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
        }
        VoiceUnit voiceUnit = voiceUnitList.peek();
        if(voiceUnit!=null) {     //内容不为空，播放当前信息
            if (index < voiceUnit.getVoices().length) {   //当前信息未播放完成。播放下一条
                final Uri uri = Uri.parse("android.resource://"+mcontext.getApplicationContext().getPackageName()+"/" +voiceUnitList.peek().getVoices()[index]);
                try {
                    mediaPlayer.setDataSource(mcontext, uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.d("xulc", "播放了一个音频(index = ):"+index+"   "+uri.getPath());
                            mediaPlayer.reset();
                            index++;
                            playVoice();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{  //播放下一项
                Log.d("xulvcheng","播放下一项....");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        voiceUnitList.poll();
                        index = 0;
                        playVoice();
                    }
                },2000);       //确定是否会造成  内存泄漏
                //200ms 读取下一条
            }
        }else{
            isPlaying = false;  //播放完成
            index = 0;
            Log.d("xulvcheng","播放完成的状态....");
        }
    }

    private Handler handler = new MyHnadler();



    private static class MyHnadler extends Handler {}

    public void destory(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
