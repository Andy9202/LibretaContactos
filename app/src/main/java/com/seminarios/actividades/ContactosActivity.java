package com.seminarios.actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.seminarios.interfaces.OperacionesContactos;
import com.seminarios.principal.MainActivity;
import com.seminarios.utilitarios.Patrones;
import com.seminarios.utilitarios.TBLContactosManager;

/**
 * @author Grupo SQLite
 */
public class ContactosActivity extends Activity {

    private static OperacionesContactos db;
	List<String> nombresContactos;
	AutoCompleteTextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.contactos_activity);

		Intent iActualizarActivity = getIntent();

		// Obtenemos el valor boolean que se envia desde la actividad
		// ActualizarContactoActivity, si no hay valor enviado por defecto sera
		// false
		boolean contactoEliminado = iActualizarActivity.getBooleanExtra(
				"contactoEliminado", false);

		if (contactoEliminado) {
			// Se construye el mensaje
			Toast msg = Toast.makeText(this, "Contacto eliminado",
					Toast.LENGTH_LONG);

			// Posici�n del mensaje
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

			// Se muestra el mensaje
			msg.show();
		}

		db = new TBLContactosManager(this);
		nombresContactos = new ArrayList<String>();

		// Se abre la conexi�n a la base de datos
		db.open();

		// Con el cursor obtenemos todos los contactos
		Cursor c = db.obtenerTodosContactos();

		// Es verdadero si existe al menos un registro en el cursor
		if (c.moveToFirst()) {
			do {
				nombresContactos.add(c.getString(1) + " - " + c.getString(2));
				// Con el metodo moveToNext() se recorre el cursor
			} while (c.moveToNext());
		}

		// Se cierra la conexi�n a la base de datos
		db.close();

		//
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, nombresContactos);

		// Objeto que permite autocompletar
		textView = (AutoCompleteTextView) findViewById(R.id.txtContactos);

		// Al ingresar el primer caracter se carga la lista seg�n el caracter
		// ingresado
		textView.setThreshold(1);

		textView.setAdapter(adapter);

		if (nombresContactos.isEmpty()) {
			Toast msg = Toast.makeText(this, "No tiene contactos agregados",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
		}
	}

	/**
	 * Para seleccionar un email
	 * 
	 * @param v
	 */
	public void onClickEmail(View v) {
		String seleccionado = textView.getText().toString();

		if (seleccionado.equals("")) {
			Toast msg = Toast.makeText(this, "Debe seleccionar un contacto",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
			return;
		}

		String[] valoresi = seleccionado.split(" - ");

		String email = valoresi[1].trim();

		Pattern pattern = Pattern.compile(Patrones.PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			Toast msg = Toast.makeText(this, "Correo electronico incorrecto",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			msg.show();
			return;
		}

		Intent intent = new Intent(this, ActualizarContactoActivity.class);
		// El valor email se pasa a la actividad: ActualizarContactoActivity
		intent.putExtra("email", email);
		startActivity(intent);

		// Al pasar a la actividad: ActualizarContactoActivity, se cierra la
		// actividad actual; lo que significa que estando en la actividad
		// ActualizarContactoActivity al dar click hacia atr�s ya no se regresa
		// a la actividad ContactosActivity
		finish();
	}

	/**
	 * Permite regresar a la actividad Main
	 * 
	 * @param v
	 */
	public void onClickRegresar(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
