package com.sjsu.karthik.datastorageassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    private Activity context=this;
    TextView resultTxtValue;
    String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            InputStream in=openFileInput(PrefView.STORE_PREFERENCES);
            if(in!=null)
            {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null)
                {
                    buf.append(str +"\n");
                }
                in.close();
                resultTxtValue = (TextView)findViewById(R.id.resultTxt);
                resultTxtValue.setText(buf.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void gotoPrefView(View v){

        Intent i = new Intent(this,PrefView.class);
        startActivity(i);

    }

    public void gotoSQLiteView(View v){

        Intent i = new Intent(this,SQLiteView.class);
        startActivity(i);

    }

    public void exit(View v){

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
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
}
