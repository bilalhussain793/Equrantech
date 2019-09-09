package com.img.equran;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class TeacherAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> maintitle;


    public TeacherAdapter(Activity context, ArrayList<String> maintitle) {
        super(context, R.layout.teacher_list_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.teacher_list_layout, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.dec);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imv);

        titleText.setText(maintitle.get(position));
        Picasso.with(context).load("https://firebasestorage.googleapis.com" +
                "/v0/b/" +
                "teacherequran.appspot.com/o/img%2F"
                +maintitle.get(position)+
                "?alt=media&token=53e6c894-27a6-4318-8288-d603a039124e")
                .into(imageView);

        return rowView;

    }
}