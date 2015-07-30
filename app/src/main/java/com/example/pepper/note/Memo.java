package com.example.pepper.note;

import java.io.Serializable;

/**
 * Created by Pepper on 2015/7/29.
 */
public class Memo implements Serializable{
    private long id;
    private String title;
    private String content;
    private String date;
    private int editCount;

    public Memo(){};
    public Memo(long id ,String content ,String date){
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Memo(String title,String content,String data){
        this.title =title;
        this.content = content;
        this.date = data;
    }

    public long getId(){return id;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    public String getTitle(){return title;}
    public void setId(long id){this.id = id;}
    public void setDate(String date){this.date = date;}
    public void setContent(String content){this.content = content;}
}
