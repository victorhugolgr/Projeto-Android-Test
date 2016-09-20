package com.example.vandame.project_blank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    //Adciona o menu na activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sair:
                SharedPreferences.Editor edit = getSharedPreferences("pref",Context.MODE_PRIVATE).edit();
                edit.remove("login");
                edit.remove("senha");
                edit.commit();

                finish();
                Intent i  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método que será executado ao clicar no botão enviar.
    public void clickBtnEnviar(View view) {
        Util.showMsgAlertOK(MainActivity.this, "Titulo", "Minha mensagem", TipoMsg.SUCESSO);
    }
}
