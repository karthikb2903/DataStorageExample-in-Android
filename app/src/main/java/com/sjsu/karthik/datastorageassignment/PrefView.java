package com.sjsu.karthik.datastorageassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrefView extends AppCompatActivity {
    EditText bookTxt,authorTxt,descTxt;
    public final static String STORE_PREFERENCES="storePrefFinal.txt";
    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_view);
        bookTxt = (EditText)findViewById(R.id.bookTxt);
        authorTxt = (EditText)findViewById(R.id.authorTxt);
        descTxt = (EditText)findViewById(R.id.descTxt);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);


    }

    public void reset(View v){

        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("EXIT", true);
        startActivity(i);
    }


    public void savePref(View v){
        //SharedPreferences settings;
        String bookName = bookTxt.getText().toString();
        String bookAuthor = authorTxt.getText().toString();
        String bookDesc = descTxt.getText().toString();

        if(bookName!=null && bookAuthor!=null && bookDesc!=null)
        {
            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("COUNTER", counter);

                editor.putString("book", bookName);
                editor.commit();

              //  SharedPreferences settings = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(STORE_PREFERENCES,MODE_APPEND));
                String message="\nPreferences "+counter+", "+s.format(new Date())+" book: "+bookName;
                out.write(message);
                out.close();
            }
             catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

     public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);
    }




}
