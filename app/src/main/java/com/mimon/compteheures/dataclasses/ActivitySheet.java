package com.mimon.compteheures.dataclasses;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivitySheet implements Serializable {
    private String name;
    private ArrayList<TimeSlot> timeSlotList;

    public ActivitySheet(){
        this("Activity Sheet");
    }

    public ActivitySheet(String name) {
        this.name = name;
        timeSlotList = new ArrayList<TimeSlot>();
    }

    public DateTime totalDuration(){
        DateTime totalDuration = new DateTime();
        for (int i=0; i < timeSlotList.size(); i++){
            totalDuration.add(timeSlotList.get(i).duration());
        }
        return totalDuration;
    }

    public ArrayList<TimeSlot> getTimeSlotList(){ return this.timeSlotList; }

    public void addTimeSlot(TimeSlot ts){
        this.timeSlotList.add(ts);
    }

    public void removeTimeSlot(TimeSlot ts){
        this.timeSlotList.remove(ts);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String value) { this.name = value; }

    @Override
    public String toString(){
        String str = "";
        for (TimeSlot ts : timeSlotList) {
            str += ts + "\n";
        }
        return str;
    }
}
