package com.example.vandame.project_blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.vandame.project_blank.entidade.Profissao;
import com.example.vandame.project_blank.util.Mask;

import java.util.ArrayList;
import java.util.List;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    private EditText editCpfCnpj;

    private RadioGroup rbgCpfCnpj;

    private RadioButton rbtCpf;

    private TextWatcher cpfMask;

    private  TextWatcher cnpjMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        editCpfCnpj = (EditText) findViewById(R.id.editCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rgbCpfCnpj);
        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);


        cpfMask = Mask.insert("###.###.###-##", editCpfCnpj);
        editCpfCnpj.addTextChangedListener(cpfMask);

        cnpjMask = Mask.insert("##.###.###/####-##", editCpfCnpj);
        editCpfCnpj.addTextChangedListener(cpfMask);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int opcao = group.getCheckedRadioButtonId();
                if(opcao == rbtCpf.getId()){
                    editCpfCnpj.removeTextChangedListener(cnpjMask);
                    editCpfCnpj.addTextChangedListener(cpfMask);
                }else{
                    editCpfCnpj.removeTextChangedListener(cpfMask);
                    editCpfCnpj.addTextChangedListener(cnpjMask);

                }
            }
        });

        initProfissoes();

    }

    private void initProfissoes() {

        List<String> profissoes = new ArrayList<>();

        for (Profissao p : Profissao.values()){
            profissoes.add(p.getDescricao());
        }

        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }
}
