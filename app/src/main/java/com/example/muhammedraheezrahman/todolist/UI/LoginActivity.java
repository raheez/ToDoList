package com.example.muhammedraheezrahman.todolist.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

public class LoginActivity extends RootActivity {

    TextInputEditText emailEt,passEt;
    Button loginbtn, signupbtn;
    String email,password;
    private FirebaseAuth auth;
    KProgressHUD hud;
    RelativeLayout relativeLayout;
    Snackbar snackbar;
    boolean isEmailValid =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn = (Button) findViewById(R.id.loginBut);
        signupbtn = (Button) findViewById(R.id.signupBut);
        emailEt = (TextInputEditText) findViewById(R.id.email_et);
        passEt = (TextInputEditText) findViewById(R.id.passEt);
        relativeLayout = (RelativeLayout) findViewById(R.id.rv);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEt.getText().toString();
                password = passEt.getText().toString();

                isEmailValid = isValidEmail(email);

                if(email.isEmpty()){
                    emailEt.setError("Email is Empty");
                }
                else  if (!isEmailValid){
                    emailEt.setError("Email not valid");
                }
                if (password.isEmpty()){
                    passEt.setError("Password is Empty");
                }

                if (isEmailValid && !password.isEmpty()){

                    loginbtn.setEnabled(false);
                     hud = KProgressHUD.create(LoginActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setDetailsLabel("Loging in")
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    login();

                }


            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });
    }

    public void login(){
        auth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){

                            Log.d("RanLogin","Login failed");
                            hud.dismiss();
                            loginbtn.setEnabled(true);
                              snackbar = Snackbar
                                    .make(relativeLayout, "Login Failed", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            snackbar.dismiss();
                                        }
                                    });

                            snackbar.show();
                        }
                        else {
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            hud.dismiss();
                            loginbtn.setEnabled(true);
                            finish();
                        }
                    }
                });

    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
