package com.img.equran;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.teacher_list_layout, null,true);

        final TextView titleText = (TextView) rowView.findViewById(R.id.dec);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imv);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+maintitle.get(position));

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.child("Name").getValue(String.class);
               // Log.d(TAG, "Value is: " + value);
                titleText.setText(value);
                Picasso.with(context).load("https://firebasestorage.googleapis.com" +
                        "/v0/b/" +
                        "teacherequran.appspot.com/o/img%2F"
                        +maintitle.get(position)+
                        "?alt=media&token=53e6c894-27a6-4318-8288-d603a039124e")
                        .into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        String url = "https://teacherequran.firebaseio.com/users/"+maintitle.get(position)+".json";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s) {
////                doOnSuccess(s);
//                titleText.setText(s);
//
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("" + volleyError);
//            }
//        });
//        RequestQueue rQueue = Volley.newRequestQueue(context);
//        rQueue.add(request);

        // titleText.setText(maintitle.get(position));


        return rowView;

    }
}