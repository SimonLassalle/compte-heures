package com.mimon.compteheures;

import android.app.Activity;
import android.os.Bundle;

import com.mimon.compteheures.dataclasses.ActivitySheet;
import com.mimon.compteheures.dataclasses.ActivitySheetController;
import com.mimon.compteheures.dataclasses.TimeSlot;
import com.mimon.compteheures.dataclasses.TimeSlotAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetActivity extends AppCompatActivity {

    public int currentActivitySheetIndex;
    public ActivitySheetController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivitySheetIndex = getIntent().getIntExtra("currentActivitySheetIndex", 0);
        controller = ActivitySheetController.getInstance("mySave.ser", this);
        setContentView(R.layout.activity_sheet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.getActivitySheets().get(currentActivitySheetIndex).addTimeSlot(new TimeSlot());
                controller.setChanged();
                populateListView();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set name edit text
        final EditText activitySheetName = findViewById(R.id.activitySheetName);
        activitySheetName.setText(controller.getActivitySheets().get(currentActivitySheetIndex).getName());
        activitySheetName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    controller.getActivitySheets().get(currentActivitySheetIndex).setName(activitySheetName.getText().toString());
                    controller.setChanged();
                    return true;
                }
                return false;
            }
        });

        populateListView();
    }

    public void populateListView(){
        ListView myListView = (ListView) findViewById(R.id.myListView);
        ArrayList<TimeSlot> myArrayList;
        myArrayList = controller.getActivitySheets().get(currentActivitySheetIndex).getTimeSlotList();
        TimeSlotAdapter myAdapter = new TimeSlotAdapter(myArrayList, this, currentActivitySheetIndex);
        myListView.setAdapter(myAdapter);

        // update total duration value
        ActivitySheet activitySheet = controller.getActivitySheets().get(currentActivitySheetIndex);
        TextView total = findViewById(R.id.txtTotal);
        total.setText(activitySheet.totalDuration().toString());
    }

}
