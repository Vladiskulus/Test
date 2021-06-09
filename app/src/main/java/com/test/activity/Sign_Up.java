package com.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.test.R;

public class Sign_Up extends AppCompatActivity {

    SharedPreferences sPref;
    Editor edit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        sPref = getApplicationContext().getSharedPreferences("REGISTR", MODE_PRIVATE);
        edit = sPref.edit();
        CardView sign_up =findViewById(R.id.btn_sign_up);
        EditText email = findViewById(R.id.sign_in_email), password = findViewById(R.id.sign_in_password);;
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((email.getText().toString().trim().matches(emailPattern) && email.getText().toString().length() > 0)&&
                   (password.getText().toString().trim().length() > 4 && password.getText().toString().trim().length() < 20)){
                    String em = email.getText().toString();
                    String pass = password.getText().toString();
                    edit.putString("Email", em);
                    edit.putString("Password", pass);
                    edit.commit();
                    Intent i = new Intent(Sign_Up.this, Main.class);
                    startActivity(i);
                }
                else Toast.makeText(getApplicationContext(),"Неправильний ввід даних",Toast.LENGTH_SHORT).show();
            }
        });
    }
}