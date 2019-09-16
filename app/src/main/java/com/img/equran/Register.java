package com.img.equran;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity {
    Button btn_next,bt1,bt2;
    LinearLayout ln_1,ln_2,ln_3,ln_0;
    String value;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    CheckBox speak_arabic,prac_quran,learn_quran,correction_hifaz;
    //learn goals
    CheckBox self_improv,study_abroad,pass_interview,excel,Student,Teacher;
    EditText name,email,password,phone_et,refrel;
    Spinner gender,contry;
    StringBuffer st_act=new StringBuffer();
    StringBuffer st_goals=new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);

        speak_arabic = findViewById(R.id.arabic);
        prac_quran = findViewById(R.id.pra_quran);
        learn_quran = findViewById(R.id.learn_quran);
        correction_hifaz = findViewById(R.id.hifaz);
        self_improv = findViewById(R.id.cb_improve);
        study_abroad = findViewById(R.id.cb_study);
        pass_interview = findViewById(R.id.cb_pass);
        excel = findViewById(R.id.cb_Excel);
        name = findViewById(R.id.etUsername);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        phone_et = findViewById(R.id.etcountry);
        refrel = findViewById(R.id.etReferral);
        gender = findViewById(R.id.gen);
        contry = findViewById(R.id.Country);
        Student = findViewById(R.id.std);
        Teacher = findViewById(R.id.tech);

        ln_1 = findViewById(R.id.li1);
        ln_2 = findViewById(R.id.li2);
        ln_3 = findViewById(R.id.li3);
        ln_0 = findViewById(R.id.li0);

        btn_next = (Button) findViewById(R.id.btn_next);
        bt1 = findViewById(R.id.bt_next1);
        bt2 = findViewById(R.id.btn_next2);
        ln_0.setVisibility(View.VISIBLE);

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln_0.setVisibility(View.GONE);
                ln_1.setVisibility(View.VISIBLE);
                ln_2.setVisibility(View.GONE);
                ln_3.setVisibility(View.GONE);
            }
        });

        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,TeacherRegister.class));
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(speak_arabic.isChecked()||prac_quran.isChecked()||learn_quran.isChecked()||correction_hifaz.isChecked()){
                    ln_1.setVisibility(View.GONE);
                    ln_2.setVisibility(View.VISIBLE);
                    if(speak_arabic.isChecked()){
                        st_act.append(speak_arabic.getText().toString());
                    }
                    if(prac_quran.isChecked()){
                        st_act.append(prac_quran.getText().toString());
                    }
                    if(learn_quran.isChecked()){
                        st_act.append(learn_quran.getText().toString());
                    }
                    if(correction_hifaz.isChecked()){
                        st_act.append(correction_hifaz.getText().toString());
                    }
                    Toast.makeText(Register.this, ""+st_act, Toast.LENGTH_SHORT).show();
                    //activities here
                }else{
                    Toast.makeText(Register.this, "Nothing is selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(self_improv.isChecked()||study_abroad.isChecked()||pass_interview.isChecked()||excel.isChecked()){
                    ln_2.setVisibility(View.GONE);
                    ln_3.setVisibility(View.VISIBLE);
                    if(self_improv.isChecked()){
                        st_goals.append(self_improv.getText().toString());
                    }
                    if(study_abroad.isChecked()){
                        st_goals.append(study_abroad.getText().toString());
                    }
                    if(pass_interview.isChecked()){
                        st_goals.append(pass_interview.getText().toString());
                    }
                    if(excel.isChecked()){
                        st_goals.append(excel.getText().toString());
                    }
                    Toast.makeText(Register.this, ""+st_goals, Toast.LENGTH_SHORT).show();
                    //activities here
                }else{
                    Toast.makeText(Register.this, "Nothing is selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                regfun();
            }

        });

    }
    public void regfun(){

        final String phone, un, em, ps ;
                phone = phone_et.getText().toString();
                un = name.getText().toString();
                em = email.getText().toString();
                ps = password.getText().toString();
                final String rf = refrel.getText().toString();

      if(phone.equals("")){phone_et.setError("Required");}

        if(un.equals("")){name.setError("Required");}
        if(em.equals("")){email.setError("Required");}
        if(ps.equals("")){password.setError("Required");}

if(!em.contains("@")){email.setError("example@.com");}

              if (phone.length() < 11) {
                  phone_et.setError("Not Valid Phone");
              }
              if(un.length()<6){
                  name.setError("Enter Valid Name");
              }
              if(ps.length()<6){
                  password.setError("Minimum 6 Characters ");
              }


        else{



        String url = "https://teacherequran.firebaseio.com/std.json";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
                public void onResponse(String s) {
                Firebase reference = new Firebase("https://teacherequran.firebaseio.com/std");

                if(s.equals("null")) {
                    reference.child(phone).child("Password").setValue(ps);
                    Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (!obj.has(phone)) {
                            reference.child(phone).child("Password").setValue(ps);
                            reference.child(phone).child("Email").setValue(em);
                            reference.child(phone).child("Refrel").setValue(rf);
                            reference.child(phone).child("Phone").setValue(phone);
                            reference.child(phone).child("Name").setValue(un);
                        if (self_improv.isChecked()) {
                            reference.child(phone).child("Activity").setValue(self_improv.getText().toString());
                        }
                        if (study_abroad.isChecked()) {
                            reference.child(phone).child("Activity1").setValue(study_abroad.getText().toString());
                        }
                        if (pass_interview.isChecked()) {
                            reference.child(phone).child("Activity2").setValue(pass_interview.getText().toString());
                        }
                        if (excel.isChecked()) {
                            reference.child(phone).child("Activity3").setValue(excel.getText().toString());
                        }
                        if (speak_arabic.isChecked()) {
                            reference.child(phone).child("Std1").setValue(speak_arabic.getText().toString());
                        }
                        if (prac_quran.isChecked()) {
                            reference.child(phone).child("Std2").setValue(prac_quran.getText().toString());

                        }
                        if (learn_quran.isChecked()) {
                            reference.child(phone).child("Std1").setValue(learn_quran.getText().toString());
                        }
                        if (correction_hifaz.isChecked()) {
                            reference.child(phone).child("Std1").setValue(correction_hifaz.getText().toString());
                        }
                            //image will be added there
                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Register.this, "user already exists", Toast.LENGTH_LONG).show();
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //  pd.dismiss();
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Register.this);
        rQueue.add(request);
    }}

}
