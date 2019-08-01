package com.mimon.compteheures.dataclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.snackbar.Snackbar;
import com.mimon.compteheures.MainActivity;
import com.mimon.compteheures.R;
import com.mimon.compteheures.SheetActivity;

import java.util.ArrayList;

public class ActivitySheetAdapter extends ArrayAdapter<ActivitySheet> {
    private ArrayList<ActivitySheet> dataSet;
    Context mContext;
    ActivitySheetController controller;

    private static class ViewHolder{
        TextView txtName;
        TextView txtDuration;
        AppCompatImageView btnDelete;
    }

    public ActivitySheetAdapter(ArrayList<ActivitySheet> data, Context context, ActivitySheetController controller){
        super(context, R.layout.sheet_element, data);
        this.dataSet = data;
        this.mContext = context;
        this.controller = controller;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ActivitySheet activitySheet = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.sheet_element, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtDuration = convertView.findViewById(R.id.duration);
            viewHolder.btnDelete = convertView.findViewById(R.id.delete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(activitySheet.getName());
        viewHolder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, activitySheet.getName() + " clicked", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, SheetActivity.class);
                intent.putExtra("currentActivitySheetIndex", position);
                mContext.startActivity(intent);
            }
        });
        viewHolder.txtDuration.setText(activitySheet.totalDuration().toString());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, R.string.confirm_deletion, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        controller.removeActivitySheet(activitySheet);
                        ((MainActivity) mContext).populateListView();
                    }
                });
                snackbar.show();
            }
        });
        return convertView;
    }
}
