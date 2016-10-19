package com.example.vandame.project_blank.entidade;

/**
 * Created by vandame on 27/09/16.
 */

public enum Sexo {
    FEMININO, MASCULINO;

    public static Sexo getSexo(int pos){
        for(Sexo sexo : Sexo.values()){
            if(sexo.ordinal() == pos){
                return sexo;
            }
        }

        return null;
    }
}
