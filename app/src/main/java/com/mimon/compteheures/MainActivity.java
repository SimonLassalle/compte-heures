package com.mimon.compteheures;

import android.os.Bundle;

import com.mimon.compteheures.dataclasses.ActivitySheet;
import com.mimon.compteheures.dataclasses.ActivitySheetAdapter;
import com.mimon.compteheures.dataclasses.ActivitySheetController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivitySheetController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.controller = ActivitySheetController.getInstance("mySave.ser", this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.addActivitySheet(new ActivitySheet());
                populateListView();
            }
        });

        populateListView();
    }

    public void populateListView(){
        ListView myListView = (ListView) findViewById(R.id.myListView);
        ArrayList<ActivitySheet> myArrayList;
        myArrayList = (ArrayList<ActivitySheet>) controller.getActivitySheets();
        ActivitySheetAdapter myAdapter = new ActivitySheetAdapter(myArrayList, this, controller);
        myListView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
