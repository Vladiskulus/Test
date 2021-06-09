package com.test.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.test.R;
import com.test.util.UserSession;


public class Sign_In extends AppCompatActivity implements View.OnClickListener{

    EditText email, password;
    CardView sign_in, sign_up;
    Intent i;
    SharedPreferences sP;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        session = new UserSession(getApplicationContext());
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        sign_in=findViewById(R.id.btn_sign_in);
        sign_in.setOnClickListener(this);
        sign_up=findViewById(R.id.btn_sign_up);
        sign_up.setOnClickListener(this);
        sign_in.setVisibility(View.VISIBLE);
        sP = getSharedPreferences("REGISTR", MODE_PRIVATE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                String em = email.getText().toString();
                String pass = password.getText().toString();
                if(em.trim().length() > 0 && pass.trim().length() > 0){
                    String eMail = null;
                    String ePassword =null;
                    if (sP.contains("Email")) eMail = sP.getString("Email", "");
                    if (sP.contains("Password")) ePassword = sP.getString("Password", "");
                    if(em.equals(eMail) && pass.equals(ePassword)){
                        session.createUserLoginSession(eMail);
                        Intent i = new  Intent(getApplicationContext(),Main.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }else if(!email.equals(eMail)){
                        Toast.makeText(getApplicationContext(), "Даний Email не зареєстрований в системі", Toast.LENGTH_SHORT).show();
                    }else if(!password.equals(ePassword)){
                        Toast.makeText(getApplicationContext(), "Неправильний пароль", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Неправильний email чи пароль", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Будь-ласка введіть email і пароль", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sign_up:
                i =new Intent(Sign_In.this, Sign_Up.class);
                this.startActivity(i);
                break;
            default:
                break;
        }
    }
}