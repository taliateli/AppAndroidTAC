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

    private static final int DATABASE_VERSION = 19;
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
                "CREATE TABLE " + TAB_POSTO + "(" + ID_POSTO + " INTEGER PRIMARY KEY, " + NOME_POSTO + " TEXT NOT NULL, "
                        + LATITUDE + " REAL NULL UNIQUE, " + LONGITUDE + " REAL NOT NULL UNIQUE, " + PRECO_GASOLINA + " NUMERIC(10,4), "
                        + PRECO_GASOLINA_ADIT + " NUMERIC(10,4), " + PRECO_ETANOL + " NUMERIC(10,4), "
                        + PRECO_DIESEL + " NUMERIC(10,4), " + DT_ATUALIZACAO + " TEXT" + ")";
        db.execSQL(CREATE_TAB_POSTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 18 && newVersion == 19) {
            db.execSQL("DROP TABLE IF EXISTS " + TAB_POSTO);
        }
        onCreate(db);
    }

    public void cadastrarPosto(Posto posto) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_POSTO, posto.getNome());
        values.put(LATITUDE, posto.getLatitude());
        values.put(LONGITUDE, posto.getLongitude());
        values.put(PRECO_GASOLINA, 0.0);
        values.put(PRECO_GASOLINA_ADIT, 0.0);
        values.put(PRECO_ETANOL, 0.0);
        values.put(PRECO_DIESEL, 0.0);
        db.insert(TAB_POSTO, null, values);
        db.close();
    }

    public Posto consultaPorLatLng(double lat, double lng) throws Exception {
        Posto posto = new Posto();
        String selectQuery = "SELECT * FROM " + TAB_POSTO + " WHERE " + LATITUDE + "="
                + String.valueOf(lat) + " AND " + LONGITUDE + "=" + String.valueOf(lng);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            posto.setID(Integer.parseInt(cursor.getString(0)));
            posto.setNome(cursor.getString(1));
            posto.setLatitude(cursor.getDouble(2));
            posto.setLongitude(cursor.getDouble(3));
            posto.setPrecoGasolina(cursor.getDouble(4));
            posto.setPrecoGasolinaAditivada(cursor.getDouble(5));
            posto.setPrecoEtanol(cursor.getDouble(6));
            posto.setPrecoDiesel(cursor.getDouble(7));
        }
        return posto;
    }

    public Posto consultarPorID(int id) throws Exception {
        Posto posto = new Posto();
        String selectQuery = "SELECT * FROM " + TAB_POSTO + " WHERE " + ID_POSTO + "=" + String.valueOf(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            posto.setID(Integer.parseInt(cursor.getString(0)));
            posto.setNome(cursor.getString(1));
            posto.setLatitude(cursor.getDouble(2));
            posto.setLongitude(cursor.getDouble(3));
            posto.setPrecoGasolina(cursor.getDouble(4));
            posto.setPrecoGasolinaAditivada(cursor.getDouble(5));
            posto.setPrecoEtanol(cursor.getDouble(6));
            posto.setPrecoDiesel(cursor.getDouble(7));
        }
        return posto;
    }

    public void atualizarPrecoCombustivel(Posto posto) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_POSTO, posto.getID());
        values.put(NOME_POSTO, posto.getNome());
        values.put(LATITUDE, posto.getLatitude());
        values.put(LONGITUDE, posto.getLongitude());
        values.put(PRECO_GASOLINA, posto.getPrecoGasolina());
        values.put(PRECO_GASOLINA_ADIT, posto.getPrecoGasolinaAditivada());
        values.put(PRECO_ETANOL, posto.getPrecoEtanol());
        values.put(PRECO_DIESEL, posto.getPrecoDiesel());
        values.put(DT_ATUALIZACAO, posto.getDtAtualizacao());
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
                posto.setPrecoGasolina(cursor.getDouble(4));
                posto.setPrecoGasolinaAditivada(cursor.getDouble(5));
                posto.setPrecoEtanol(cursor.getDouble(6));
                posto.setPrecoDiesel(cursor.getDouble(7));
                posto.setDtAtualizacao(cursor.getString(8));
                postos.add(posto);
            } while (cursor.moveToNext());
        }
        return postos;
    }

}
