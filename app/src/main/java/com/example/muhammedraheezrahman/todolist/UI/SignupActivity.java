package com.example.muhammedraheezrahman.todolist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.muhammedraheezrahman.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;


public class SignupActivity extends RootActivity {

    private TextInputEditText emailEt,passEt;
    private String password,email;
    Button registerBtn;
    private FirebaseAuth auth;
    RelativeLayout relativeLayout;
    Snackbar snackbar;
    KProgressHUD hud;
    boolean isEmailValid =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEt = (TextInputEditText) findViewById(R.id.email_et);
        passEt = (TextInputEditText) findViewById(R.id.passEt);
        registerBtn = (Button) findViewById(R.id.signupBut);

        auth = FirebaseAuth.getInstance();

        relativeLayout = (RelativeLayout) findViewById(R.id.rvSignup);
        registerBtn.setOnClickListener(new View.OnClickListener() {
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

                    hud = KProgressHUD.create(SignupActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setDetailsLabel("Signing in")
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    signup();

                }
            }
        });
    }

    public void signup(){
        auth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            hud.dismiss();
                            snackbar = Snackbar
                                    .make(relativeLayout, "Signup Failed", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            registerBtn.setEnabled(false);
                                            snackbar.dismiss();
                                        }
                                    });


                            snackbar.show();
                        }
                        else{
                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                            hud.dismiss();
                            registerBtn.setEnabled(true);
                            finish();
                        }
                    }
                });
    }
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
