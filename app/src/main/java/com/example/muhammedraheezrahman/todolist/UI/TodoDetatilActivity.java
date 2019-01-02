package com.example.muhammedraheezrahman.todolist.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoDetatilActivity extends RootActivity {
    private List<Todo> searchList;
    private DatabaseReference databaseReference;
    private TextInputEditText titleET,messageEt;
    private CalendarView calendarView;
    private CheckBox statusCb;
    private String dateString;
    private long dateLongType;
    private ShimmerFrameLayout shimmerFrameLayout;
    String key;

    Todo todo;
    Todo todoobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        titleET = (TextInputEditText) findViewById(R.id.titleEt);
        messageEt = (TextInputEditText) findViewById(R.id.messageEt);
        statusCb = (CheckBox) findViewById(R.id.status_Cb);
        calendarView = (CalendarView) findViewById(R.id.calendar_View);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_detail_layout);

        Bundle p = getIntent().getExtras();
        if (p!=null)
        key =p.getString("Key");
        getTodo();
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void searchTodo(){

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren() ){
                             todoobj = data.getValue(Todo.class);
                        }
                        if (todoobj!= null){
                            titleET.setText(todoobj.gettitle());
                            messageEt.setText(todoobj.getMessage());
                            if (todoobj.getStatus().equals("Completed")){
                                statusCb.setChecked(true);
                            }else if (todoobj.getStatus().equals("Inprogress")){
                                statusCb.setChecked(false);
                            }

                            try {
                                dateString = todoobj.getDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = sdf.parse(dateString);

                                dateLongType = date.getTime();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            calendarView.setDate(dateLongType);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void getTodo(){

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("id").equalTo(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren() ){
                             todo = data.getValue(Todo.class);
                        }
                        todoobj = todo;
                        if (todoobj!= null){
                            titleET.setText(todoobj.gettitle());
                            messageEt.setText(todoobj.getMessage());
                            if (todoobj.getStatus().equals("Completed")){
                                statusCb.setChecked(true);
                            }else if (todoobj.getStatus().equals("Inprogress")){
                                statusCb.setChecked(false);
                            }

                            try {
                                dateString = todoobj.getDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = sdf.parse(dateString);

                                dateLongType = date.getTime();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            calendarView.setDate(dateLongType);
                        }

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void updateTodo(String id){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Todo todo = new Todo();
        todo.setTitle("Vinod updated to Sai John");
        todo.setMessage("My message is Freedom");
        String dateString = "31/05/2016 06:15 PM";
        todo.setDate(dateString);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( id, todo.toFirebaseObject());

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        if (databaseError == null) {

                            Toast.makeText(TodoDetatilActivity.this,"Completed",Toast.LENGTH_SHORT).show();
//                    finish();
                        }
                    }
                });
    }
}
