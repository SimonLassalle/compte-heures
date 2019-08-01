package com.mimon.compteheures.dataclasses;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    public DateTime mBegin;
    public DateTime mEnd;

    public TimeSlot(){
        this(new DateTime(), new DateTime());
    }

    public TimeSlot(int h1, int m1, int h2, int m2){
        this(new DateTime(h1, m1), new DateTime(h2, m2));
    }

    public TimeSlot(DateTime begin, DateTime end){
        mBegin = begin;
        mEnd = end;
        equalize();
    }

    public DateTime duration(){
        return DateTime.less(mEnd, mBegin);
    }

    public void setTimeSlot(int bHour, int bMinute, int eHour, int eMinute){
        mBegin.setHour(bHour);
        mBegin.setMinute(bMinute);
        mEnd.setHour(eHour);
        mEnd.setMinute(eMinute);
        equalize();
    }

    private void equalize(){
        if (duration().convertToMinute() < 0){
            mEnd.setHour(mBegin.getHour());
            mEnd.setMinute(mBegin.getMinute());
        }
    }

    @Override
    public String toString(){
        return mBegin + " - " + mEnd;
    }
}
