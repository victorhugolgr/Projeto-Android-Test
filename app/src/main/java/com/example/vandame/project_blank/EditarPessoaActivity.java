package com.example.vandame.project_blank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.MenuItem;
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
import com.example.vandame.project_blank.repository.PessoaRepository;
import com.example.vandame.project_blank.util.Mask;
import com.example.vandame.project_blank.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditarPessoaActivity extends AppCompatActivity {

    private Pessoa pessoa;

    private Spinner spnProfissao;

    private TextView txtCpfCnpj;

    private EditText edtNome;
    private EditText edtEndereco;
    private EditText edtCpfCnpj;
    private EditText edtNasc;
    private RadioGroup rbgCpfCnpj;
    private RadioGroup rbgSexo;
    private RadioButton rbtCpf;
    private RadioButton rbtCnpj;
    private RadioButton rbtMasc;
    private RadioButton rbtFem;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    private int cpfCpnfSelecionado;

    private PessoaRepository pessoaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pessoa);

        getSupportActionBar().setTitle("Editar Pessoa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pessoa = (Pessoa) getIntent().getExtras().getSerializable("pessoa");


        txtCpfCnpj = (TextView) findViewById(R.id.txtCpfCnpj);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        edtNasc = (EditText) findViewById(R.id.edtNasc);
        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtEndereco = (EditText) findViewById(R.id.edt_endereco);
        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbgSexo = (RadioGroup) findViewById(R.id.rbgSexo);
        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);
        rbtCnpj = (RadioButton) findViewById(R.id.rbtCnpj);
        rbtMasc = (RadioButton) findViewById(R.id.rbtMasc);
        rbtFem = (RadioButton) findViewById(R.id.rbtFem);


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

    private void initCampos(){
        edtNome.setText(pessoa.getNome());
        edtEndereco.setText(pessoa.getEnderco());
        edtCpfCnpj.setText(pessoa.getCpfCnpj());
        switch (pessoa.getTipoPessoa()){
            case FISICA:
                txtCpfCnpj.setText("CPF:");
                rbtCpf.setChecked(true);
                break;
            case JURIDICA:
                txtCpfCnpj.setText("CNPJ:");
                rbtCnpj.setChecked(true);
                break;
        }

        switch (pessoa.getSexo()){
            case MASCULINO:
                rbtMasc.setChecked(true);
                break;
            case FEMININO:
                rbtFem.setChecked(true);
                break;

        }

        spnProfissao.setSelection(pessoa.getProfissao().ordinal());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edtNasc.setText(dateFormat.format(pessoa.getDtNasc()));
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
            edtNasc.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            Util.showMsgToast(EditarPessoaActivity.this, "Ano: " + year + ", Mes: " + (monthOfYear + 1) + ", Dia: " + dayOfMonth);
        }
    };

    private void initProfissoes() {
        ArrayList<String> profissoes = new ArrayList<>();
        for (Profissao p : Profissao.values()) {
            profissoes.add(p.getDescricao());
        }
        ArrayAdapter adapter = new ArrayAdapter(EditarPessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }

    public void enviarPessoa(View view) {
        Pessoa p = montarPessoa();
        if (!validarPessoa(p)) {
            pessoaRepository.salvarPessoa(p);
            Intent i = new Intent(this, ListaPessoaActivity.class);
            startActivity(i);
            finish();
        }

    }

    private boolean validarPessoa(Pessoa pessoa) {
        boolean erro = false;
        if (pessoa.getNome() == null || pessoa.getNome() == "") {
            erro = true;
            edtNome.setError("Campo Nome obrigatório");
        }
        if (pessoa.getEnderco() == null || "".equals(pessoa.getEnderco())) {
            erro = true;
            edtEndereco.setError("Campo Endereço obrigatório");
        }
        if (pessoa.getCpfCnpj() == null || "".equals(pessoa.getCpfCnpj())) {
            erro = true;
            switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
                case R.id.rbtCpf:
                    edtCpfCnpj.setError("Campo CPF obrigatório");
                    break;
                case R.id.rbtCnpj:
                    edtCpfCnpj.setError("Campo CNPJ obrigatório");
                    break;
            }
        } else {
            switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
                case R.id.rbtCpf:
                    if (edtCpfCnpj.getText().length() < 14 && rbgCpfCnpj.getCheckedRadioButtonId() == R.id.rbtCpf) {
                        erro = true;
                        edtCpfCnpj.setError("Campo CPF deve ter 11 caracteres");
                        break;
                    }
                case R.id.rbtCnpj:
                    if (edtCpfCnpj.getText().length() < 18 && rbgCpfCnpj.getCheckedRadioButtonId() == R.id.rbtCnpj) {
                        erro = true;
                        edtCpfCnpj.setError("Campo CNPJ deve ter 18 caracteres");
                        break;
                    }
            }
        }

        if (pessoa.getDtNasc() == null) {
            erro = true;
            edtNasc.setError("Campo Data Nascimento obrigatório");
        }

        return erro;
    }

    public Pessoa montarPessoa() {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEnderco(edtEndereco.getText().toString());
        pessoa.setCpfCnpj(edtCpfCnpj.getText().toString());
        switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
            case R.id.rbtCpf:
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                break;
            case R.id.rbtCnpj:
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                break;
        }

        switch (rbgSexo.getCheckedRadioButtonId()) {
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

        return pessoa;
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
