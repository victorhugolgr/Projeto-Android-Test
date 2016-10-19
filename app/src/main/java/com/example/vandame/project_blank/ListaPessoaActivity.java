package com.example.vandame.project_blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vandame.project_blank.entidade.Pessoa;
import com.example.vandame.project_blank.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView lstPessoa;

    private PessoaRepository pessoaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);

        pessoaRepository = new PessoaRepository(this);

        lstPessoa = (ListView) findViewById(R.id.lstPessoa);

        List<Pessoa> lista = pessoaRepository.listarPessoa();

        List<String> valores = new ArrayList<>();
        for (Pessoa pessoa : lista) {
            valores.add(pessoa.getNome());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, valores);

        lstPessoa.setAdapter(adapter);
    }
}
