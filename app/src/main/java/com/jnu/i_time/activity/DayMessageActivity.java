package com.jnu.i_time.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.R;
import com.jnu.i_time.my_application.MyApplication;

import java.util.Calendar;

import static com.jnu.i_time.my_application.MyApplication.REQUEST_CODE_NEW_DAY;
import static com.jnu.i_time.my_application.MyApplication.getDays;
import static com.jnu.i_time.my_application.MyApplication.getIdFindDay;
import static com.jnu.i_time.my_application.MyApplication.getResizePhoto;
import static com.jnu.i_time.my_application.MyApplication.makeStatusBarTransparent;

public class DayMessageActivity extends AppCompatActivity {

    private int dayID;
    private NavigationView mess_menu ;
    private ImageView backgroundPicture;

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_day_message);
        makeStatusBarTransparent(this);

        mess_menu = findViewById(R.id.mess_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_mess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        dayID=getIntent().getIntExtra("dayId",0);

        backgroundPicture=findViewById(R.id.day_mess_picture);
        if(getIdFindDay().get(dayID).getPicturePath()!=null){
            Bitmap bmp=getResizePhoto(getIdFindDay().get(dayID).getPicturePath());
            backgroundPicture.setImageBitmap(bmp);
        }
        else{
            Resources res = DayMessageActivity.this.getResources();
            Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.backgroud_1);
            backgroundPicture.setImageBitmap(bmp);
        }

        mess_menu.getMenu().findItem(R.id.day_name).setTitle(getIdFindDay().get(dayID).getName());

        final Handler handler=new Handler(){
            public void handleMessage(Message msg){
                Calendar sub=Calendar.getInstance();
                if(getIdFindDay().get(dayID)!=null){
                    sub=getIdFindDay().get(dayID).getSub();
                    getIdFindDay().get(dayID).changeTargetDay();
                    String subString=""
                            +(sub.get(Calendar.YEAR)-1970)+"年"
                            +(sub.get(Calendar.MONTH))+"月"
                            +(sub.get(Calendar.DAY_OF_MONTH)-1)+"日"
                            +(sub.get(Calendar.HOUR_OF_DAY))+":"
                            +(sub.get(Calendar.MINUTE))+":"
                            +(sub.get(Calendar.SECOND));
                    if(getIdFindDay().get(dayID).isDayFinal()){
                        mess_menu.getMenu().findItem(R.id.day_time).setTitle("已经"+subString);
                    }
                    else{
                        mess_menu.getMenu().findItem(R.id.day_time).setTitle(subString);
                    }
                    super.handleMessage (msg);
                }
                else finish();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        handler.sendEmptyMessage(1);
                        Thread.sleep(1000);
                    }

                }catch (InterruptedException e){

                }
            }
        }).start();

        Switch dayAlarmSwitch = (Switch)mess_menu.getMenu().findItem(R.id.day_alarm).getActionView().findViewById(R.id.switch_);
        if(getIdFindDay().get(dayID).isAlarm())dayAlarmSwitch.setChecked(true);
        dayAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(DayMessageActivity.this,"成功加入闹钟",Toast.LENGTH_LONG).show();
                    getIdFindDay().get(dayID).setAlarm(true);
                } else {
                    Toast.makeText(DayMessageActivity.this,"取消闹钟提醒",Toast.LENGTH_LONG).show();
                    getIdFindDay().get(dayID).setAlarm(false);
                }
            }
        });
        Switch dayCalendarSwitch = (Switch)mess_menu.getMenu().findItem(R.id.day_calendar).getActionView().findViewById(R.id.switch_);
        dayCalendarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(DayMessageActivity.this,"成功在日历中显示",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DayMessageActivity.this,"已取消在日历中显示",Toast.LENGTH_LONG).show();
                }
            }
        });
        Switch iconShortCutSwitch = (Switch)mess_menu.getMenu().findItem(R.id.day_IconShortCut).getActionView().findViewById(R.id.switch_);
        iconShortCutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.day_mess_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        if(itemId==R.id.action_edit){
            Intent intent = new Intent(DayMessageActivity.this, NewOrUpdateDayActivity.class);
            intent.putExtra("dayID",dayID);
            startActivityForResult(intent, REQUEST_CODE_NEW_DAY);
        }
        if(itemId==R.id.action_delete){
            new android.app.AlertDialog.Builder(this)
                    .setTitle("询问")
                    .setMessage("删除吗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent();
                            Boolean isDele=true;
                            intent.putExtra("delete",isDele);
                            intent.putExtra("id",dayID);
                            Toast.makeText(MyApplication.getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_NEW_DAY :{
                if(resultCode==RESULT_OK){
                    //Log.d("sus","ppp");
                    getIdFindDay().get(dayID).setName(data.getStringExtra("name"));
                    getIdFindDay().get(dayID).setTarget((Calendar) data.getSerializableExtra("newTarget"));
                    getIdFindDay().get(dayID).setPeriod(data.getIntExtra("newPeriod",0));
                    String picturePath=data.getStringExtra("newPicturePath");
                    getIdFindDay().get(dayID).setPicturePath(picturePath);
                    getIdFindDay().get(dayID).setType(data.getIntExtra("newType",0));
                    mess_menu.getMenu().findItem(R.id.day_name).setTitle(getIdFindDay().get(dayID).getName());
                    if(getIdFindDay().get(dayID).getPicturePath()!=null){
                        Bitmap bmp=getResizePhoto(getIdFindDay().get(dayID).getPicturePath());
                        backgroundPicture.setImageBitmap(bmp);
                    }
                    else{
                        Resources res = DayMessageActivity.this.getResources();
                        Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.backgroud_1);
                        backgroundPicture.setImageBitmap(bmp);
                    }
                }
                break;
            }
        }
    }
}
