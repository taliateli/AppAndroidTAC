package tac.com.appandroidtac.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tac.com.appandroidtac.model.Posto;

/**
 * Created by diego on 02/03/2017.
 */

public class ConexaoSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "postosGyn";
    private static final String TAB_POSTO = "posto";
    private static final String ID_POSTO = "_ID";
    private static final String NOME_POSTO = "nome";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PRECO_GASOLINA = "precoGasolina";
    private static final String PRECO_GASOLINA_ADIT = "precoGasolinaAditivada";
    private static final String PRECO_ETANOL = "precoEtanol";
    private static final String PRECO_DIESEL = "precoDiesel";
    private static final String DT_ATUALIZACAO = "dtAtualizacao";

    public ConexaoSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TAB_POSTO =
                "CREATE TABLE " + TAB_POSTO + "(" + ID_POSTO + " INTEGER PRIMARY KEY, " + NOME_POSTO + " TEXT, "
                        + LATITUDE + " DOUBLE, " + LONGITUDE + " DOUBLE, " + PRECO_GASOLINA + " NUMERIC(10,4), "
                        + PRECO_GASOLINA_ADIT + " NUMERIC(10,4), " + PRECO_ETANOL + " NUMERIC(10,4), "
                        + PRECO_DIESEL + " NUMERIC(10,4), " + DT_ATUALIZACAO + " TEXT" + ")";
        db.execSQL(CREATE_TAB_POSTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 7 && newVersion == 8) {
            db.execSQL("DROP TABLE IF EXISTS " + TAB_POSTO);
        }
        onCreate(db);
    }

    public void cadastrarPosto(Posto posto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_POSTO, posto.getNome());
        values.put(LATITUDE, posto.getLatitude());
        values.put(LONGITUDE, posto.getLongitude());
        db.insert(TAB_POSTO, null, values);
        db.close();
    }

    public Posto consultaPorLatLng(double lat, double lng) {
        Posto posto = new Posto();
        String selectQuery = "SELECT * FROM " + TAB_POSTO + " WHERE " + LATITUDE + "="
                + String.valueOf(lat) + " AND " + LONGITUDE + "=" + String.valueOf(lng);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            posto.setID(Integer.parseInt(cursor.getString(0)));
            posto.setNome(cursor.getString(1));
            posto.setPrecoGasolina(cursor.getInt(4));
            posto.setPrecoGasolinaAditivada(cursor.getInt(5));
            posto.setPrecoEtanol(cursor.getInt(6));
            posto.setPrecoDiesel(cursor.getInt(7));
        }
        return posto;
    }

    public Posto consultarPorID(int id) {
        Posto posto = new Posto();
        String selectQuery = "SELECT * FROM " + TAB_POSTO + " WHERE " + ID_POSTO + "=" + String.valueOf(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            posto.setID(Integer.parseInt(cursor.getString(0)));
        }
        return posto;
    }

    public void atualizarPrecoCombustivel(Posto posto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRECO_GASOLINA, posto.getPrecoGasolina());
        values.put(PRECO_GASOLINA_ADIT, posto.getPrecoGasolinaAditivada());
        values.put(PRECO_ETANOL, posto.getPrecoEtanol());
        values.put(PRECO_DIESEL, posto.getPrecoDiesel());
        // values.put(DT_ATUALIZACAO, posto.getDtAtualizacao().toString());
        db.update(TAB_POSTO, values, ID_POSTO + " = ?",
                new String[]{String.valueOf(posto.getID())});
        db.close();
    }

    public List<Posto> getPostos() {
        List<Posto> postos = new ArrayList<Posto>();
        String selectQuery = "SELECT * FROM " + TAB_POSTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Posto posto = new Posto();
                posto.setID(Integer.parseInt(cursor.getString(0)));
                posto.setNome(cursor.getString(1));
                posto.setLatitude(cursor.getDouble(2));
                posto.setLongitude(cursor.getDouble(3));
                postos.add(posto);
            } while (cursor.moveToNext());
        }
        return postos;
    }

}
