package com.example.vandame.project_blank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vandame.project_blank.util.Util;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogar;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getPreferences(Context.MODE_PRIVATE);

        String login = preferences.getString("login", null);
        String senha = preferences.getString("senha", null);
        if (login != null && senha != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        edtLogin = (EditText) findViewById(R.id.edit_login);
        edtSenha = (EditText) findViewById(R.id.edit_senha);
        btnLogar = (Button) findViewById(R.id.btn_logar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                boolean isValido = validarCamposLogin(login, senha);

                if (isValido) {
                    //Navegação entre as Activity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
    }

    private boolean validarCamposLogin(String login, String senha) {
        boolean resultado = true;

        if (login == null || "".equals(login)) {
            edtLogin.setError("Campo Obrigatório");
            resultado = false;
        }

        if (senha == null || "".equals(senha)) {
            edtSenha.setError("Campo Obrigatório");
            resultado = false;
        }

        if (resultado) {
            if (!login.equals("admin") || !senha.equals("admin")) {
                Util.showMsgToast(LoginActivity.this, "Login/Senha inválidos!");
                resultado = false;
            }
        } else {
            //Grava em memória os dados do login
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login", login);
            editor.putString("senha", senha);
            editor.commit();
        }

        return resultado;
    }
}
