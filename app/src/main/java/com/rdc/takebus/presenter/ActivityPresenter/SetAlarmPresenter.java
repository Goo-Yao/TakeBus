package com.rdc.takebus.presenter.ActivityPresenter;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.AlarmInfo;
import com.rdc.takebus.presenter.tbinterface.SetAlarmInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZH on 2016/5/24.
 */
public class SetAlarmPresenter extends BaseActivityPresenter<SetAlarmInterface> {
    private SetAlarmInterface mSetAlarmInterface;
    private RingtoneManager mRingtoneManager;
    private Context context;
    private List<AlarmInfo> lists = new ArrayList<>();
    private MediaPlayer mMediaPlayer;

    public SetAlarmPresenter(Context context, SetAlarmInterface mSetAlarmInterface) {
        this.mSetAlarmInterface = mSetAlarmInterface;
        this.context = context;
    }

    public void setBeforeData(int position) {
        if (position < lists.size()) {
            //先设置上次的值
            for (AlarmInfo alarmInfo : lists)
                alarmInfo.setChecked(false);
            lists.get(position).setChecked(true);
        }
    }

    public void getMediaData() {
        mRingtoneManager = new RingtoneManager(context);
        mRingtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = mRingtoneManager.getCursor();

        lists.add(new AlarmInfo("静音", null));
        lists.add(new AlarmInfo("默认", RingtoneManager.getActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_RINGTONE).toString()));

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                String uid = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
                Log.e("listResult", title + ">>" + uid);
                lists.add(new AlarmInfo(title, uid));
            } while (cursor.moveToNext());
        }
        mSetAlarmInterface.loadMediaData(lists);
    }

    public void playMedia(int position) {

        //先清空
        for (AlarmInfo alarmInfo : lists)
            alarmInfo.setChecked(false);
        lists.get(position).setChecked(true);
        mSetAlarmInterface.notifyData();

        //取消上次的播放
        if (mMediaPlayer != null)
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
        //获取系统的铃声uri再用MediaPlayer播放
        if (lists.get(position).getUid() != null) {
            mMediaPlayer = MediaPlayer.create(context, Uri.parse(lists.get(position).getUid()));
            mMediaPlayer.setLooping(true);
            try {
                mMediaPlayer.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        } else {
            if (mMediaPlayer != null)
                mMediaPlayer.stop();
        }
    }

    public void onDestory() {
        if (mMediaPlayer != null)
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
    }

}
