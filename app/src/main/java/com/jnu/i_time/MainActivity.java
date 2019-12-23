package com.jnu.i_time;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.data.Day;
import com.jnu.i_time.data.DayArrayAdapter;
import com.jnu.i_time.data.MySeekBarDialog;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_NEW_DAY = 900;
    public static final int REQUEST_CODE_UPDATE_DAY = 901;

    private AppBarConfiguration mAppBarConfiguration;
    private static ArrayList<Day> days;

    private static Integer ID=0;
    private static Map<Integer,Day> idFindDay;
    private static Context context;
    private static Activity activity;

    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        days=new ArrayList<>();//初始化-->结合持久化
        idFindDay=new HashMap<Integer, Day>();

        context=this;
        activity=this;

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改成转移到新建activity,并实现activity之间的数据交流，再更新fragment
                Intent intent = new Intent(context, NewOrUpdateDayActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_NEW_DAY);
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_anniversary, R.id.nav_live,
                R.id.nav_work, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
            new MySeekBarDialog(this).show();

            //这里设置主题修改的功能
            Toast.makeText(getApplicationContext(), "修改成功" ,Toast.LENGTH_SHORT).show();
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
            case MainActivity.REQUEST_CODE_NEW_DAY :{
                if(resultCode==RESULT_OK){
                    final Handler handler=new Handler(){
                        public void handleMessage(Message msg){
                            navController.navigateUp();
                        }
                    };
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String name=data.getStringExtra("name");
                            if(name.equals(""))name="New Day";
                            String description=data.getStringExtra("description");
                            Calendar target=(Calendar) data.getSerializableExtra("newTarget");
                            String picturePath=data.getStringExtra("newPicturePath");
                            Drawable picture=new BitmapDrawable(getResizePhoto(picturePath));
                            if(data.getStringExtra("newPicturePath")==null)picture=getResources().getDrawable(R.drawable.backgroud_1);
                            int period=data.getIntExtra("newPeriod",0);
                            int type=data.getIntExtra("newType",0);
                            Day newDay=new Day(ID,type,name,description,picturePath,target,period);
                            //Log.d("dayp：",""+newDay);
                            days.add(newDay);
                            //Log.d("daym：",""+days.get(0));
                            idFindDay.put(ID++,newDay);
                            //Log.d("daya：",""+idFindDay);
                            handler.sendEmptyMessage(1);
                        }
                    }).start();
                }
                break;
            }
        }
    }
    public void load(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.sendEmptyMessage(1);
            }
        }).start();
    }
    public static Bitmap getResizePhoto(String ImagePath){
        if (ImagePath!=null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(ImagePath,options);
            double ratio = Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024);
            options.inSampleSize = (int) Math.ceil(ratio);
            options.inJustDecodeBounds= false;
            Bitmap bitmap=BitmapFactory.decodeFile(ImagePath,options);
            return bitmap;
        }
        return null;
    }
    public static ArrayList<Day> getDays(int type) {
        ArrayList<Day> typeDays = new ArrayList<>();
        for(int i=0;i<days.size();i++){
            if(days.get(i).getType()==type)typeDays.add(days.get(i));
        }
        return typeDays;
    }
    public static ArrayList<Day> getDays() {
        return days;
    }
    public static void deleteDays(int id) {
        for(int i=0;i<days.size();i++){
           // if(days.get(i).getId())==id)days.remove;
        }
    }
    public static Map<Integer,Day> getIdFindDay(){
        return idFindDay;
    }
    public static Context getContext(){ return context; }
    public static Activity getActivity(){ return activity; }
}
