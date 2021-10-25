package com.example.lab5milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    TextView textView2;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        String welcomeText = "Welcome " + intent.getStringExtra("username") + "!";
        textView2.setText(welcomeText);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper helper = new DBHelper(sqLiteDatabase);
        notes = helper.readNotes(intent.getStringExtra("username"));


        ArrayList<String> displayNotes = new ArrayList<>();

        for(Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                Log.i("OnClick", "position = " + position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Log.d("myTag", "logout?" );
                SharedPreferences sharedPreferences = getSharedPreferences("<com.example.lab5milestone1>", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                goToActivity1();
                return true;
            case R.id.addNote:
                Intent intent = new Intent(this, MainActivity3.class);
                intent.putExtra("noteid", "-1");
                startActivity(intent);
                return true;

        }
        return true;
    }

    public void goToActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}