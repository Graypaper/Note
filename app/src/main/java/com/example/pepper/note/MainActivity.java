package com.example.pepper.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView listView;
    private Button newNotebtn;
    private ArrayAdapter listAdapter;
    private final int newMemo = 1;
    private List<Memo> memolist;
    private List<String> strList;
    private ItemDAO itemDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemDAO = new ItemDAO(getApplicationContext());
        findView();
        showList();
        setListener();
    }

    public void findView(){
        listView = (ListView)findViewById(R.id.listView);
        newNotebtn = (Button)findViewById(R.id.newNote);
    }

    public void showList(){
        memolist = itemDAO.getAll();
        strList = new ArrayList<String>();
        Iterator<Memo> it = memolist.iterator();
        while(it.hasNext()){
            String str = ((Memo)it.next()).getTitle();
            strList.add(str);
            System.out.println("memo title: " + str);
        }
        listAdapter = new ArrayAdapter<String>(this,R.layout.listitem,R.id.listContent,strList);
        listView.setAdapter(listAdapter);
    }

    public void setListener() {
        newNotebtn.setOnClickListener(new NewButtonListener());
        listView.setOnItemClickListener(new ItemListener());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class NewButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(),NewMomo.class);
            startActivityForResult(intent,newMemo);
        }
    }

    class ItemListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent,View v,int pos,long id){
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getBaseContext(),Content.class);
            Memo memo = memolist.get((int)id);
            bundle.putSerializable("memo",memo);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data==null)
            return;
        switch(requestCode){
            case newMemo:
                Bundle bundle  = data.getExtras();
                Memo memo = (Memo)bundle.getSerializable("memo");
                itemDAO.insert(memo);
                strList.add(memo.getTitle());
                listAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        showList();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
