package com.jnu.i_time.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Day implements Serializable{
    //一个目标日
    //周期
    //当现在日期超过目标日变为已经
    //当周期结束此时按照周期改变目标日，再判断
    public static final int TypeDefault=0;
    public static final int Anniversary=1;
    public static final int Live=2;
    public static final int Work=3;

    private String name;
    private int type;
    private Drawable picture;
    private Calendar target;
    private boolean alarm;
    private String description;
    private int period;

    public Day(int type, String name, Drawable picture, Calendar target,int period) {
        this.type = type;
        this.name = name;
        this.picture=picture;
        this.target=target;
        this.period=period;
        this.alarm=false;//默认为不提醒

    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public Calendar getTarget() {
        return target;
    }
    public void setTarget(Calendar target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Drawable getPicture() {
        return picture;
    }
    public void setPicture(Drawable picture){
        this.picture=picture;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void changeTargetDay(){
        if(getSub().getTimeInMillis()==0){
            Calendar calendar= Calendar.getInstance();

            calendar.setTime(new Date((long)(period*(1000*60*60*24))));
            long pTime=calendar.getTimeInMillis();

            long time=target.getTimeInMillis()+pTime;
            target.setTime(new Date(time));
        }
    }

    public Calendar getSub(){
        Calendar calendar= Calendar.getInstance();

        calendar.setTime(new Date());
        long nTime=calendar.getTimeInMillis();

        long time=target.getTimeInMillis();

        long sub=time-nTime;
        calendar.setTime(new Date(sub));
        return calendar;
    }
    public String getStringType(){
        if(type==0)return "type";
        if(type==1)return "Anniversary";
        if(type==2)return "live";
        if(type==3)return "work";
        return "";
    }
}
