package com.jnu.i_time.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.R;
import com.jnu.i_time.data.Day;
import com.jnu.i_time.data.DataSaver;
import com.jnu.i_time.data.ThemeChangeSeekBarDialog;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.jnu.i_time.my_application.MyApplication.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDataSaver().save_days();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        makeStatusBarTransparent(this);

        setActivity(this);
        setDays(getDataSaver().load_days());

        for(int i=0;i<getDays().size();i++){
            setID(Math.max(getID(),getDays().get(i).getId())+1);
            getIdFindDay().put(getDays().get(i).getId(),getDays().get(i));
        }

        //Log.d("初始化",""+days);
        //Log.d("初始化",""+idFindDay);
        //Log.d("fisID:",""+ID);

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改成转移到新建activity,并实现activity之间的数据交流，再更新fragment
                Intent intent = new Intent(MainActivity.this, NewOrUpdateDayActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_DAY);
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_anniversary, R.id.nav_live,
                R.id.nav_work, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        setNavController(Navigation.findNavController(this, R.id.nav_host_fragment));

        NavigationUI.setupActionBarWithNavController(this, getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, getNavController());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.theme_settings) {
            new ThemeChangeSeekBarDialog(this).show();//这里设置主题修改的功能
            return true;
        }
        if (id == R.id.language_settings) {
            //这里设置语言修改的功能
            Toast.makeText(this, "修改成功" ,Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_NEW_DAY :{
                if(resultCode==RESULT_OK){
                    String name=data.getStringExtra("name");
                    if(name.equals(""))name="New Day";
                    String description=data.getStringExtra("description");
                    Calendar target=(Calendar) data.getSerializableExtra("newTarget");
                    String picturePath=data.getStringExtra("newPicturePath");
                    int period=data.getIntExtra("newPeriod",0);
                    int type=data.getIntExtra("newType",0);
                    Day newDay=new Day(getID(),type,name,description,picturePath,target,period);
                    //Log.d("dayp：",""+newDay);
                    getDays().add(newDay);
                    //Log.d("daym：",""+days.get(0));
                    getIdFindDay().put(getID(),newDay);
                    setID(getID()+1);
                    //Log.d("daya：",""+idFindDay);
                    getNavController().navigate(R.id.nav_home);
                }
                break;
            }
        }
    }
}
