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

public class points extends AppCompatActivity {
LinearLayout l,l1;
TextView comit_price,total_price,agenda,week_agenda;
Spinner spmin,spweek;
CheckBox chk_month,chk_quarter,chk_Semester,chk_year;
Button subscribe;


    String time1="15 minutes per Day";
    String time2="15 minutes per Day";
    String time3="35 minutes per Day";
    String time4="45 minutes per Day";
    String time5="55 minutes per Day";

    String week1="1 dayy per Week";
    String week2="2 dayy per Week";
    String week3="3 dayy per Week";
    String week4="4 dayy per Week";
    String week5="5 dayy per Week";

    String[] time={"Minutes per Day",time1,time2,time3,time4,time5};

    String[] week={"Days per Week",week1,week2,week3,week3,week4,week5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        subscribe=findViewById(R.id.btn_subcribe);

        l=findViewById(R.id.li10);
        l1=findViewById(R.id.li20);
        l.setVisibility(View.VISIBLE);
        l1.setVisibility(View.VISIBLE);


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
        ArrayAdapter aa=new ArrayAdapter(points.this,R.layout.support_simple_spinner_dropdown_item,time);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spmin.setAdapter(aa);
        spmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(time[i].equals(time1)){agenda.setText("35");}
                if(time[i].equals(time2)){agenda.setText("45");}
                if(time[i].equals(time3)){agenda.setText("55");}
                if(time[i].equals(time4)){agenda.setText("65");}
                if(time[i].equals(time5)){agenda.setText("75");}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter aa2=new ArrayAdapter(points.this,R.layout.support_simple_spinner_dropdown_item,week);
        aa2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spweek.setAdapter(aa2);
        spweek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(week[i].equals(week1)){week_agenda.setText("1");}
                if(week[i].equals(week2)){week_agenda.setText("2");}
                if(week[i].equals(week3)){week_agenda.setText("3");}
                if(week[i].equals(week4)){week_agenda.setText("4");}
                if(week[i].equals(week5)){week_agenda.setText("5");}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       chk_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            comit_price.setText("130");
                total_price.setText("111");
                chk_Semester.setChecked(false);
                chk_quarter.setChecked(false);
                chk_year.setChecked(false);

            }
        });
        chk_quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("111");
                total_price.setText("111");
                chk_month.setChecked(false);
                chk_Semester.setChecked(false);
                chk_year.setChecked(false);
            }
        });  chk_Semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("98");
                total_price.setText("98");
                chk_month.setChecked(false);
                chk_quarter.setChecked(false);
                chk_year.setChecked(false);
            }
        });  chk_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comit_price.setText("85");
                total_price.setText("85");
                chk_month.setChecked(false);
                chk_quarter.setChecked(false);
                chk_Semester.setChecked(false);

            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i=Integer.parseInt(total_price.getText().toString());

                Toast.makeText(points.this, "Total payable is "+i+"Proceed to Pay", Toast.LENGTH_SHORT).show();



            }
        });




    }
}
