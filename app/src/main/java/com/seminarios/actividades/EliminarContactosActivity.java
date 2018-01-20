package com.seminarios.actividades;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seminarios.interfaces.OperacionesContactos;
import com.seminarios.principal.MainActivity;
import com.seminarios.utilitarios.TBLContactosManager;

/**
 * @author Grupo SQLite
 */
public class EliminarContactosActivity extends Activity {

    private static OperacionesContactos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_contactos);
        db = new TBLContactosManager(this);

        Button btnEliminar = (Button)findViewById(R.id.btnEliminarContactos);

        db.open();

        int registros = db.obtenerTodosContactos().getCount();

        if(registros == 0){
            Toast.makeText(this,"No tiene contactos registrados",Toast.LENGTH_LONG).show();
            btnEliminar.setEnabled(false);
            btnEliminar.setBackgroundColor(getResources().getColor(R.color.gris));
        }

        db.close();
    }

    public void onClickBorrar(View view){
        Button btnEliminar = (Button)findViewById(R.id.btnEliminarContactos);

        db.open();
        db.borrarContactos();
        db.close();

        btnEliminar.setBackgroundColor(getResources().getColor(R.color.verde));

        Toast.makeText(this,"Todos los contactos fueron borrados!",Toast.LENGTH_LONG).show();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onRegresar(view);
    }

    public void onRegresar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eliminar_contactos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
