package tac.com.appandroidtac.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tac.com.appandroidtac.model.Usuario;

/**
 * Created by diego on 02/03/2017.
 */

public class ConexaoSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "postosGyn";
    private static final String TAB_USUARIO = "usuario";
    private static final String ID = "id";
    private static final String NOME_APELIDO = "nomeApelido";
    private static final String SENHA = "senha";

    public ConexaoSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TAB_USUARIO =
                "CREATE TABLE " + TAB_USUARIO + "(" + ID + " LONG PRIMARY KEY,"
                + NOME_APELIDO + " TEXT," + SENHA + " TEXT" + ")";
        db.execSQL(CREATE_TAB_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TAB_USUARIO);
        onCreate(db);
    }

    public void cadastrarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_APELIDO, usuario.getNomeApelido());
        values.put(SENHA, usuario.getSenha());
        db.insert(TAB_USUARIO, null, values);
        db.close();
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> contactList = new ArrayList<Usuario>();
        String selectQuery = "SELECT * FROM " + TAB_USUARIO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Usuario contact = new Usuario();
                contact.setId(Long.parseLong(cursor.getString(0)));
                contact.setNomeApelido(cursor.getString(1));
                contact.setSenha(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
