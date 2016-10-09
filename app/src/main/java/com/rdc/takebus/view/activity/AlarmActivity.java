package com.rdc.takebus.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.rdc.takebus.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 闹钟提醒界面
 * Created by ZZH on 2016/5/23.
 */
public class AlarmActivity extends Activity {
    private TextView tvTime, tvDate, tvContent;
    private String stationName;
    private Vibrator vibrator;
    private MediaPlayer mMediaPlayer;
    private RingtoneManager mRingtoneManager;
    private SharedPreferences mSharedPreferences;
    private String uri;
    private String shock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        stationName = getIntent().getStringExtra("stationName");
        setContentView(R.layout.activity_alarm);
        initView();
        setData();
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvContent = (TextView) findViewById(R.id.tv_content);
        mSharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        //默認鈴聲
        uri = mSharedPreferences.getString("uri", RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_RINGTONE).toString());
        shock = mSharedPreferences.getString("shock", "开启");
        Log.e("alarmAcitivtyy", uri + ">>" + shock);
    }

    private void setData() {
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/PoiretOne-Regular.ttf");
        tvTime.setTypeface(tf);
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat("M月dd日 E");
        Date date = new Date();
        tvTime.setText(formatTime.format(date));
        tvDate.setText(formatDate.format(date));
        tvContent.setText("已到达" + stationName);
        if (shock.equals("开启"))
            //开启震动
            setShockAlarm();
        //播放铃声
        if (uri != null)
            playRing(uri);
    }

    private void playRing(String uri) {
        //获取系统的铃声uri再用MediaPlayer播放
        mMediaPlayer = MediaPlayer.create(this, Uri.parse(uri));
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (vibrator != null)
                cancleShockAlarm();
            if (mMediaPlayer != null)
                mMediaPlayer.stop();
            finish();
        }
        return super.onTouchEvent(event);
    }


    //开启震动
    private void setShockAlarm() {
        //获得一个震动的服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Log.e("vibrator", "vibrator");
        if (vibrator.hasVibrator()) {
            long[] pattern = {1000, 2000, 1000, 2000}; //隔1秒震动2次
            vibrator.vibrate(pattern, 1); // -1不重复，1重复
        }
    }

    private void cancleShockAlarm() {
        vibrator.cancel();
    }


}
