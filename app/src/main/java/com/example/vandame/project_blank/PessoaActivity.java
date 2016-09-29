package com.example.vandame.project_blank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vandame.project_blank.entidade.Pessoa;
import com.example.vandame.project_blank.entidade.Profissao;
import com.example.vandame.project_blank.entidade.Sexo;
import com.example.vandame.project_blank.entidade.TipoPessoa;
import com.example.vandame.project_blank.fragment.DatePickerFragment;
import com.example.vandame.project_blank.util.Mask;
import com.example.vandame.project_blank.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PessoaActivity extends AppCompatActivity {
    private Spinner spnProfissao;

    private TextView txtCpfCnpj;

    private EditText edtNome;
    private EditText edtEndereco;
    private EditText edtCpfCnpj;
    private EditText edtNasc;

    private RadioGroup rbgCpfCnpj;
    private RadioGroup rbgSexo;

    private RadioButton rbtCpf;

    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    private int cpfCpnfSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        txtCpfCnpj = (TextView) findViewById(R.id.txtCpfCnpj);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        edtNasc = (EditText) findViewById(R.id.edtNasc);
        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtNome = (EditText) findViewById(R.id.edt_endereco);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);

        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbgSexo = (RadioGroup) findViewById(R.id.rbgSexo);

        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);

        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);

        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.setText("");
                edtCpfCnpj.requestFocus();
                cpfCpnfSelecionado = group.getCheckedRadioButtonId();
                if (cpfCpnfSelecionado == rbtCpf.getId()) {
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                    txtCpfCnpj.setText("CPF:");
                } else {
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                    txtCpfCnpj.setText("CPNJ:");
                }
            }
        });

        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (rbgCpfCnpj.getCheckedRadioButtonId() == rbtCpf.getId()) {
                    if (edtCpfCnpj.getText().length() < 14) {
                        edtCpfCnpj.setText("");
                    }
                } else {
                    if (edtCpfCnpj.getText().length() < 18) {
                        edtCpfCnpj.setText("");
                    }
                }
            }
        });

        this.initProfissoes();
    }

    public void setData(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        Calendar cal = Calendar.getInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("dia", cal.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes", cal.get(Calendar.MONTH));
        bundle.putInt("ano", cal.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);
        datePickerFragment.setDateListener(dateListener);
        datePickerFragment.show(getFragmentManager(), "Data Nasc.");
    }


    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtNasc.setText(dayOfMonth + "/" + (monthOfYear + 1)  + "/" + year);
            Util.showMsgToast(PessoaActivity.this, "Ano: " + year + ", Mes: " + (monthOfYear + 1) + ", Dia: " + dayOfMonth);
        }
    };

    private void initProfissoes() {
        ArrayList<String> profissoes = new ArrayList<>();
        for (Profissao p : Profissao.values()) {
            profissoes.add(p.getDescricao());
        }
        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }

    public void enviarPessoa(View view){
        montarPessoa();
    }

    public void montarPessoa(){
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEnderco(edtEndereco.getText().toString());
        pessoa.setCpfCnpj(edtCpfCnpj.getText().toString());
        switch (rbgCpfCnpj.getCheckedRadioButtonId()){
            case R.id.rbtCpf:
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                break;
            case R.id.rbtCnpj:
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                break;
        }

        switch (rbgSexo.getCheckedRadioButtonId()){
            case R.id.rbtMasc:
                pessoa.setSexo(Sexo.MASCULINO);
                break;
            case R.id.rbtFem:
                pessoa.setSexo(Sexo.FEMININO);
                break;
        }

        pessoa.setProfissao(Profissao.getProfissao(spnProfissao.getSelectedItemPosition()));


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date nasc = dateFormat.parse(edtNasc.getText().toString());
            pessoa.setDtNasc(nasc);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Util.showMsgToast(this, pessoa.toString());
    }
}
