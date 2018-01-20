package com.seminarios.interfaces;

import android.database.Cursor;
import android.database.SQLException;

/**
 * @author Grupo SQLite
 */
public interface OperacionesContactos {

    public static final String TABLE_CONTACTS_NAME = "contactos";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NANE = "nombre";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FONO = "fono";

    public OperacionesContactos open();

    public void close();

    /**
     * Se limpia toda la tabla contactos
     */
    public void borrarContactos();

    /**
     * Se inserta un registro nuevo, el id es autoincremental
     *
     * @param nombre
     * @param email
     * @param fono
     * @return
     */
    public long insertarContacto(String nombre, String email, String fono);

    /**
     * Se obtiene un objeto de tipo Cursor que contiene los datos de todos los registros
     *
     * @return
     */
    public Cursor obtenerTodosContactos();

    /**
     * Se obtiene un objeto de tipo Cursor que contiene los datos de un registro
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public Cursor obtenerContacto(String email) throws SQLException;

    /**
     * Se actualiza el registro
     *
     * @param id
     * @param nombre
     * @param email
     * @param fono
     * @return
     */
    public boolean actualizarContacto(Integer id, String nombre, String email, String fono);

    /**
     * Se borra fisicamente el registro
     *
     * @param id
     * @return
     */
    public boolean borrarContacto(Integer id);

}
