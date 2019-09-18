package com.img.equran;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button bt_log,btn_google,std,teacher;
    TextView bt_signup;
    EditText et_Username,et_Password;
    Dialog d;
    String select;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    String name, email;
    String idToken;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "MainActivity";

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


        firebaseAuth = FirebaseAuth.getInstance();
        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user != null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,  this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


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
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
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
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
            //   startActivity(new Intent(MainActivity.this,Navdrawer.class));
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(this, "Login Unsuccessful"+result, Toast.LENGTH_LONG).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            gotoProfile();
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void gotoProfile(){


        Intent intent = new Intent(MainActivity.this, navigator.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authStateListener != null){
            FirebaseAuth.getInstance().signOut();
        }
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}