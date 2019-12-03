package com.jnu.i_time;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.ui.home.DayArrayAdapter;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static ArrayList<Day> days;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        days=new ArrayList<>();//初始化-->结合持久化
        days.add(new Day(2019,12,1,new Day.Period(1,0,0)));
        days.add(new Day(2019,12,1,new Day.Period(1,0,0)));
        days.add(new Day(2019,12,1,new Day.Period(1,0,0)));
        days.add(new Day(2019,12,5,new Day.Period(1,0,0)));

        context=this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改成转移到新建activity,并实现activity之间的数据交流，再更新fragment

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_live, R.id.nav_work,
                R.id.nav_anniversary, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.theme_settings) {
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

    public static ArrayList<Day> getDays() {
        return days;
    }
    public static Context getContext(){ return context; }

}
