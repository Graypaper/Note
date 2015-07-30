package com.example.pepper.note;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Pepper on 2015/7/29.
 */
public class MemoAdapter extends ArrayAdapter<Memo>{
    private int resource;
    private List<Memo> memoList;

    public MemoAdapter(Context context,int resource,List memos){
        super(context,resource,memos);
        this.resource = resource;
        this.memoList = memos;
    }

}
