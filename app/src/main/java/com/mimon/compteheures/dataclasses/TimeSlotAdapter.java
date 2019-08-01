package com.mimon.compteheures.dataclasses;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.mimon.compteheures.MainActivity;
import com.mimon.compteheures.R;
import com.mimon.compteheures.SheetActivity;

import java.util.ArrayList;

public class TimeSlotAdapter extends ArrayAdapter<TimeSlot> {
    private ArrayList<TimeSlot> dataSet;
    Context mContext;
    int activitySheetIndex;

    private static class ViewHolder{
        EditText bHour;
        EditText bMinute;
        EditText eHour;
        EditText eMinute;
        AppCompatImageButton delete;
    }

    public TimeSlotAdapter(ArrayList<TimeSlot> data, Context context, int activitySheetIndex){
        super(context, R.layout.sheet_element, data);
        this.dataSet = data;
        this.mContext = context;
        this.activitySheetIndex = activitySheetIndex;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final TimeSlot timeSlot = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.time_slot_element, parent, false);
            viewHolder.bHour = convertView.findViewById(R.id.bHour);
            viewHolder.bMinute = convertView.findViewById(R.id.bMinute);
            viewHolder.eHour = convertView.findViewById(R.id.eHour);
            viewHolder.eMinute = convertView.findViewById(R.id.eMinute);
            viewHolder.delete = convertView.findViewById(R.id.delete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.bHour.setText(String.valueOf(timeSlot.mBegin.getHour()));

        viewHolder.bMinute.setText(String.valueOf(timeSlot.mBegin.getMinute()));

        viewHolder.eHour.setText(String.valueOf(timeSlot.mEnd.getHour()));

        viewHolder.eMinute.setText(String.valueOf(timeSlot.mEnd.getMinute()));
        viewHolder.eMinute.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    setTimeSlot(viewHolder, position);
                    return true;
                }
                System.out.println("NOT AGAIN");
                return false;
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SheetActivity) mContext).controller.getActivitySheets().get(activitySheetIndex).getTimeSlotList().remove(position);
                ((SheetActivity) mContext).controller.setChanged();
                ((SheetActivity) mContext).populateListView();
            }
        });
        return convertView;
    }

    private void setTimeSlot(ViewHolder viewHolder, int position){
        System.out.println("DONE LOL");
        ((SheetActivity)mContext).controller.getActivitySheets().get(activitySheetIndex).getTimeSlotList().get(position).setTimeSlot(
                Integer.valueOf(viewHolder.bHour.getText().toString()),
                Integer.valueOf(viewHolder.bMinute.getText().toString()),
                Integer.valueOf(viewHolder.eHour.getText().toString()),
                Integer.valueOf(viewHolder.eMinute.getText().toString())
        );
        ((SheetActivity)mContext).controller.setChanged();
        ((SheetActivity)mContext).populateListView();
    }
}
