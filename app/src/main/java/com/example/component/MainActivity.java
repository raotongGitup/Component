package com.example.component;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.router_annotation.Route;

@Route(path = "/main/test")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}