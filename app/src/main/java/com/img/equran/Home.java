package com.img.equran;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.android.volley.VolleyLog.TAG;


public class Home extends Fragment {
Button bt_minssub;
TextView sub_min;
View view;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            view= inflater.inflate(R.layout.fragment_home, container, false);
            FirebaseDatabase database = FirebaseDatabase.getInstance();



sub_min=view.findViewById(R.id.submins);
bt_minssub=view.findViewById(R.id.btn_mins);


            DatabaseReference myRef = database.getReference("username/jalo/remainpoints");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
sub_min.setText("Coming Data From Firebase");

bt_minssub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),getponits.class));
    }
});

return view;
        }
}
