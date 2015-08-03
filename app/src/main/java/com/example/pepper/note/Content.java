package com.example.pepper.note;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Content extends ActionBarActivity {
    private EditText editText;
    private Memo memo;
    private LinearLayout linearLayout;
    private ImageButton editButton;
    private ImageButton infoButton;
    private ImageButton delButton;
    private Button saveButton;
    private ItemDAO itemDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        findView();
        getSupportActionBar().hide();
        itemDAO = new ItemDAO(getApplicationContext());
        editText.setEnabled(false);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        memo = (Memo)bundle.getSerializable("memo");
        editText.setText(memo.getContent());
        setListener();
    }

    public void findView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        editText = (EditText)findViewById(R.id.editText);
        editButton = (ImageButton)findViewById(R.id.edit_button);
        infoButton = (ImageButton)findViewById(R.id.info_button);
        delButton = (ImageButton)findViewById(R.id.del_button);
        saveButton = (Button)findViewById(R.id.save_button);
    }

    public void setListener(){
        ButtonListener buttonListener = new ButtonListener();
        editButton.setOnClickListener(buttonListener);
        delButton.setOnClickListener(buttonListener);
        infoButton.setOnClickListener(buttonListener);
        saveButton.setOnClickListener(buttonListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
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

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.edit_button:
                    editText.setEnabled(true);
                    saveButton.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    break;
                case R.id.info_button:
                    infoAction();
                    break;
                case R.id.del_button:
                    deleteAction();

                    break;
                case R.id.save_button:
                    String str = editText.getText().toString();
                    String title;
                    if(str.indexOf('\n')==-1)
                        title = str;
                    else
                        title = str.substring(0, str.indexOf("\n"));
                    memo.setContent(str);
                    memo.setTitle(title);
                    memo.setDate();
                    if(!itemDAO.update(memo)){
                        System.out.println("failed to save");
                    }

                    saveButton.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    editText.setEnabled(false);
                    break;
            }
        }
    }

    public void deleteAction(){
        LayoutInflater layoutInflater = LayoutInflater.from(Content.this);
        View view = layoutInflater.inflate(R.layout.deletcheck,null);
        final Dialog dialog = new Dialog(Content.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Button yesButton = (Button)dialog.findViewById(R.id.yes_button);
        Button noButton = (Button)dialog.findViewById(R.id.no_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemDAO.delete(memo.getId())){
                    System.out.println("failed to delete");
                }
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void infoAction(){
        LayoutInflater layoutInflater = LayoutInflater.from(Content.this);
        View view = layoutInflater.inflate(R.layout.information,null);
        final Dialog dialog = new Dialog(Content.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        EditText infoText = (EditText)dialog.findViewById(R.id.infoText);
        Button infoOkButton = (Button)dialog.findViewById(R.id.info_ok_button);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        infoText.setText("latest edit in \n" + memo.getDate());
        infoOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
