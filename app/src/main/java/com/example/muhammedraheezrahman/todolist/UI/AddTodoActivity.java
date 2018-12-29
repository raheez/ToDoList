package com.example.muhammedraheezrahman.todolist.UI;

import android.os.Bundle;
import android.util.Log;

import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTodoActivity extends RootActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToDo();
    }
    public void addToDo() {

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dateString = format.format(date);
        //make the modal object and convert it into hasmap


        //second section
        //save it to the firebase db

        dateString = "31/05/2016 06:15 PM";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("todoList").push().getKey();
        Log.d("AlanLey","the key is "+ key);

        Todo todo = new Todo();
        todo.setName("Rahman ");
        todo.setMessage("Explore life");
        todo.setDate(dateString);
        todo.setId(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( key, todo.toFirebaseObject());

        database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {

//                    Toast.makeText(MainActivity.this,"Completed",Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });


    }
}
