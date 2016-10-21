package com.example.vandame.project_blank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vandame.project_blank.bo.LoginBO;
import com.example.vandame.project_blank.validation.LoginValidation;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogar;

    private SharedPreferences preferences;

    private LoginBO loginBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBO = new LoginBO(this);

        /**
         * Captura as variáveis salvas em memória. Se a variável já existir na memória então redireciona
         * para a página de login direto.
         */
        preferences = getSharedPreferences("pref",Context.MODE_PRIVATE);

        String login = preferences.getString("login", null);
        String senha = preferences.getString("senha", null);
        if (login != null && senha != null) {
            Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
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

                LoginValidation loginValidation = new LoginValidation();
                loginValidation.setActivity(LoginActivity.this);
                loginValidation.setEditLogin(edtLogin);
                loginValidation.setEditSenha(edtSenha);
                loginValidation.setLogin(login);
                loginValidation.setSenha(senha);

                boolean isValido = loginBO.validarCamposLogin(loginValidation);

                if (isValido) {
                    //Navegação entre as Activity
                    Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
    }

}
