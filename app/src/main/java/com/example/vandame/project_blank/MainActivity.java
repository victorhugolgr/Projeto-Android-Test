package com.example.vandame.project_blank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vandame.project_blank.util.TipoMsg;
import com.example.vandame.project_blank.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Manipulação da action bar
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Bem vindo, Victor");


    }

    public void clickBtnEnviar(View view) {
        Util.showMsgAlertOK(MainActivity.this, "Titulo", "Minha mensagem", TipoMsg.SUCESSO);
    }
}
