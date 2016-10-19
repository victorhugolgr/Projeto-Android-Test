package com.example.vandame.project_blank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void callCadastro(View view){}

    public void callList(View view){
        Intent i = new Intent(this, ListaPessoaActivity.class);
        startActivity(i);
    }

}
