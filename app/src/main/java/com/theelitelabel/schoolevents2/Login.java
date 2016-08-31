package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends BaseActivity {
    EditText emailfield;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button loginbutton;
    private static final String TAG = "EmailPassword";
    TextView ksudomain;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailfield = (EditText)findViewById(R.id.ksu_email);
        loginbutton = (Button)findViewById(R.id.login);
        ksudomain = (TextView)findViewById(R.id.ksu_domain);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //User is signed in
                    System.out.println(user.getUid());
                }else{
                    //User is signed out
                    System.out.println("User is signed out");
                }

            }
        };
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.login) {
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailfield.getText().toString().toLowerCase() + "@" + ksudomain.getText()).matches()){
                        createAccount(emailfield.getText().toString().toLowerCase() + "@" + ksudomain.getText(), "KSU30101");
                        Intent intent = new Intent(view.getContext(),MainActivity.class);
                        intent.putExtra("user",emailfield.getText().toString().toLowerCase());
                        startActivity(intent);
                        System.out.println(emailfield.getText().toString() + "@" + ksudomain.getText());
                    }else{
                        Toast.makeText(Login.this, "invalid email",
                                Toast.LENGTH_SHORT).show();
                    }


                }
                else if(i == R.id.register){
                    signIn(emailfield.getText().toString().toLowerCase() + "@" + ksudomain.getText(), "KSU30101");
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    intent.putExtra("user",emailfield.getText());
                    startActivity(intent);
                    System.out.println(emailfield.getText().toString() + "@" + ksudomain.getText());
                }
            }
        });






    }
    private boolean validateForm() {
        boolean valid = true;

        String email = emailfield.getText().toString() + ksudomain;
        if (TextUtils.isEmpty(email)) {
            emailfield.setError("Required.");
            valid = false;
        } else {
            emailfield.setError(null);
        }
        String password = "KSU30101";



        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }





        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "create auth failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login.this, "sign-in auth failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        /*if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }*/
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }





}
