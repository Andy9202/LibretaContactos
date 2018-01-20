package com.seminarios.actividades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seminarios.interfaces.OperacionesContactos;
import com.seminarios.utilitarios.Patrones;
import com.seminarios.utilitarios.TBLContactosManager;
import com.seminarios.dialogos.DialogoEliminarContacto;

/**
 * @author Grupo SQLite
 */
public class ActualizarContactoActivity extends Activity {

    private static OperacionesContactos db;
	int idContacto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actualizar_activity);

		db = new TBLContactosManager(this);

		db.open();
		Cursor c = db.obtenerContacto(getIntent().getStringExtra("email"));
		db.close();

		EditText etNombre = (EditText) findViewById(R.id.txtEditNombre);
		EditText etEmail = (EditText) findViewById(R.id.txtEditEmail);
		EditText etFono = (EditText) findViewById(R.id.txtEditFono);

		if (c != null) {
			idContacto = c.getInt(0);
			etNombre.setText(c.getString(1));
			etEmail.setText(c.getString(2));
			etFono.setText(c.getString(3));

		} else {
			idContacto = 0;
			etNombre.setText("");
			etEmail.setText("");
			etFono.setText("");

			Toast msg = Toast
					.makeText(
							this,
							"No hay contacto registrado con los datos del filtro anterior",
							Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
		}
	}

	public void onClickRegresar(View v) {
		Intent intent = new Intent(this, ContactosActivity.class);
		startActivity(intent);
		finish();
	}

	public void onClickActualizar(View v) {
		EditText etNombre = (EditText) findViewById(R.id.txtEditNombre);
		EditText etEmail = (EditText) findViewById(R.id.txtEditEmail);
		EditText etFono = (EditText) findViewById(R.id.txtEditFono);

		String nombre = etNombre.getText().toString();
		String email = etEmail.getText().toString();
		String fono = etFono.getText().toString();

		if (nombre == null || nombre.equals("")) {
			Toast msg = Toast.makeText(this,
					"Los campos con asteristicos son obligatorios",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
			return;
		}

		Pattern patternEmail = Pattern.compile(Patrones.PATTERN_EMAIL);
		Matcher matcherEmail = patternEmail.matcher(email);

		if (!matcherEmail.matches()) {
			Toast msg = Toast.makeText(this, "Correo electronico incorrecto",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
			return;
		}

		if (!fono.equals("")) {
			Toast msg = null;
			Pattern patternFono = Pattern.compile(Patrones.PATTERN_FONO);
			Matcher matcherFono = patternFono.matcher(fono);

			if (!matcherFono.matches()) {
				msg = Toast.makeText(this, "Movil acepta solo numeros",
						Toast.LENGTH_LONG);
				msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				msg.show();
				return;
			}

			if (fono.length() < 8 || fono.length() > 10) {
				msg = Toast
						.makeText(this, "Movil debe tener de 8 a 10 numeros",
								Toast.LENGTH_LONG);
				msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				msg.show();
				return;
			}
		}

		db.open();

		Cursor c = null;

		try {
			c = db.obtenerContacto(email);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		int count = c.getCount();

		if (count > 0) {
			if (idContacto != c.getInt(0)) {
				Toast msg = Toast.makeText(this,
						"El email actual ya pertenece a otro contacto",
						Toast.LENGTH_LONG);
				msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				msg.show();
				return;
			}
		}

		boolean actualizado = false;

		db.open();
		actualizado = db.actualizarContacto(idContacto, nombre, email, fono);
		db.close();

		Toast msg = null;
		if (actualizado) {
			msg = Toast.makeText(this, "Contacto actualizado correctamente",
					Toast.LENGTH_LONG);
		} else {
			msg = Toast.makeText(this, "No se pudieron guardar los cambios",
					Toast.LENGTH_LONG);
		}
		msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		msg.show();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onClickEliminar(View v) {
		DialogoEliminarContacto dialogo = DialogoEliminarContacto.nuevo(
				"Desea eliminar el contacto?", this, idContacto);
        dialogo.show(getFragmentManager(),"dialog");
	}

    /**
     * Este metodo es llamado en el boton Aceptar de DialogoEliminarContacto
     *
     * @param eliminado
     */
    public void validarEliminacion(boolean eliminado) {
        if (eliminado) {
            Intent intent = new Intent(this, ContactosActivity.class);
            intent.putExtra("contactoEliminado", eliminado);
            startActivity(intent);
            finish();
        } else {
            Toast msg = Toast.makeText(this, "No se pudo borrar el contacto",
                    Toast.LENGTH_LONG);
            msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            msg.show();
        }
    }

}
