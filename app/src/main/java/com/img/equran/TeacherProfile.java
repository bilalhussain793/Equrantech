package com.img.equran;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class TeacherProfile extends AppCompatActivity {

    TextView tv,email,contact,adr,fnm;
    ImageView img;
    Button btn_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        tv=findViewById(R.id.tv);
        email=findViewById(R.id.em);
        adr=findViewById(R.id.adr);
        contact=findViewById(R.id.cnt);
        fnm=findViewById(R.id.fnm);
        btn_message=findViewById(R.id.bt_msg);
        img=findViewById(R.id.ivm);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+ProfileAdapter.contact);
        contact.setText(ProfileAdapter.contact);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String nm = dataSnapshot.child("Name").getValue(String.class);
                String eml = dataSnapshot.child("Email").getValue(String.class);
                String address = dataSnapshot.child("Address").getValue(String.class);

               // Log.d(TAG, "Value is: " + value);
                tv.setText(""+nm);
                email.setText(eml);;
                adr.setText(address);
                fnm.setText(nm);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Picasso.with(TeacherProfile.this).load("https://firebasestorage.googleapis.com" +
                "/v0/b/" +
                "teacherequran.appspot.com/o/img%2F"
                +ProfileAdapter.contact+
                "?alt=media&token=53e6c894-27a6-4318-8288-d603a039124e").transform(new CircleTransform())
                .into(img);
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails.chatWith = ProfileAdapter.contact;
                startActivity(new Intent(TeacherProfile.this, ChatActivity.class));
            }
        });
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}