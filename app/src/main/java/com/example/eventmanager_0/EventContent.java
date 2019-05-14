package com.example.eventmanager_0;

public class EventContent {
    private int id;
    private int index;
    private String title;
    private String type;
    private String subject;

    public EventContent(String title, String type, String subject){
        this.title = title;
        this.type = type;
        this.subject = subject;
    }
    public int getId(){ return this.id; }

    public void setId(int id){ this.id = id; }

    public int getIndex(){ return this.index; }

    public void setIndex(int index){ this.index = index; }

    public String getTitle(){ return this.title; }

    public void setTitle(String title){ this.title = title; }

    public String getType(){ return this.type; }

    public void setType(String type){ this.type = type; }

    public String getSubject(){ return this.subject; }

    public void setSubject(String subject){ this.subject = subject; }
}
