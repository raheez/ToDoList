package com.example.muhammedraheezrahman.todolist.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.Adapter.RecyclerAdapter;
import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.example.muhammedraheezrahman.todolist.ViewModel.TodoViewmodel;
import com.facebook.shimmer.ShimmerFrameLayout;
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

public class ListActivity extends RootActivity {
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RecyclerAdapter adapter;
    private List<Todo> todoList;
    private FirebaseDatabase database;
    private FloatingActionButton addIcon;
    private DatabaseReference databaseReference;
    int visibleThreashold=1,totalCount,lastItem;
    private KProgressHUD hud;
    private List<Todo> list;
    private List<Todo> searchList;
    Snackbar snackbar;
    TodoViewmodel todovm;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addIcon = (FloatingActionButton) findViewById(R.id.add_icon);
        shimmerFrameLayout  = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout);
        database = FirebaseDatabase.getInstance();
        rv = (RecyclerView) findViewById(R.id.recycler);
        llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(llm);

        todoList = new ArrayList<>();
        adapter = new RecyclerAdapter(getApplicationContext());
        rv.setAdapter(adapter);
        llm.setSmoothScrollbarEnabled(true);
        todovm = ViewModelProviders.of(this).get(TodoViewmodel.class);

        llm.setSmoothScrollbarEnabled(false);
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this,AddTodoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        super.onResume();
        getToDoList();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getToDoList() {
        list = new ArrayList<>();
        list.clear();
        database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren() ){
                            Todo todo = data.getValue(Todo.class);
                            list.add(todo);
                        }
                        if(!list.isEmpty()){
                            adapter.addToList(list);
                        }
                        if (list.isEmpty()){
                        }
                        stopShimmerEffect();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    void stopShimmerEffect(){

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
