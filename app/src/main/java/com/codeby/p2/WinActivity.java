package com.codeby.p2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.codeby.p2.ui.WinFragment;


public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_win);
        String user = getIntent().getStringExtra("user");
        WinFragment winFragment = WinFragment.newInstance(user);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_container,winFragment, null).commit();
    }
}