package com.img.equran;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;



public class MessageFragment extends Fragment {
    ListView lv;
    ArrayList<String> arr=new ArrayList<>();
    int totalUsers = 0;
    public static TeacherAdapter teacheradapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        lv=view.findViewById(R.id.listview);
        String url = "https://teacherequran.firebaseio.com/messages" +
                ".json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProfileAdapter.contact=arr.get(position);
                startActivity(new Intent(getActivity(),TeacherProfile.class));
                Toast.makeText(getActivity(), ""+arr.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.phone)) {
                    if(key.contains(UserDetails.phone+"_")){
                        String a=key.replace(UserDetails.phone+"_","");
                        arr.add(a);
                    }

                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            // usersList.setVisibility(View.GONE);
        }
        else{
            //usersList.setVisibility(View.VISIBLE);
            teacheradapter=new TeacherAdapter(getActivity(),arr);
            lv.setAdapter(new ChatAdapter(getActivity(),arr));
        }
    }
}