package com.codeby.p2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

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
        TextView tvHacked = findViewById(R.id.tv_sign);
        if (isEmulator()) tvEmulator.setVisibility(View.VISIBLE);
        if (checkRoot()) tvRoot.setVisibility(View.VISIBLE);
        if (!checkCRT()) tvHacked.setVisibility(View.VISIBLE);

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
    public static final String CRT = "7F:8D:39:A6:EC:6B:3C:71:96:A9:48:0F:CB:61:0B:86:95:37:71:54:97:05:2E:DE:80:4C:3B:76:A9:AE:90:9E".replace(":","");
    boolean checkCRT(){
        //проверка после версии АПИ 28 включительно 
        if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. P) { 
            return getPackageManager().hasSigningCertificate("com.codeby.p2", hexToByte(CRT), PackageManager. CERT_INPUT_SHA256); 
        } else { 
            //проверка до АПИ 28
            try { 
                PackageManager pm = getPackageManager(); 
                Signature signature = pm.getPackageInfo("com.codeby.p2", PackageManager.GET_SIGNATURES).signatures[0];
                byte[] pkgSign = MessageDigest. getInstance("SHA-256").digest(signature.toByteArray()); 
                return Arrays. equals( hexToByte(CRT), pkgSign); 
            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            } 
        } 
    } 
    //конвертация hex-строки в байтовый массив 
    public static byte[] hexToByte(String s) { 
        int len = s.length(); byte[] data = new byte[len / 2]; 
        for (int i = 0; i < len; i += 2) { 
            data[i / 2] = (byte) ((Character. digit(s.charAt(i), 16) << 4) + Character. digit(s.charAt(i + 1), 16)); 
        } 
        return data;
    }
}