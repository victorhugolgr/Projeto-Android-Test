package com.example.vandame.project_blank.entidade;

/**
 * Created by vandame on 24/09/16.
 */

public enum Profissao {
    ARQUITETO("Arquiteto de Software"),
    PEDREIRO("Pedreiro"),
    PROFESSOR("Professor"),
    DESENVOLVEDOR("Desvenvolvedor de Software"),
    ANALISTA("Analista Financeiro"),
    ENGENHEIRO("Engenheiro Mecanico"),
    ZELADOR("Zelador"),
    CARTEIRO("Carteiro");

    private Profissao(String descricao){
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao(){
        return  descricao;
    }
}
