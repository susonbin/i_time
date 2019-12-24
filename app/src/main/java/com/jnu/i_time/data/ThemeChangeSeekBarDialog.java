package com.jnu.i_time.data;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jnu.i_time.R;

/*自定义一个MySeekBarDialog*/
public class ThemeChangeSeekBarDialog extends AlertDialog implements SeekBar.OnSeekBarChangeListener{

    private SeekBar colorSeekBar;//用于显示颜色
    private Button buttonOk;
    private Button buttonCancel;
    private int Color;

    /*自定义构造函数用于初始化*/
    public ThemeChangeSeekBarDialog(final Context context) {
        super(context);
        View view = getLayoutInflater().inflate(R.layout.seek_bar,null);
        colorSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        buttonOk=view.findViewById(R.id.button_ok);
        buttonCancel=view.findViewById(R.id.button_cancel);
        colorSeekBar.setMax(255);
        colorSeekBar.setProgress(0);
        setView(view);
        colorSeekBar.setOnSeekBarChangeListener(this);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Theme=Color;改变颜色
                ThemeChangeSeekBarDialog.this.dismiss();
                Toast.makeText(context, "修改成功" ,Toast.LENGTH_SHORT).show();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeChangeSeekBarDialog.this.dismiss();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //Color=progress通过拖条改变Color值;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
