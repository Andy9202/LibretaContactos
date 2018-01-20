package com.seminarios.utilitarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.seminarios.interfaces.OperacionesContactos;


/**
 * En esta clase se manejan los metodos CRUD de la interface OperacionesContactos
 * 
 * @author Grupo SQLite
 * 
 */
public class TBLContactosManager implements OperacionesContactos{

	public static final String SQL_CREATE_CONTACTS = "create table " + TABLE_CONTACTS_NAME + "(" +
            KEY_ROWID + " integer primary key autoincrement," +
			KEY_NANE + " text not null," +
            KEY_EMAIL + " text not null," +
            KEY_FONO + " text);";

	private static final String[] FIELDS_TABLE = { KEY_ROWID, KEY_NANE, KEY_EMAIL, KEY_FONO };

    private DBHelper dbHelper;

    /**
     * SQLiteDatabase permite manipular la base de datos
     */
    private SQLiteDatabase db;

	public TBLContactosManager(Context context) {
		dbHelper = new DBHelper(context);
	}

	public OperacionesContactos open() {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

    @Override
	public void borrarContactos() {
		db.execSQL("DELETE FROM contactos");
	}

    @Override
	public long insertarContacto(String nombre, String email, String fono) {
		ContentValues values = new ContentValues();

		values.put(KEY_NANE, nombre);
		values.put(KEY_EMAIL, email);
		values.put(KEY_FONO, fono);

		return db.insert(TABLE_CONTACTS_NAME, KEY_FONO, values);
	}

    @Override
	public Cursor obtenerTodosContactos() {
		return db.query(TABLE_CONTACTS_NAME, FIELDS_TABLE, null, null, null, null,
				null);
	}

    @Override
	public Cursor obtenerContacto(String email) throws SQLException {
		Cursor cursor = null;
		cursor = db.query(true, TABLE_CONTACTS_NAME, FIELDS_TABLE, KEY_EMAIL + "='"
				+ email + "'", null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

    @Override
	public boolean actualizarContacto(Integer id, String nombre, String email,
			String fono) {
		ContentValues values = new ContentValues();

		values.put(KEY_NANE, nombre);
		values.put(KEY_EMAIL, email);
		values.put(KEY_FONO, fono);

		return db.update(TABLE_CONTACTS_NAME, values, KEY_ROWID + "=" + id, null) > 0;
	}

    @Override
	public boolean borrarContacto(Integer id) {
		return db.delete(TABLE_CONTACTS_NAME, KEY_ROWID + "=" + id, null) > 0;
	}
}
