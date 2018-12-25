package com.example.muhammedraheezrahman.todolist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhammedraheezrahman.todolist.Model.Todo;
import com.example.muhammedraheezrahman.todolist.R;

import java.util.ArrayList;
import java.util.List;

class Viewholder extends RecyclerView.ViewHolder{

    public TextView titleTv;
    public Viewholder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.nametv);
    }

}
public class RecyclerAdapter extends RecyclerView.Adapter<Viewholder> {

    private List<Todo> list = new ArrayList<>();
    private Context context;
    private Todo todo;
    public RecyclerAdapter(Context context){

        this.context = context;
    }
    public RecyclerAdapter(List<Todo> list){
        this.list = list;

    }

    public void  addToList(List<Todo> list){

        this.list.addAll(list);
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
}
