package com.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.R;
import com.test.util.UserSession;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class Account extends AppCompatActivity {
    ImageView acc;
    TextView account, password, al_1, al_2, al_3;
    HtmlTextView al_4;
    SharedPreferences sP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);
        sP = getSharedPreferences("REGISTR", MODE_PRIVATE);
        acc = findViewById(R.id.al_im);
        acc.setImageResource(R.drawable.account);
        account=findViewById(R.id.al_release);
        password=findViewById(R.id.al_stars);
        al_1 =findViewById(R.id.al_name);
        al_1.setVisibility(View.GONE);
        al_2 =findViewById(R.id.al_pop);
        al_2.setVisibility(View.GONE);
        al_3 =findViewById(R.id.al_lang);
        al_3.setVisibility(View.GONE);
        al_4 =findViewById(R.id.al_info);
        al_4.setVisibility(View.GONE);
        password.setVisibility(View.VISIBLE);
        account.setText("Ваш Email: "+sP.getString("Email",""));
        password.setText("Ваш пароль: "+sP.getString("Password",""));
    }
}