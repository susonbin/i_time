package com.jnu.i_time.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Day implements Serializable{
    //一个目标日
    //周期
    //当现在日期超过目标日变为已经
    //当周期结束此时按照周期改变目标日，再判断
    public static final int TypeDefault=0;
    public static final int Anniversary=1;
    public static final int Live=2;
    public static final int Work=3;

    private int id;
    private String name;
    private int type;
    private String picturePath;
    private Calendar target;
    private boolean alarm;
    private String description;
    private int period=-1;
    private boolean dayFinal=false;

    public Day(int id,int type, String name, String description,String picturePath ,Calendar target, int period) {
        this.id=id;
        this.type = type;
        this.name = name;
        this.description=description;
        this.picturePath=picturePath;
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

    public boolean isDayFinal() {
        return dayFinal;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPicturePath() {
        return picturePath;
    }
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void changeTargetDay(){
        if(isDayFinal()&&period>0){
            Calendar calendar= Calendar.getInstance();

            calendar.setTime(new Date((long)(period*(1000*60*60*24))));
            long pTime=calendar.getTimeInMillis();

            long time=target.getTimeInMillis()+pTime;
            target.setTime(new Date(time));
            dayFinal=false;
        }
    }

    public Calendar getSub(){
        Calendar calendar= Calendar.getInstance();

        calendar.setTime(new Date());
        long nTime=calendar.getTimeInMillis();

        long time=target.getTimeInMillis();

        if(time-nTime<0)dayFinal=true;
        else dayFinal=false;

        long sub=Math.abs(time-nTime);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));//此处为格林威治时区，方便后面与1970-1-1 0：0：0相减

        calendar.setTime(new Date(sub));
        return calendar;
    }
    public String getStringType(){
        if(type==0)return "";
        if(type==1)return ": Anniversary";
        if(type==2)return ": live";
        if(type==3)return ": work";
        return "";
    }
    public String getStringPeriod(){
        if(period==365){
            return ": Year";
        }
        if(period==30){
            return ": Month";
        }
        if(period==7){
            return ": Week";
        }
        if(period==1){
            return ": day";
        }
        if(period==0){
            return ": None";
        }
        if(period==-1){
            return "";
        }
        else{
            return ": "+period+"天";
        }
    }
}
