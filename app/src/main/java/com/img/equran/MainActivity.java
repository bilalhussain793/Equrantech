package com.img.equran;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button bt_log,btn_google,std,teacher;
    TextView bt_signup;
    EditText et_Username,et_Password;
    Dialog d;
    String select;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_log=findViewById(R.id.login_bt);
        bt_signup=findViewById(R.id.signup_bt);
        btn_google=findViewById(R.id.sign_in_button);
        et_Username=findViewById(R.id.etUsername);
        et_Password=findViewById(R.id.etPassword);
        radioGroup=findViewById(R.id.radiogrp);

//        d=new Dialog(MainActivity.this);
//        d.setContentView(R.layout.login_select_dialog);
//        std=d.findViewById(R.id.std);
//        teacher=d.findViewById(R.id.teacher);
//        d.show();
//        d.setCancelable(false);
//        std.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                select="STD";
//                d.cancel();
//            }
//        });
//        teacher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                select="TCH";
//                d.cancel();
//            }
//        });
        bt_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
//                Toast.makeText(MainActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
                select=radioButton.getText().toString();
                if(select.equals("Student")) {
//                startActivity(new Intent(MainActivity.this,navigator.class));
                    login2(et_Username.getText().toString(), et_Password.getText().toString());
                    UserDetails.Type="Student";
                }else {
                    login(et_Username.getText().toString(), et_Password.getText().toString());
                    UserDetails.Type="Teacher";
                }
            }
        });
       // startActivity(new Intent(MainActivity.this,getponits.class));


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,Register.class));

            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void login(String user, String pass){
        user = et_Username.getText().toString();
        pass = et_Password.getText().toString();

        if(user.equals("")){
            et_Username.setError("can't be blank");
        }
        else if(pass.equals("")){
            et_Password.setError("can't be blank");
        }
        else{
            String url = "https://teacherequran.firebaseio.com/users.json";
            final ProgressDialog pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            final String finalUser = user;
            final String finalPass = pass;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    if(s.equals("null")){
                        Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                    }
                    else{
                        try {
                            JSONObject obj = new JSONObject(s);

                            if(!obj.has(finalUser)){
                                Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                               // login2(et_Username.getText().toString(), et_Password.getText().toString());

                            }
                            else if(obj.getJSONObject(finalUser).getString("Password").equals(finalPass)){
                                UserDetails.phone = finalUser;
                                UserDetails.password = finalPass;
                                UserDetails.Name=obj.getJSONObject(finalUser).getString("Name");
                                UserDetails.Email=obj.getJSONObject(finalUser).getString("Email");
                                startActivity(new Intent(MainActivity.this, navigator.class));
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                    Toast.makeText(MainActivity.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
            rQueue.add(request);
        }
    }
    public void login2(String user, String pass){
        user = et_Username.getText().toString();
        pass = et_Password.getText().toString();

        if(user.equals("")){
            et_Username.setError("can't be blank");
        }
        else if(pass.equals("")){
            et_Password.setError("can't be blank");
        }
        else{
            String url = "https://teacherequran.firebaseio.com/std.json";
            final ProgressDialog pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            final String finalUser = user;
            final String finalPass = pass;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    if(s.equals("null")){
                        Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                    }
                    else{
                        try {
                            JSONObject obj = new JSONObject(s);

                            if(!obj.has(finalUser)){
                                Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else if(obj.getJSONObject(finalUser).getString("Password").equals(finalPass)){
                                UserDetails.phone = finalUser;
                                UserDetails.password = finalPass;
                                UserDetails.Name=obj.getJSONObject(finalUser).getString("Name");
                                UserDetails.Email=obj.getJSONObject(finalUser).getString("Email");
                                startActivity(new Intent(MainActivity.this, navigator.class));
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                    Toast.makeText(MainActivity.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
            rQueue.add(request);
        }
    }

}