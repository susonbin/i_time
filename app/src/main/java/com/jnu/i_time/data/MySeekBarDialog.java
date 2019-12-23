package com.jnu.i_time.data;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.jnu.i_time.MainActivity;
import com.jnu.i_time.R;

/*自定义一个MySeekBarDialog*/
public class MySeekBarDialog extends AlertDialog implements SeekBar.OnSeekBarChangeListener{

    private SeekBar colorSeekBar;//用于显示屏幕亮度
    private Button buttonOk;
    private Button buttonCancel;

    /*自定义构造函数用于初始化*/
    public MySeekBarDialog(Context context) {
        super(context);
        View view = getLayoutInflater().inflate(R.layout.seek_bar,null);
        colorSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        buttonOk=view.findViewById(R.id.button_ok);
        buttonCancel=view.findViewById(R.id.button_cancel);
        colorSeekBar.setMax(255);
        colorSeekBar.setProgress(0);
        setView(view);
        colorSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("pro", String.valueOf(Color.rgb(progress,progress,progress)));



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
