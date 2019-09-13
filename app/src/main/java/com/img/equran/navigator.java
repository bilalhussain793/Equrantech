package com.img.equran;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class navigator extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {



                case R.id.navigation_home:
                    Home blankFragment = new Home();
                    FragmentManager f = getSupportFragmentManager();
                    f.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment)
                            .commit();
                    return true;
                case R.id.navigation_account:
                    Home blankFragment3 = new Home();
                    FragmentManager f2 = getSupportFragmentManager();
                    f2.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment3)
                            .commit();
                    return true;
                case R.id.navigation_options:
                    Home blankFragment4 = new Home();
                    FragmentManager f4 = getSupportFragmentManager();
                    f4.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment4)
                            .commit();
                    return true;
                case R.id.navigation_tutor:
                    Tutor blankFragment5 = new Tutor();
                    FragmentManager f5 = getSupportFragmentManager();
                    f5.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment5)
                            .commit();
                    return true;
                case R.id.chat_frag:
                    MessageFragment blankFragment6 = new MessageFragment();
                    FragmentManager f6 = getSupportFragmentManager();
                    f6.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment6)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Home blankFragment = new Home();
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, blankFragment)
                .commit();
    }

}
