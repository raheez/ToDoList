package com.example.muhammedraheezrahman.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;
import com.example.muhammedraheezrahman.todolist.UI.TodoDetatilActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class Viewholder extends RecyclerView.ViewHolder{

    public TextView titleTv;
    public TextView messageTv;
    public TextView dateTv;
    public CircleImageView statusImage;
    public Viewholder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.titleTv);
        messageTv = (TextView) itemView.findViewById(R.id.messageTv);
        dateTv = (TextView) itemView.findViewById(R.id.dateTv);
        statusImage = (CircleImageView) itemView.findViewById(R.id.statusImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(),TodoDetatilActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Todo todo = RecyclerAdapter.todoList.get(getAdapterPosition());
                String id = todo.getId();
                Bundle bundle = new Bundle();
                bundle.putString("Key",id);
                i.putExtras(bundle);
                view.getContext().startActivity(i);



            }
        });
    }



}
public class RecyclerAdapter extends RecyclerView.Adapter<Viewholder>{

    public List<Todo> list = new ArrayList<>();
    public  static List<Todo> todoList = new ArrayList<>();
    private List<Todo> searchResults = new ArrayList<>();
    private Context context;
    private Todo todo;

    public RecyclerAdapter(Context context){

        this.context = context;
    }
    public RecyclerAdapter(List<Todo> list){
        this.list = list;

    }

    public void addToList(List<Todo> list){


        this.list.clear();
        this.list.addAll(list);
        this.todoList.clear();
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
        if (!todo.gettitle().toString().isEmpty()){

            holder.titleTv.setText(todo.gettitle().toString());

        }
        if (!todo.getMessage().isEmpty()){
            holder.messageTv.setText(todo.getMessage().toString());
        }
        if (!todo.getStatus().isEmpty()){
            if (todo.getStatus().equals("Completed")){
                Glide.with(context).load(R.drawable.completed).into(holder.statusImage);
            }else if  (todo.getStatus().equals("Inprogress")){
                Glide.with(context).load(R.drawable.progressicon).into(holder.statusImage);

            }
        }
        if (!todo.getDate().isEmpty()){
            holder.dateTv.setText(todo.getDate());
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
