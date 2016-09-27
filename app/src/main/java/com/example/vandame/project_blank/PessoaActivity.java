package com.example.vandame.project_blank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.vandame.project_blank.entidade.Profissao;
import com.example.vandame.project_blank.util.Mask;
import com.example.vandame.project_blank.util.Util;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    private EditText editCpfCnpj;

    private RadioGroup rbgCpfCnpj;

    private RadioButton rbtCpf;

    private TextWatcher cpfMask;

    private TextWatcher cnpjMask;

    private int cpfCnpjSelecionado;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
                editCpfCnpj.setText("");
                editCpfCnpj.requestFocus();
                cpfCnpjSelecionado = group.getCheckedRadioButtonId();
                if (cpfCnpjSelecionado == rbtCpf.getId()) {
                    editCpfCnpj.removeTextChangedListener(cnpjMask);
                    editCpfCnpj.addTextChangedListener(cpfMask);
                } else {
                    editCpfCnpj.removeTextChangedListener(cpfMask);
                    editCpfCnpj.addTextChangedListener(cnpjMask);

                }
            }
        });

        editCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (rbgCpfCnpj.getCheckedRadioButtonId() == rbtCpf.getId()) {
                    if (editCpfCnpj.getText().length() < 14) {
                        editCpfCnpj.setText("");
                    } else {
                        if (editCpfCnpj.getText().length() < 18) {
                            editCpfCnpj.setText("");
                        }
                    }
                }
            }
        });

        initProfissoes();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initProfissoes() {

        List<String> profissoes = new ArrayList<>();

        for (Profissao p : Profissao.values()) {
            profissoes.add(p.getDescricao());
        }

        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }

    public void setData(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(this, dateListenne, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener dateListenne = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Util.showMsgToast(PessoaActivity.this, "Ano: " + year + " Mês: " + month);
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Pessoa Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
