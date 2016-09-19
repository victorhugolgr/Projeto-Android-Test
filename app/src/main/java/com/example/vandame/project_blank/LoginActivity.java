package com.example.vandame.project_blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vandame.project_blank.util.Util;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edit_login);
        edtSenha = (EditText) findViewById(R.id.edit_senha);
        btnLogar = (Button) findViewById(R.id.btn_logar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                validarCamposLogin(login,senha);
            }
        });
    }

    private boolean validarCamposLogin(String login, String senha){
        boolean resultado = true;

        if(login == null || "".equals(login)){
            edtLogin.setError("Campo Obrigatório");
            resultado = false;
        }

        if(senha == null || "".equals(senha)){
            edtSenha.setError("Campo Obrigatório");
            resultado = false;
        }

        return  resultado;
    }
}
