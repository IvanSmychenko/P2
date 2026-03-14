package com.codeby.p2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        TextView helloUserTV = findViewById(R.id.tv_hello);
        TextView showPswTV = findViewById(R.id.tv_show_psw);
        Button buttonPlay = findViewById(R.id.btn_play);
        String username = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("psw");
        helloUserTV.setText("Hello, " + username);
        showPswTV.setText("Yor password: " + password);
        saveDate(username, password);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, GameActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
            }
        });
        //check
        TextView tvRoot = findViewById(R.id.tv_root);
        TextView tvEmulator = findViewById(R.id.tv_emulator);
        if (isEmulator()) tvEmulator.setVisibility(View.VISIBLE);
        if (checkRoot()) tvRoot.setVisibility(View.VISIBLE);

    }

    void saveDate(String name, String password){
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        preferences.edit()
                .putString("username", name)
                .putString("pass", password)
                .commit();

    }

    boolean isEmulator(){
        String s = Build.BRAND.toLowerCase(Locale.ROOT);
        return s.startsWith("google") || s.startsWith("android") || s.startsWith("generic");
    }

    boolean checkRoot(){
        File file = new File("/system/xbin/su");
        return file.exists();
    }
}