package com.example.vandame.project_blank.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vandame on 28/09/16.
 */

public class Pessoa implements Serializable{
    private int idPessoa;
    private String nome;
    private String enderco;
    private String cpfCnpj;
    private TipoPessoa tipoPessoa;
    private Sexo sexo;
    private Date dtNasc;
    private Profissao profissao;

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnderco() {
        return enderco;
    }

    public void setEnderco(String enderco) {
        this.enderco = enderco;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", enderco='" + enderco + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                ", sexo=" + sexo +
                ", dtNasc=" + dtNasc +
                ", profissao=" + profissao +
                '}';
    }
}
