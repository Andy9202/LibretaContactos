package com.seminarios.utilitarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * En esta clase se crea o actualiza la base de datos
 *
 * @author Grupo SQLite
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "agenda";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TBLContactosManager.SQL_CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        //Codigo solo de ejemplo, ya que si se modifica el esquema
        //posiblemente solo se daba actualizar la tabla
        db.execSQL("DROP TABLE IF EXISTS contactos");
        onCreate(db);
    }
}
