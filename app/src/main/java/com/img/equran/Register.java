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

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    Button btn_next,bt1,bt2;
    LinearLayout ln_1,ln_2,ln_3,ln_0;
    String value;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    CheckBox speak_arabic,prac_quran,learn_quran,correction_hifaz;
    //learn goals
    CheckBox self_improv,study_abroad,pass_interview,excel,Student,Teacher;
    EditText name,email,password,country_code,refrel;
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
        country_code = findViewById(R.id.etcountry);
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

                if (speak_arabic.isChecked() || prac_quran.isChecked() || learn_quran.isChecked() || correction_hifaz.isChecked()) {
                    ln_1.setVisibility(View.GONE);
                    ln_2.setVisibility(View.VISIBLE);
                    if (speak_arabic.isChecked()) {
                        st_act.append(speak_arabic.getText().toString());
                    }
                    if (prac_quran.isChecked()) {
                        st_act.append(prac_quran.getText().toString());
                    }
                    if (learn_quran.isChecked()) {
                        st_act.append(learn_quran.getText().toString());
                    }
                    if (correction_hifaz.isChecked()) {
                        st_act.append(correction_hifaz.getText().toString());
                    }

                    Toast.makeText(Register.this, "" + st_act, Toast.LENGTH_SHORT).show();
                    //activities here

                } else {
                    Toast.makeText(Register.this, "Nothing is selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (self_improv.isChecked() || study_abroad.isChecked() || pass_interview.isChecked() || excel.isChecked()) {
                    ln_2.setVisibility(View.GONE);
                    ln_3.setVisibility(View.VISIBLE);
                    if (self_improv.isChecked()) {
                        st_goals.append(self_improv.getText().toString());
                    }
                    if (study_abroad.isChecked()) {
                        st_goals.append(study_abroad.getText().toString());
                    }
                    if (pass_interview.isChecked()) {
                        st_goals.append(pass_interview.getText().toString());
                    }
                    if (excel.isChecked()) {
                        st_goals.append(excel.getText().toString());
                    }

                    Toast.makeText(Register.this, "" + st_goals, Toast.LENGTH_SHORT).show();
                    //activities here

                } else {
                    Toast.makeText(Register.this, "Nothing is selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un, em, ps, country;
                un = name.getText().toString();
                em = email.getText().toString();
                ps = password.getText().toString();
                String rf = refrel.getText().toString();
                country = country_code.getText().toString();


                if (un.length() == 0 || em.length() == 0 || ps.length() == 0 || country.length() == 0) {

                    Toast.makeText(Register.this, "All fields are required !", Toast.LENGTH_SHORT).show();

                } else {

                    DatabaseReference reference = database.getReference(un);

                    reference.child("password").setValue(ps);
                    reference.child("email").setValue(em);
                    reference.child("username").setValue(un);
                    reference.child("refferal").setValue(rf);
                    reference.child("country").setValue(country);
                    if (self_improv.isChecked()) {
                        reference.child("Activity").setValue(self_improv.getText().toString());
                    }
                    if (study_abroad.isChecked()) {
                        reference.child("Activity1").setValue(study_abroad.getText().toString());
                    }
                    if (pass_interview.isChecked()) {
                        reference.child("Activity2").setValue(pass_interview.getText().toString());
                    }
                    if (excel.isChecked()) {
                        reference.child("Activity3").setValue(excel.getText().toString());
                    }
                    if (speak_arabic.isChecked()) {
                        reference.child("std").setValue(speak_arabic.getText().toString());
                    }
                    if (prac_quran.isChecked()) {
                        reference.child("std1").setValue(prac_quran.getText().toString());
                    }
                    if (learn_quran.isChecked()) {
                        reference.child("std2").setValue(learn_quran.getText().toString());
                    }
                    if (correction_hifaz.isChecked()) {
                        reference.child("std3").setValue(correction_hifaz.getText().toString());
                    }


                    //image will be added there

                    Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();

                }

            }


        });
    }
}
