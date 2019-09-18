package com.img.equran;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Button calling;
    TextView tv;
    String Buttonpop;
    Firebase reference1, reference2;
    Dialog d;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users/"+ProfileAdapter.contact);
    final int random = new Random().nextInt(2234326) + 12345635;
    final String ca="https://appr.tc/r/"+random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        d=new Dialog(ChatActivity.this);
        d.setContentView(R.layout.custom_diag);


        tv=findViewById(R.id.user_name);
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        calling=findViewById(R.id.call_bt);
        int r;
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        for(r=0;r<10; r++){

            calling.startAnimation(myAnim);

        }
        Firebase.setAndroidContext(this);


        if(UserDetails.Type.equals("Teacher"))
        {

            final TextView text = (TextView) d.findViewById(R.id.text);

            DatabaseReference get = database.getReference("std/" + UserDetails.chatWith);
            get.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    String nemeofhero = dataSnapshot.child("Name").getValue(String.class);
                    text.setText("Calling From " + nemeofhero);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }


        Button dialogButton = (Button) d.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call();

            }
        });

        if(UserDetails.Type.equals("Teacher"))
        {DatabaseReference get = database.getReference("users/" + UserDetails.phone);
            get.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    String number2 = dataSnapshot.child("call").getValue(String.class);
                    if(number2.length()>7)
                    {
                        calling.setVisibility(View.VISIBLE);
                        d.show();
                    }
                    else {
                        calling.setVisibility(View.GONE);
                        d.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

        }





        if(UserDetails.Type.equals("Teacher"))
        {DatabaseReference get = database.getReference("users/" + UserDetails.phone);
            get.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Buttonpop= dataSnapshot.child("call").getValue(String.class);

                    if(Buttonpop.length()>5){calling.setVisibility(View.VISIBLE);}
                    else{calling.setVisibility(View.GONE);}

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }

        if(UserDetails.Type.equals("Student")) {

            DatabaseReference myRef44 = database.getReference("users/" + ProfileAdapter.contact);
            myRef44.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String namm = dataSnapshot.child("Name").getValue(String.class);

                    tv.setText(namm);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });



        }
        if(UserDetails.Type.equals("Teacher")) {

            tv.setText(UserDetails.chatWith);
        }
        reference1 = new Firebase("https://teacherequran.firebaseio.com/messages/" + UserDetails.phone + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://teacherequran.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.phone);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.phone);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });



        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        Call();
        if(UserDetails.Type.contains("Teacher")){

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Clear11();
                }
            }, 5000);
        }

            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.phone)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void Call()
    {

        if(UserDetails.Type.equals("Student")) {
            DatabaseReference myRef = database.getReference("users/" + ProfileAdapter.contact);

            myRef.child("call").setValue(ca);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ca));
            startActivity(intent);}



        if(UserDetails.Type.equals("Teacher"))
        {DatabaseReference get = database.getReference("users/" + UserDetails.phone);
            get.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String loginid = dataSnapshot.child("call").getValue(String.class);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginid));
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
    }

    private void Clear11() {


        if(UserDetails.Type.equals("Teacher")) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef2 = database.getReference("users/" + UserDetails.phone);

            myRef2.child("call").setValue("2");

        }
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1){
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{

            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);

        }

        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);

    }
}