package com.jnu.i_time.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jnu.i_time.R;
import com.jnu.i_time.data.Day;
import com.jnu.i_time.data.ImageFilter;


import java.util.Calendar;

import static com.jnu.i_time.my_application.MyApplication.*;

public class NewOrUpdateDayActivity extends AppCompatActivity {

    private int dayID;
    private TextView name;
    private TextView description;
    private EditText nameEdit;
    private EditText descriptionEdit;
    private ImageView picture;
    private Calendar newTarget;
    private String newPicturePath;
    private int newPeriod=-1;
    private int newType=0;
    private NavigationView edit_menu;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_or_update_day);
        makeStatusBarTransparent(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }//得到图片获取权限

        Toolbar toolbar = findViewById(R.id.toolbar_new_update);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        nameEdit=findViewById(R.id.name_editText);
        descriptionEdit=findViewById(R.id.description_editText);
        picture=findViewById(R.id.new_update_picture);
        edit_menu = findViewById(R.id.edit_menu);
        newTarget=Calendar.getInstance();

        dayID=getIntent().getIntExtra("dayID",-1);

        if(dayID>=0){
            newPicturePath= getIdFindDay().get(dayID).getPicturePath();
            nameEdit.setText(getIdFindDay().get(dayID).getName());
            descriptionEdit.setText(getIdFindDay().get(dayID).getDescription());
            if(getIdFindDay().get(dayID).getPicturePath()!=null){
                Bitmap bmp=getResizePhoto(getIdFindDay().get(dayID).getPicturePath());
                @SuppressLint({"NewApi", "LocalSuppress"}) Bitmap blurBitmap = ImageFilter.blurBitmap(NewOrUpdateDayActivity.this, bmp, 20f);
                picture.setImageBitmap(blurBitmap);
            }
            else{
                Resources res = NewOrUpdateDayActivity.this.getResources();
                Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.backgroud_1);
                Bitmap blurBitmap = ImageFilter.blurBitmap(this, bmp, 20f);
                picture.setImageBitmap(blurBitmap);
            }
            newTarget=getIdFindDay().get(dayID).getTarget();
            edit_menu.getMenu().findItem(R.id.date).setTitle(String.format("%tY年%<tm月%<td日 %<tH:%<tM",getIdFindDay().get(dayID).getTarget().getTime()));
            edit_menu.getMenu().findItem(R.id.type).setTitle("Label"+getIdFindDay().get(dayID).getStringType());
            edit_menu.getMenu().findItem(R.id.repeat).setTitle("Repeat"+getIdFindDay().get(dayID).getStringPeriod());
        }


        edit_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.date:{
                        showDatePickerDialog(NewOrUpdateDayActivity.this,4, newTarget);
                        break;
                    }
                    case R.id.repeat:{
                        final String[] items = new String[]{"Year", "Month", "Week", "Day", "Custom", "None"};//创建item
                        AlertDialog repeatDialog = new AlertDialog.Builder(NewOrUpdateDayActivity.this)
                                .setTitle("Repeat")
                                .setItems(items, new DialogInterface.OnClickListener() {//添加列表
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i!=4)edit_menu.getMenu().findItem(R.id.repeat).setTitle("Repeat: "+items[i]);
                                        if(i==0){
                                            newPeriod=365;
                                        }
                                        if(i==1){
                                            newPeriod=30;
                                        }
                                        if(i==2){
                                            newPeriod=7;
                                        }
                                        if (i==3){
                                            newPeriod=1;
                                        }
                                        if(i==4){
                                            final Dialog dialog = new Dialog(NewOrUpdateDayActivity.this);
                                            // 设置它的ContentView
                                            // 得到myview才可以通过Id找到控件,实现dialog里的按钮的点击事件
                                            View editView = LayoutInflater.from(NewOrUpdateDayActivity.this).inflate(R.layout.edit_period_view, null);
                                            dialog.setContentView(editView);
                                            Button dialog_btnOk = (Button) editView.findViewById(R.id.edit_period_button_ok);
                                            Button dialog_btnCancel = (Button) editView.findViewById(R.id.edit_period_button_cancel);
                                            final EditText dialog_edtxt=  (EditText) editView.findViewById(R.id.edit_period_text);
                                            /** dialog_btn的点击事件 */
                                            dialog_btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    newPeriod=Integer.parseInt(dialog_edtxt.getEditableText().toString().trim());
                                                    edit_menu.getMenu().findItem(R.id.repeat).setTitle("Repeat: "+newPeriod+"天");
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog_btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                        if(i==5){
                                            newPeriod=0;
                                        }
                                    }
                                })
                                .create();
                        repeatDialog.show();
                        break;
                    }
                    case R.id.picture:{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);intent.setType("image/*");
                        startActivityForResult(intent,111);
                        break;
                    }
                    case R.id.type:{
                        final String[] items = new String[]{"Anniversary", "Live", "Work", "None"};//创建item
                        AlertDialog typeDialog = new AlertDialog.Builder(NewOrUpdateDayActivity.this)
                                .setTitle("Label")
                                .setItems(items, new DialogInterface.OnClickListener() {//添加列表
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        edit_menu.getMenu().findItem(R.id.type).setTitle("Label: "+items[i]);
                                        if(i==0){
                                            newType=Day.Anniversary;
                                        }
                                        if(i==1){
                                            newType=Day.Live;
                                        }
                                        if(i==2){
                                            newType=Day.Work;
                                        }
                                        if(i==3){
                                            newType=Day.TypeDefault;
                                        }
                                    }
                                })
                                .create();
                        typeDialog.show();
                        break;
                    }
                }

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_update_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        if(itemId==R.id.action_check){
            Intent intent=new Intent();
            intent.putExtra("name",nameEdit.getText().toString());
            intent.putExtra("description",descriptionEdit.getText().toString());
            intent.putExtra("newTarget",newTarget);
            intent.putExtra("newPicturePath",newPicturePath);
            intent.putExtra("newPeriod",newPeriod);
            intent.putExtra("newType",newType);
            setResult(RESULT_OK,intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param calendar
     */
    public void showDatePickerDialog(Activity activity, int themeResId, Calendar calendar) {
        //Log.d("dattt2","hahaha");
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId,
                new DatePickerDialog.OnDateSetListener() {
                // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newTarget.set(year,monthOfYear,dayOfMonth);
                        showTimePickerDialog(NewOrUpdateDayActivity.this,4, newTarget);
                    }
                }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param calendar
     */
    public void showTimePickerDialog(Activity activity, int themeResId, final Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        newTarget.set(newTarget.get(Calendar.YEAR),newTarget.get(Calendar.MONTH)
                                , newTarget.get(Calendar.DAY_OF_MONTH),hourOfDay,minute,0);
                        edit_menu.getMenu().findItem(R.id.date).setTitle(String.format("%tY年%<tm月%<td日 %<tH:%<tM",newTarget.getTime()));
                    }
                }
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplication(), "点击取消从相册选择", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Uri uri = data.getData();
                    Log.e("TAG", uri.toString());
                    String filePath = getRealPathFromURI(this,uri);
                    newPicturePath = filePath;//此处获取！！！
                    Bitmap bmp=getResizePhoto(newPicturePath);
                    @SuppressLint({"NewApi", "LocalSuppress"}) Bitmap blurBitmap = ImageFilter.blurBitmap(NewOrUpdateDayActivity.this, bmp, 20f);
                    picture.setImageBitmap(blurBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri){
        Cursor cursor = null;
        try{
            String [] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri,proj,null,null,null);
            cursor.moveToFirst();
            int column_indenx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_indenx);
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}


