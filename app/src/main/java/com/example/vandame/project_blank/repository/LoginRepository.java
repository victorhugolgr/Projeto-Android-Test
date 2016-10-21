package com.example.vandame.project_blank.repository;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vandame.project_blank.util.Constantes;
import com.example.vandame.project_blank.util.Util;

import java.util.ArrayList;

/**
 * Created by vandame on 20/09/16.
 */
public class LoginRepository extends SQLiteOpenHelper {


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

        popularBD(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(14),");
        query.append(" SEXO INTEGER(1) NOT NULL,");
        query.append(" PROFISSAO INTEGER(3) NOT NULL,");
        query.append(" DT_NASC INTEGER NOT NULL)");

        db.execSQL(query.toString());
    }

    private void popularBD(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO TB_LOGIN(USUARIO, SENHA) VALUES(?, ?)");

        db.execSQL(query.toString(), new String[]{"admin", "admin"});
    }

    public void listarLogins(Activity activity) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("TB_LOGIN", null, "ID_LOGIN = ?", new String[]{"1"}, null, null, "USUARIO");


        while (cursor.moveToNext()) {

            Log.d("ID DE USUÁRIO: ", String.valueOf(cursor.getInt(0)));
            Log.d("NOME DE USUÁRIO: ", cursor.getString(cursor.getColumnIndex("USUARIO")));
            Log.d("SENHA DE USUÁRIO", cursor.getString(2));

            Util.showMsgToast(activity, "USURÁIO = " + String.valueOf(cursor.getInt(0)));
        }

    }

    public void addLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.insert("TB_LOGIN", null, contentValues);
    }

    public void updateLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.update("TB_LOGIN", contentValues, "id_login > 1", null);
    }

    public void deleteLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TB_LOGIN", "USUARIO = ? OR SENHA = ?",  new String[]{login, senha});
    }
}
