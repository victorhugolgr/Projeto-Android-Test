package com.example.vandame.project_blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Manipulação da action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Bem vindo, Victor");

        LayoutInflater inflater = getLayoutInflater();
        View lytToast = inflater.inflate(R.layout.meu_toast, (ViewGroup) findViewById(R.id.lytToast));

        TextView txtToast = (TextView) lytToast.findViewById(R.id.textToast);
        txtToast.setText("Text App v1.00");

        //Mensagem de Alerta básica
        Toast toast = new  Toast(this);
        toast.setView(lytToast);

        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.show();
    }
}
