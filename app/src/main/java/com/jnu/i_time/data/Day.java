package com.jnu.i_time.data;

import java.util.Calendar;
import java.util.Date;

public class Day {
    //一个目标日
    //周期
    //当现在日期超过目标日变为已经
    //当周期结束此时按照周期改变目标日，再判断
    public static final int Anniversary=1;
    public static final int live=2;
    public static final int work=3;

    private String message;
    private int type;
    private int pictureId;
    private int year;
    private int month;
    private int day;

    public static class Period {
        int year;
        int month;
        int day;

        public Period(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }
    private Period period;
    private long interval;

    public Day(int type,String message,int pictureId,int year, int month, int day, Period period) {
        this.type = type;
        this.message=message;
        this.pictureId=pictureId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public int getPictureId() {
        return pictureId;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public void changeTargetDay(){
        if(interval==0){
            year+= period.year;
            month+= period.month;
            day+= period.day;
        }
    }

    public void setInterval(){

    }
    public long getInterval(){
        Calendar calendar= Calendar.getInstance();

        calendar.setTime(new Date());
        long nTime=calendar.getTimeInMillis();

        calendar.set(year,month-1,day);
        long time=calendar.getTimeInMillis();

        long subDay=(time-nTime)/(1000*60*60*24);
        interval=subDay;
        return interval;
    }
}
