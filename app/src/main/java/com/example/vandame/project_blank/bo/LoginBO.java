package com.example.vandame.project_blank.bo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.vandame.project_blank.repository.LoginRepository;
import com.example.vandame.project_blank.util.Util;
import com.example.vandame.project_blank.validation.LoginValidation;

/**
 * Created by vandame on 19/09/16.
 */
public class LoginBO {

    private LoginRepository loginRepository;

    public LoginBO(Activity activity){
        loginRepository = new LoginRepository(activity);
        loginRepository.popularBD();
    }

    public boolean validarCamposLogin(LoginValidation validation) {
        boolean resultado = true;

        if (validation.getLogin() == null || "".equals(validation.getLogin())) {
            validation.getEditLogin().setError("Campo Obrigatório");
            resultado = false;
        }

        if (validation.getSenha() == null || "".equals(validation.getSenha())) {
            validation.getEditSenha().setError("Campo Obrigatório");
            resultado = false;
        }

        if (resultado) {
            if (!validation.getLogin().equals("admin") || !validation.getSenha().equals("admin")) {
                Util.showMsgToast(validation.getActivity(), "Login/Senha inválidos!");
                resultado = false;
            }
        } else {
            //Grava em memória os dados do login
            SharedPreferences.Editor editor = validation.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE).edit();
            editor.putString("login", validation.getLogin());
            editor.putString("senha", validation.getSenha());
            editor.commit();
        }

        return resultado;
    }

    public void desLogar(){

    }

}

