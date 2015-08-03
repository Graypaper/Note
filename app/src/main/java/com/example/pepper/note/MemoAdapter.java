package com.example.pepper.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pepper on 2015/7/29.
 */
public class MemoAdapter extends ArrayAdapter<Memo>{
    private int resource;
    private List<Memo> memoList;
    private TextView textView;
    public MemoAdapter(Context context,int resource,List memos){
        super(context,resource,memos);
        this.resource = resource;
        this.memoList = memos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;

        Memo memo = getMemo(position);
        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, linearLayout, true);
        }
        else {
            linearLayout = (LinearLayout) convertView;
        }

        textView = (TextView)linearLayout.findViewById(R.id.multi_list_content);
        textView.setText(memo.getContent());

        return linearLayout;
    }

    public Memo getMemo(int id){
        return memoList.get(id);
    }
}
