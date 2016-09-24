package com.example.vandame.project_blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);

        List<String> profissoes = new ArrayList<>();
        profissoes.add("Analista de Sistemas");
        profissoes.add("Médico");
        profissoes.add("Professor");
        profissoes.add("Pedagoga");

        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);

    }
}
