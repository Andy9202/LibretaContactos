package com.seminarios.actividades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seminarios.interfaces.OperacionesContactos;
import com.seminarios.principal.MainActivity;
import com.seminarios.utilitarios.Patrones;
import com.seminarios.utilitarios.TBLContactosManager;

/**
 * @author Grupo SQLite
 */
public class NuevoContactoActivity extends Activity {

    private static OperacionesContactos db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.nuevo_activity);

		db = new TBLContactosManager(this);
	}

	public void onClickRegresar(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void onClickGuardar(View v) {
		EditText etNombre = (EditText) findViewById(R.id.txtNombre);
		EditText etEmail = (EditText) findViewById(R.id.txtEmail);
		EditText etFono = (EditText) findViewById(R.id.txtFono);

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
			Pattern patternFono = Pattern.compile(Patrones.PATTERN_FONO);
			Matcher matcherFono = patternFono.matcher(fono);

			if (!matcherFono.matches()) {
				Toast msg = Toast.makeText(this, "Movil acepta solo numeros",
						Toast.LENGTH_LONG);
				msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				msg.show();
				return;
			}

			if (fono.length() < 8 || fono.length() > 10) {
				Toast msg = Toast
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

		Toast msg = null;

		if (count == 0) {
			db.open();
			db.insertarContacto(nombre, email, fono);
			db.close();

			msg = Toast.makeText(this, "Contacto agregado correctamente",
					Toast.LENGTH_LONG);
		} else {
			msg = Toast.makeText(this,
					"Ya existe un contacto con el mismo email",
					Toast.LENGTH_LONG);
		}
		
		msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		msg.show();
	}

	public void onClickLimpiar(View v) {
		EditText etNombre = (EditText) findViewById(R.id.txtNombre);
		EditText etEmail = (EditText) findViewById(R.id.txtEmail);
		EditText etFono = (EditText) findViewById(R.id.txtFono);

		etNombre.setText("");
		etEmail.setText("");
		etFono.setText("");

		etNombre.requestFocus();
	}

}
