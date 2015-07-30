package com.example.pepper.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewMomo extends ActionBarActivity {
    private EditText editText;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_momo);
        editText = (EditText)findViewById(R.id.editText);
        confirm = (Button)findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new ConfirmButtonlistener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_momo, menu);
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

    class ConfirmButtonlistener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText editText = (EditText)findViewById(R.id.editText);
            String str = editText.getText().toString();
            String title;
            if(str.indexOf('\n')==-1){
                title = str;
                System.out.println("got 1 line");
            }
            else
                title = str.substring(0, str.indexOf("\n"));
            String key = "memo";
            Memo memo = new Memo(title,str,"today");
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(key, memo);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
