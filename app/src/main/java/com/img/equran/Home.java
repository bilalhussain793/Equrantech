package com.img.equran;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.android.volley.VolleyLog.TAG;


public class Home extends Fragment {
        Button bt_minssub;
        TextView sub_min,name1,email,uid;
        ImageView imageView;
        View view;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            view= inflater.inflate(R.layout.fragment_home, container, false);

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            sub_min=view.findViewById(R.id.submins);
            bt_minssub=view.findViewById(R.id.btn_mins);
            imageView=view.findViewById(R.id.profileImage);
            name1=view.findViewById(R.id.name);
            email=view.findViewById(R.id.email);
            uid=view.findViewById(R.id.userId);

            Picasso.with(getActivity()).load("https://firebasestorage.googleapis.com" +
                    "/v0/b/" +
                    "teacherequran.appspot.com/o/img%2F"
                    +UserDetails.phone+
                    "?alt=media&token=53e6c894-27a6-4318-8288-d603a039124e")
                    .transform(new CircleTransform())
                    .into(imageView);

            name1.setText(UserDetails.Name);
            email.setText(UserDetails.Email);
            uid.setText("UID: "+UserDetails.phone);

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