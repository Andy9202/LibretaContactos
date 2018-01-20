package com.seminarios.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.seminarios.actividades.ContactosActivity;
import com.seminarios.actividades.EliminarContactosActivity;
import com.seminarios.actividades.NuevoContactoActivity;
import com.seminarios.actividades.R;

/**
 * @author Grupo SQLite
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);
	}

	public void onClickContactos(View v) {
		Intent i = new Intent(this, ContactosActivity.class);
		startActivity(i);
		finish();
	}

	public void onClickNuevo(View v) {
		Intent i = new Intent(this, NuevoContactoActivity.class);
		startActivity(i);
		finish();
	}

    public void onBorrarContactos(View v){
        Intent i = new Intent(this, EliminarContactosActivity.class);
        startActivity(i);
        finish();
    }
}
