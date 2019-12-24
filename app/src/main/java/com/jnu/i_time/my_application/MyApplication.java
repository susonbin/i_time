package com.jnu.i_time.my_application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.navigation.NavController;

import com.jnu.i_time.data.DataSaver;
import com.jnu.i_time.data.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    public static final int REQUEST_CODE_NEW_DAY = 900;
    public static final int REQUEST_CODE_UPDATE_DAY = 901;

    private static ArrayList<Day> days;

    private static Integer ID=0;
    private static Map<Integer,Day> idFindDay;
    private static Context context;
    private static Activity activity;
    private static DataSaver dataSaver;

    private static NavController navController;

    public void onCreate() {
        super.onCreate();
        context=this;
        days=new ArrayList<>();//初始化-->结合持久化
        idFindDay=new HashMap<Integer, Day>();
        dataSaver =new DataSaver(this);
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

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static ArrayList<Day> getDays() {
        return days;
    }

    public static void setDays(ArrayList<Day> days) {
        MyApplication.days = days;
    }

    public static Integer getID() {
        return ID;
    }

    public static void setID(Integer ID) {
        MyApplication.ID = ID;
    }

    public static Map<Integer, Day> getIdFindDay() {
        return idFindDay;
    }

    public static void setIdFindDay(Map<Integer, Day> idFindDay) {
        MyApplication.idFindDay = idFindDay;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        MyApplication.activity = activity;
    }

    public static DataSaver getDataSaver() {
        return dataSaver;
    }

    public static void setDataSaver(DataSaver dataSaver) {
        MyApplication.dataSaver = dataSaver;
    }

    public static NavController getNavController() {
        return navController;
    }

    public static void setNavController(NavController navController) {
        MyApplication.navController = navController;
    }

    public static ArrayList<Day> getDays(int type) {
        ArrayList<Day> typeDays = new ArrayList<>();
        for(int i=0;i<days.size();i++){
            if(days.get(i).getType()==type)typeDays.add(days.get(i));
        }
        return typeDays;
    }
}
