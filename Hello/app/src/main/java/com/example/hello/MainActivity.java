package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButton1Clicked(View V) {
        Toast.makeText(getApplicationContext(), "Button is pressed!", Toast.LENGTH_LONG).show();
    }

    public void onButton2Clicked(View V){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://m.naver.com"));
        startActivity(intent);
    }
}
