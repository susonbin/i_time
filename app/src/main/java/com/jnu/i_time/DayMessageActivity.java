package com.jnu.i_time;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.data.Day;

public class DayMessageActivity extends AppCompatActivity {

    private int dayID;

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_day_message);

        NavigationView edit_menu = findViewById(R.id.edit_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_mess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        dayID=getIntent().getIntExtra("dayId",0);
        Log.d("idddd：",""+dayID);

        ImageView imageView=findViewById(R.id.picture);
        imageView.setImageDrawable(MainActivity.getContext().getResources().getDrawable(MainActivity.getDays().get(dayID).getPictureId()));

        edit_menu.getMenu().findItem(R.id.day_mess).setTitle(MainActivity.getDays().get(dayID).getMessage());
        edit_menu.getMenu().findItem(R.id.day_time).setTitle(""+MainActivity.getDays().get(dayID).getInterval());//此处需要倒计时

        Switch dayAlarmSwitch = (Switch)edit_menu.getMenu().findItem(R.id.day_alarm).getActionView().findViewById(R.id.switch_);
        dayAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        Switch dayCalendarSwitch = (Switch)edit_menu.getMenu().findItem(R.id.day_calendar).getActionView().findViewById(R.id.switch_);
        dayCalendarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.getContext(),"成功在日历中显示",Toast.LENGTH_LONG).show();
                } else {
                    //Todo
                }
            }
        });
        Switch iconShortCutSwitch = (Switch)edit_menu.getMenu().findItem(R.id.day_IconShortCut).getActionView().findViewById(R.id.switch_);
        iconShortCutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("aaba3", "点击 ");
                } else {
                    //Todo
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
            Intent intent = new Intent(MainActivity.getContext(), NewOrUpdateDayActivity.class);
            intent.putExtra("datID",dayID);
            startActivityForResult(intent, MainActivity.REQUEST_CODE_NEW_DAY);
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
                            Toast.makeText(MainActivity.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MainActivity.REQUEST_CODE_NEW_DAY :{
                if(resultCode==RESULT_OK){
                    String name=data.getStringExtra("name");

                }
                break;
            }
        }
    }
}
