package com.codeby.p2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, GameActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
            }
        });
    }
}