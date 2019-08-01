package com.mimon.compteheures.dataclasses;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ActivitySheetController {
    private String filename;
    private Context context;
    private List<ActivitySheet> activitySheets = new ArrayList<>();

    private static ActivitySheetController instance;

    public static ActivitySheetController getInstance(String filename, Context context){
        if (instance == null){
            instance = new ActivitySheetController(filename, context);
        }
        else{
            instance.filename = filename;
            instance.context = context;
        }
        return instance;
    }

    private ActivitySheetController(String filename, Context context){
        this.filename = filename;
        this.context = context;
        load();
    }

    public List<ActivitySheet> getActivitySheets(){
        load();
        return this.activitySheets;
    }

    public void addActivitySheet(ActivitySheet sheet){
        this.activitySheets.add(sheet);
        save();
    }

    public void removeActivitySheet(ActivitySheet sheet){
        this.activitySheets.remove(sheet);
        save();
    }

    public void setChanged(){
        save();
    }

    private void save(){
        try {
            System.out.println("SHEETS : \n");
            for (ActivitySheet sheet : this.activitySheets) {
                System.out.println("sheet : " + sheet);
            }
            FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.activitySheets);

            out.close();
            file.close();
            System.out.println("save completed");
        }
        catch (Exception e){
            System.out.println("Error in save");
        }
    }

    private void load(){
        try {
            // Reading the object from a file
            FileInputStream file = context.openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            this.activitySheets = (List<ActivitySheet>) in.readObject();

            in.close();
            file.close();
            System.out.println("load completed");
        }
        catch (Exception e){
            System.out.println("Error in load");
        }
    }
}
