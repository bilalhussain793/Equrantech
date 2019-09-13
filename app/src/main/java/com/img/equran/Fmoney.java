package com.img.equran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Fmoney extends AppCompatActivity {
    LinearLayout l,l1;
    TextView comit_price,total_price,agenda,week_agenda,estim;
    Spinner spmin,spweek;
    CheckBox chk_month,chk_quarter,chk_Semester,chk_year;
    Button subscribe;

    double hisab=0.1;

    String time1="15 minutes per Day";
    String time2="30 minutes per Day";
    String time3="45 minutes per Day";
    String time4="60 minutes per Day";


    String week1="3 day per Week";
    String week2="4 day per Week";
    String week3="5 day per Week";


    String[] time={"Minutes per Day",time1,time2,time3,time4};

    String[] week={"Days per Week",week1,week2,week3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getponits);
        subscribe=findViewById(R.id.btn_subcribe);

        l=findViewById(R.id.li10);
        l1=findViewById(R.id.li20);
        l.setVisibility(View.VISIBLE);
        l1.setVisibility(View.VISIBLE);


        estim=findViewById(R.id.esimate);
        week_agenda=findViewById(R.id.agenda_weekly);  //Days in a Week
        agenda=findViewById(R.id.agenda);              // Min in a Day
        comit_price=findViewById(R.id.comprice);        //Price commit
        total_price=findViewById(R.id.tot);             //Total

        chk_month=findViewById(R.id.chkmonth);
        chk_quarter=findViewById(R.id.chkquarter);
        chk_Semester=findViewById(R.id.chksemester);
        chk_year=findViewById(R.id.chkyear);

        spweek=findViewById(R.id.spinner1);
        spmin=findViewById(R.id.spinner);
        ArrayAdapter aa=new ArrayAdapter(Fmoney.this,R.layout.support_simple_spinner_dropdown_item,time);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spmin.setAdapter(aa);
        spmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l ) {

                if(time[i].equals(time1)){agenda.setText("15");}
                if(time[i].equals(time2)){agenda.setText("30");}
                if(time[i].equals(time3)){agenda.setText("45");}
                if(time[i].equals(time4)){agenda.setText("60");}


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter aa2=new ArrayAdapter(Fmoney.this,R.layout.support_simple_spinner_dropdown_item,week);
        aa2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spweek.setAdapter(aa2);
        spweek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(week[i].equals(week1)){week_agenda.setText("3");
                    if(agenda.getText().toString().equals("15")){estim.setText("45");}
                    if(agenda.getText().toString().equals("30")){estim.setText("90");}
                    if(agenda.getText().toString().equals("45")){estim.setText("135");}
                    if(agenda.getText().toString().equals("60")){estim.setText("180");}
                }
                if(week[i].equals(week2)){week_agenda.setText("4");
                    if(agenda.getText().toString().equals("15")){estim.setText("60");}
                    if(agenda.getText().toString().equals("30")){estim.setText("120");}
                    if(agenda.getText().toString().equals("45")){estim.setText("180");}
                    if(agenda.getText().toString().equals("60")){estim.setText("240");}

                }
                if(week[i].equals(week3)){week_agenda.setText("5");
                    if(agenda.getText().toString().equals("15")){estim.setText("75");}
                    if(agenda.getText().toString().equals("30")){estim.setText("150");}
                    if(agenda.getText().toString().equals("45")){estim.setText("225");}
                    if(agenda.getText().toString().equals("60")){estim.setText("300");}
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        chk_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int month_week=4;
                int a=Integer.parseInt(estim.getText().toString());
                total_price.setText(a*month_week*hisab-((a*month_week*hisab)*5/100) +"");

                comit_price.setText("4");
                chk_Semester.setChecked(false);
                chk_quarter.setChecked(false);
                chk_year.setChecked(false);

            }
        });
        chk_quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("12");

                int month_week=12;
                int a=Integer.parseInt(estim.getText().toString());
                total_price.setText(a*month_week*hisab-((a*month_week*hisab)*10/100) +"");





                chk_month.setChecked(false);
                chk_Semester.setChecked(false);
                chk_year.setChecked(false);
            }
        });  chk_Semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("24");
                int month_week=24;
                int a=Integer.parseInt(estim.getText().toString());
                total_price.setText(a*month_week*hisab-((a*month_week*hisab)*15/100) +"");


                chk_month.setChecked(false);
                chk_quarter.setChecked(false);
                chk_year.setChecked(false);
            }
        });  chk_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("2");
                int month_week=2;
                int a=Integer.parseInt(estim.getText().toString());
                total_price.setText(a*month_week*hisab+"");

                chk_month.setChecked(false);
                chk_quarter.setChecked(false);
                chk_Semester.setChecked(false);

            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=Integer.parseInt(total_price.getText().toString());

                Toast.makeText(Fmoney.this, "Total payable is "+i+"Proceed to Pay", Toast.LENGTH_SHORT).show();





            }
        });




    }
}

