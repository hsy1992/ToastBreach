package com.endless.test;

import android.os.Bundle;

import com.endless.toastbreach.ToastManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastManager.make(this).text("asdf").show();
        ToastManager.make(this).text("asdf1").show();
        ToastManager.make(this).text("asdf2").show();
        ToastManager.make(this).text("asdf3").show();
    }
}
