package com.mimon.compteheures.dataclasses;

import java.io.Serializable;

public class DateTime implements Serializable {
    private int mHour;
    private int mMinute;

    public DateTime(){
        this(0, 0);
    }

    public DateTime(int hour, int minute){
        mHour = hour;
        mMinute = minute;
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public int getHour(){
        return mHour;
    }

    public void setHour(int hour){
        mHour = clamp(hour, 0, 23);
    }

    public int getMinute(){
        return mMinute;
    }

    public void setMinute(int minute){
        mMinute = clamp(minute, 0, 59);
    }

    public int convertToMinute(){
        return mHour * 60 + mMinute;
    }

    public void add(DateTime other){
        if (other == null) return;
        DateTime sum = plus(this, other);
        this.mHour = sum.mHour;
        this.mMinute = sum.mMinute;
    }

    public void subtract(DateTime other){
        if (other == null) return;
        DateTime diff = plus(this, other);
        this.mHour = diff.mHour;
        this.mMinute = diff.mMinute;
    }

    @Override
    public String toString(){
        String hour = mHour < 10 ? "0" + mHour : "" + mHour;
        String minute = mMinute < 10 ? "0" + mMinute : "" + mMinute;
        return hour + ":" + minute;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof  DateTime)) return false;
        DateTime d = (DateTime) o;
        return mHour == d.mHour && mMinute == d.mMinute;
    }

    public static DateTime convertFromMinute(int minute){
        return new DateTime(minute / 60, minute % 60);
    }

    public static DateTime plus(DateTime a, DateTime b){
        if (a == null || b == null) return null;
        int minutes = a.convertToMinute() + b.convertToMinute();
        return convertFromMinute(minutes);
    }

    public static DateTime less(DateTime a, DateTime b){
        if (a == null || b == null) return null;
        int minutes = a.convertToMinute() - b.convertToMinute();
        return convertFromMinute(minutes);
    }
}
