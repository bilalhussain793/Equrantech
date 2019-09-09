package com.img.equran;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class ProfileAdapter {
    public static String names="";
    public static String email="";
    public static String pass="";
    public static String address="";
    public static String contact="";
    public static String onlinestate="";
    public static String rating="";
    public static String balance="";
    public static String ImageURL="";

    public static ArrayList<String> arrayList=new ArrayList<String>();

}
