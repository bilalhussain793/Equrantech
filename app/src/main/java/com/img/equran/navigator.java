package com.img.equran;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class navigator extends AppCompatActivity {
    private TextView mTextMessage;
    BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (UserDetails.Type.equals("Teacher")){

                Menu menu =navView.getMenu();

                MenuItem target = menu.findItem(R.id.navigation_tutor);

                target.setVisible(false);

            }

            switch (item.getItemId()) {

                case R.id.navigation_home:
                        Home blankFragment = new Home();
                        FragmentManager f = getSupportFragmentManager();
                        f.beginTransaction()
                                .replace(R.id.fragment_container, blankFragment)
                                .addToBackStack(null)
                                .commit();

                    return true;
                case R.id.navigation_account:




                        Account blankFragment3 = new Account();
                        FragmentManager f2 = getSupportFragmentManager();
                        f2.beginTransaction()

                                .replace(R.id.fragment_container, blankFragment3)
                                .addToBackStack(null)
                                .commit();

                    return true;
                case R.id.navigation_options:



                        Options blankFragment4 = new Options();
                        FragmentManager f4 = getSupportFragmentManager();
                        f4.beginTransaction()
                                .replace(R.id.fragment_container, blankFragment4)
                                .addToBackStack(null)
                                .commit();

                    return true;
                case R.id.navigation_tutor:
                        Tutor blankFragment5 = new Tutor();
                        FragmentManager f5 = getSupportFragmentManager();
                        f5.beginTransaction()
                                .replace(R.id.fragment_container, blankFragment5)
                                .addToBackStack(null)
                                .commit();
                    return true;
                case R.id.chat_frag:

                    MessageFragment blankFragment6 = new MessageFragment();
                    FragmentManager f6 = getSupportFragmentManager();
                    f6.beginTransaction()
                            .replace(R.id.fragment_container, blankFragment6)
                            .addToBackStack(null)
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
         navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Home blankFragment = new Home();
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, blankFragment)
                .commit();
        if (UserDetails.Type.equals("Teacher")){
            navView.findViewById(R.id.navigation_tutor).setVisibility(View.GONE);
        }
    }
}
