package com.sjsu.karthik.datastorageassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

public class SQLiteView extends AppCompatActivity {
    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_view);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    @Override
    public void onResume(){

        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    public void saveMessage(View view)
    {
        EditText sqlMsgTxt=(EditText)findViewById(R.id.sqlMsg);
        String message=sqlMsgTxt.getText().toString();
        DataController dataController=new DataController(getBaseContext());
        dataController.open();
        long retValue= dataController.insert(message);
        dataController.close();
        if(retValue!=-1)
        {
            Context context = getApplicationContext();
           // CharSequence text=getString(R.string.save_success_msg);
          //  int duration= Toast.LENGTH_LONG;
          //  Toast.makeText(context, text, duration).show();

            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.putString("msg",message);
                editor.commit();

                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(PrefView.STORE_PREFERENCES,MODE_APPEND));
                out.write("\nSQLite "+counter+", "+s.format(new Date())+" message:"+message);
                out.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void reset(View v){

        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

    }

}
