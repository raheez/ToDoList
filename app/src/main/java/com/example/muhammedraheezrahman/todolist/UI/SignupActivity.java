package com.example.muhammedraheezrahman.todolist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends RootActivity {

    private TextInputEditText emailEt,passEt;
    private String password,email;
    Button register;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEt = (TextInputEditText) findViewById(R.id.email_et);
        passEt = (TextInputEditText) findViewById(R.id.passEt);
        register = (Button) findViewById(R.id.registerBut);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEt.getText().toString();
                password = passEt.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){

                    auth.createUserWithEmailAndPassword(email,password).
                            addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()){
                                        Log.d("RanSignup","Signup failed");
                                        Toast.makeText(SignupActivity.this,"Registeration Unsuccesffull",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Log.d("RanSignup","Login failed");
                                        Toast.makeText(SignupActivity.this,"Registeration succesffull",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
