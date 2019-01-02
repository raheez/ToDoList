package com.example.muhammedraheezrahman.todolist.Model;

import java.util.HashMap;

public class Todo {
    private String tilte;
    private String message;
    private String date;
    private String id;
    private String status;



    public Todo() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String gettitle() {
        return tilte;
    }

    public void setTitle(String tilte) {
        this.tilte = tilte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> todo =  new HashMap<String,String>();
        todo.put("title", tilte);
        todo.put("message", message);
        todo.put("date", date);
        todo.put("id",id);
        todo.put("status",status);

        return todo;
    }

}
