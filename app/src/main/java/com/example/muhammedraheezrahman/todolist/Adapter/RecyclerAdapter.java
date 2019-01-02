package com.example.muhammedraheezrahman.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
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
    public CircleImageView statusImage;
    public Viewholder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.nametv);
        messageTv = (TextView) itemView.findViewById(R.id.messageTv);
        statusImage = (CircleImageView) itemView.findViewById(R.id.statusImage);

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
        if (!todo.gettitle().toString().isEmpty()){

            holder.titleTv.setText(todo.gettitle().toString());

        }
        if (!todo.getStatus().isEmpty()){
            if (todo.getStatus().equals("Completed")){
                Glide.with(context).load(R.drawable.completed).into(holder.statusImage);
            }else if  (todo.getStatus().equals("Inprogress")){
                Glide.with(context).load(R.drawable.progressicon).into(holder.statusImage);

            }
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
