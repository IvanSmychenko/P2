package com.codeby.p2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EditText usernameED = findViewById(R.id.ed_username);
        EditText passwordED = findViewById(R.id.ed_password);
        Button btnGo = findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameED.getText().toString();
                String password = passwordED.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name", username);
                    intent.putExtra("psw", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast_msg,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}