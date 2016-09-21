package com.example.vandame.project_blank.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vandame.project_blank.util.Constantes;

/**
 * Created by vandame on 20/09/16.
 */
public class LoginRepository extends SQLiteOpenHelper{


    public LoginRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_LOGIN( ");
        query.append(" ID_LOGIN INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append(" USUARIO TEXT(15) NOT NULL, ");
        query.append(" SENHA TEXT(15) NOT NULL) ");

        db.execSQL(query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void popularBD(){
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO TB_LOGIN(USUARIO, SENHA) VALUES(?, ?)");

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query.toString(), new String[]{"admin", "admin"});
    }
}
