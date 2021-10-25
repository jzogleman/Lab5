package com.example.lab5milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void loginFunction(View view){

        EditText usernameText = (EditText) findViewById(R.id.editTextTextPersonName);

        String username = usernameText.getText().toString();

        goToActivity(username);
    }

    public void goToActivity(String username) {

        Intent intent = new Intent(this, MainActivity2.class);

        intent.putExtra("username", username);

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.lab5milestone1>", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.lab5milestone1>", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            goToActivity(sharedPreferences.getString("username", ""));
        }else {
            setContentView(R.layout.activity_main);
        }

    }
}