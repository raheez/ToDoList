package com.example.muhammedraheezrahman.todolist;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.Adapter.RecyclerAdapter;
import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends RootActivity {
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RecyclerAdapter adapter;
    private List<Todo> todoList;
    private FirebaseDatabase database;
    private FloatingActionButton addIcon;
    private DatabaseReference databaseReference;
    int visibleThreashold=1,totalCount,lastItem;
    private KProgressHUD hud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addIcon = (FloatingActionButton) findViewById(R.id.add_icon);

        database = FirebaseDatabase.getInstance();
        rv = (RecyclerView) findViewById(R.id.recycler);
        llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(llm);
        todoList = new ArrayList<>();
        adapter = new RecyclerAdapter(getApplicationContext());
        rv.setAdapter(adapter);
        todoList = getToDoList();


        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addToDo();
//                updateTodo("LU_Aqn3MKEwuqztIJAg");
//                delete("LU_Aqn3MKEwuqztIJAg");
//                showProgress();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    lastItem = llm.findLastVisibleItemPosition();
                    totalCount = llm.getItemCount();
                    if( totalCount <= (lastItem + visibleThreashold) ){

                        getToDoList();

                    }
                }
            });
        }
    }


    public void showProgress(){

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();

    }

    public void dismissProgress(){
        hud.dismiss();
    }

    private List<Todo> getToDoList() {

        final List<Todo> list = new ArrayList<>();
        database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todoList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren() ){
                    Todo todo = data.getValue(Todo.class);
                    todoList.add(todo);
                }
                if(!todoList.isEmpty())
                adapter.addToList(todoList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return list;
    }

    void addToDo() {

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
        todo.setName("Vinod new");
        todo.setMessage("My message is Freedom");
        todo.setDate(dateString);
        todo.setId(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( key, todo.toFirebaseObject());

        database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {

                    Toast.makeText(MainActivity.this,"Completed",Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });


    }

    public void updateTodo(String id){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Todo todo = new Todo();
        todo.setName("Vinod updated to Sai John");
        todo.setMessage("My message is Freedom");
        String dateString = "31/05/2016 06:15 PM";
        todo.setDate(dateString);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( id, todo.toFirebaseObject());


        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                if (databaseError == null) {

                    Toast.makeText(MainActivity.this,"Completed",Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });
    }


    public void delete(String id){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).removeValue();

    }


}
