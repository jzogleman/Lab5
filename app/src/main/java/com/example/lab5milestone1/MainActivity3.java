package com.example.lab5milestone1;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    EditText textView3;

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textView3 = (EditText) findViewById(R.id.textView3);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);
        Log.i("OnClick", "noteid = " + noteid);


        if(noteid != -1){

            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            textView3.setText(noteContent);
        }

    }

    public void saveMethod(View view){
        textView3 = (EditText) findViewById(R.id.textView3);

        String noteContent = textView3.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper helper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.lab5milestone1>", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1){
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            Log.i("OnClick", "New Note");
            helper.saveNotes(username, title, noteContent, date);
        }else {
            title = "NOTE_" + (noteid + 1);
            Log.i("OnClick", "Updating");
            helper.updateNote(title, date, noteContent, username);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}