package com.example.muhammedraheezrahman.todolist.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTodoActivity extends RootActivity {

    private FloatingActionButton addFab;
    private String title,message,date,status;
    private TextInputEditText titleEt,messageEt;
    private CalendarView calendarView;
    private CheckBox statusCb;
    private String dateString;
    private Long dateValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        addFab = (FloatingActionButton) findViewById(R.id.add_fab);
        titleEt = (TextInputEditText) findViewById(R.id.title_et);
        messageEt = (TextInputEditText) findViewById(R.id.message_Et);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        statusCb = (CheckBox) findViewById(R.id.statusCb);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                dateString = day+"-"+(month+1)+"-"+year;

            }
        });

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
    }
    public void addTodo(){
        title = titleEt.getText().toString();
        message = messageEt.getText().toString();
        if (statusCb.isChecked()){
            status = "Completed";
        }
        else
            status = "Inprogress";

        if (title.isEmpty()){
            titleEt.setError("Please enter the Title");
        }
        if (message.isEmpty()){
            message = " ";
        }
        if (!title.isEmpty()){


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String key = database.getReference("todoList").push().getKey();

            Todo todo = new Todo();
            todo.setTitle(title);
            todo.setMessage(message);
            todo.setDate(dateString);
            todo.setId(key);
            todo.setStatus(status);

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put( key, todo.toFirebaseObject());

            database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {

//                    Toast.makeText(AddTodoActivity.this,"Completed",Toast.LENGTH_SHORT).show();
                    finish();
                    }
                }
            });
        }
    }

}
