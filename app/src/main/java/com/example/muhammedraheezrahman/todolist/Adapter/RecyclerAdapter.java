package com.example.muhammedraheezrahman.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.example.muhammedraheezrahman.todolist.UI.TodoDetatilActivity;

import java.util.ArrayList;
import java.util.List;

class Viewholder extends RecyclerView.ViewHolder{

    public TextView titleTv;
    public Viewholder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.nametv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"Adapter Position is "+ getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(),TodoDetatilActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);

            }
        });
    }



}
public class RecyclerAdapter extends RecyclerView.Adapter<Viewholder>{

    private List<Todo> list = new ArrayList<>();
    private List<Todo> todoList = new ArrayList<>();
    private List<Todo> searchResults = new ArrayList<>();
    private Context context;
    private Todo todo;
    public RecyclerAdapter(Context context){

        this.context = context;
    }
    public RecyclerAdapter(List<Todo> list){
        this.list = list;

    }

    public void  addToList(List<Todo> list){


        this.list.clear();
        this.list.addAll(list);
        this.todoList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(context).inflate(R.layout.listitem,parent,false);
         return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {


        if (list.size()>0)
        todo = list.get(position);
        if (!todo.getName().toString().isEmpty()){

            holder.titleTv.setText(todo.getName().toString());

        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public void addToSearchList(List<Todo> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }
}
