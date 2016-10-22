package com.example.vandame.project_blank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
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

    private int posicaoSelecionada;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
        getSupportActionBar().setTitle("Lista de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pessoaRepository = new PessoaRepository(this);

        lstPessoa = (ListView) findViewById(R.id.lstPessoa);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        setArrayAdapterPessoa();
        lstPessoa.setOnItemClickListener(clickListenerPessoas);

        lstPessoa.setOnCreateContextMenuListener(contextMenuListener);

        lstPessoa.setOnItemLongClickListener(longClickListener);
    }

    private void setArrayAdapterPessoa() {
        listaPessoa = pessoaRepository.listarPessoa();

        List<String> valores = new ArrayList<>();
        for (Pessoa pessoa : listaPessoa) {
            valores.add(pessoa.getNome());
        }

        adapter.clear();
        adapter.addAll(valores);
        lstPessoa.setAdapter(adapter);
    }

    //adiciona as opções ao menu de contexto
    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções").setHeaderIcon(R.drawable.edit).add(1, 10, 1, "Editar");
            menu.add(1, 20, 2, "Deletar").setIcon(R.drawable.delete);
        }
    };

    //Observa a seleção da lista e seta a posição
    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    /*
     *Clique simples em cima da lista
     */
    private AdapterView.OnItemClickListener clickListenerPessoas = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = pessoaRepository.consultarPessoaPorId(listaPessoa.get(position).getIdPessoa());

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

    //ações ao clicar no menu de contexto
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 10:
                Util.showMsgToast(this, "Editar");
                break;

            case 20:
                Util.showMsgConfirm(ListaPessoaActivity.this, "Remover Pessoa", "Deseja realmente remover essa pessoa?", TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int idPessoa = listaPessoa.get(posicaoSelecionada).getIdPessoa();
                        pessoaRepository.removerPessoaPorId(idPessoa);
                        setArrayAdapterPessoa();
                        adapter.notifyDataSetChanged();//refresh na lista
                    }
                });

                break;
        }
        return super.onContextItemSelected(item);
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
