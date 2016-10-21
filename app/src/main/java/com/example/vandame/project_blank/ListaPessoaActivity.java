package com.example.vandame.project_blank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vandame.project_blank.entidade.Pessoa;
import com.example.vandame.project_blank.repository.PessoaRepository;
import com.example.vandame.project_blank.util.TipoMsg;
import com.example.vandame.project_blank.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView lstPessoa;

    private PessoaRepository pessoaRepository;

    private List<Pessoa> listaPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
        getSupportActionBar().setTitle("Lista de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pessoaRepository = new PessoaRepository(this);

        lstPessoa = (ListView) findViewById(R.id.lstPessoa);

        listaPessoa = pessoaRepository.listarPessoa();

        List<String> valores = new ArrayList<>();
        for (Pessoa pessoa : listaPessoa) {
            valores.add(pessoa.getNome());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, valores);

        lstPessoa.setAdapter(adapter);
        lstPessoa.setOnItemClickListener(clickListenerPessoas);
    }

    private AdapterView.OnItemClickListener clickListenerPessoas = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = listaPessoa.get(position);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder info = new StringBuilder();
            info.append("Nome: " + pessoa.getNome());
            info.append("\nEndereço: " + pessoa.getEnderco());
            info.append("\nCPF/CNPJ: " + pessoa.getCpfCnpj());
            info.append("\nSexo: " + pessoa.getSexo().getDescricao());
            info.append("\nProfissão: " + pessoa.getProfissao().getDescricao());
            info.append("\nDt Nasc: " + dateFormat.format(pessoa.getDtNasc()));

            Util.showMsgAlertOK(ListaPessoaActivity.this, "Info", info.toString(), TipoMsg.INFO);

        }
    };

    public void addNewPessoa(View view){
        Intent i = new Intent(this, PessoaActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
