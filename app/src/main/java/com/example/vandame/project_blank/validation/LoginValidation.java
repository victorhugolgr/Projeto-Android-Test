package com.example.vandame.project_blank.validation;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by vandame on 19/09/16.
 */
public class LoginValidation {
    private String login;

    private String senha;

    private EditText editLogin;

    private EditText editSenha;

    private Activity activity;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EditText getEditLogin() {
        return editLogin;
    }

    public void setEditLogin(EditText editLogin) {
        this.editLogin = editLogin;
    }

    public EditText getEditSenha() {
        return editSenha;
    }

    public void setEditSenha(EditText editSenha) {
        this.editSenha = editSenha;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
