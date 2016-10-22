package com.example.vandame.project_blank.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vandame.project_blank.entidade.Pessoa;
import com.example.vandame.project_blank.entidade.Profissao;
import com.example.vandame.project_blank.entidade.Sexo;
import com.example.vandame.project_blank.entidade.TipoPessoa;
import com.example.vandame.project_blank.util.Constantes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by vandame on 27/09/16.
 */

public class PessoaRepository extends SQLiteOpenHelper {

    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append(" NOME TEXT(30) NOT NULL, ");
        query.append(" ENDERECO TEXT(50), ");
        query.append(" CPF TEXT(14), ");
        query.append(" CNPJ TEXT(18), ");
        query.append(" SEXO INTERGER(1) NOT NULL, ");
        query.append(" PROFISSAO INTERGER(3) NOT NULL, ");
        query.append(" DT_NASC INTERGER, NOT NULL)");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Teste de update", "onUpgrade: passou aqui");
    }

    private void setPessoaFromCursor(Cursor cursor, Pessoa pessoa) {
        pessoa.setIdPessoa(cursor.getInt(cursor.getColumnIndex("ID_PESSOA")));
        pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        pessoa.setEnderco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
        String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
        String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
        if (cpf != null) {
            pessoa.setTipoPessoa(TipoPessoa.FISICA);
            pessoa.setCpfCnpj(cpf);
        } else {
            pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
            pessoa.setCpfCnpj(cnpj);
        }

        int sexo =  cursor.getInt(cursor.getColumnIndex("SEXO"));
        pessoa.setSexo(Sexo.getSexo(sexo));

        int profissao =  cursor.getInt(cursor.getColumnIndex("PROFISSAO"));
        pessoa.setProfissao(Profissao.getProfissao(profissao));

        long time =  cursor.getLong(cursor.getColumnIndex("DT_NASC"));
        Date dtNasd = new Date();
        dtNasd.setTime(time);
        pessoa.setDtNasc(dtNasd);
    }


    public void salvarPessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", pessoa.getNome());
        contentValues.put("ENDERECO", pessoa.getEnderco());

        switch (pessoa.getTipoPessoa()) {
            case FISICA:
                contentValues.put("CPF", pessoa.getCpfCnpj());
                break;
            case JURIDICA:
                contentValues.put("CNPJ", pessoa.getCpfCnpj());
                break;
        }

        contentValues.put("SEXO", pessoa.getSexo().ordinal());
        contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
        contentValues.put("DT_NASC", pessoa.getDtNasc().getTime());

        db.insert("TB_PESSOA", null, contentValues);
    }

    public List<Pessoa> listarPessoa() {
        List<Pessoa> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA", null, null, null, null, null, "NOME");

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            setPessoaFromCursor(cursor, pessoa);

            lista.add(pessoa);
        }

        return lista;
    }

    public Pessoa consultarPessoaPorId(int idPessoa){
        Pessoa pessoa = new Pessoa();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("TB_PESSOA", null, "WHERE ID_PESSOA = ?", new String[]{String.valueOf(idPessoa)}, null, null, "NOME");

        if(cursor.moveToNext()){
            setPessoaFromCursor(cursor, pessoa);
        }

        return pessoa;
    }

    public void removerPessoaPorId(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("TB_PESSOA", "ID_PESSOA = ?", new String[]{String.valueOf(id)});
    }
}
