package com.seminarios.dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.seminarios.actividades.ActualizarContactoActivity;
import com.seminarios.actividades.R;
import com.seminarios.interfaces.OperacionesContactos;
import com.seminarios.utilitarios.TBLContactosManager;

/**
 * @author Grupo SQLite
 */
@SuppressLint("NewApi")
public class DialogoEliminarContacto extends DialogFragment {

    private static OperacionesContactos db;
	private static int idContacto;

	public static DialogoEliminarContacto nuevo(String titulo,
			Context contexto, int id) {
		DialogoEliminarContacto dialogo = new DialogoEliminarContacto();
		Bundle args = new Bundle();
		args.putString("titulo", titulo);
		dialogo.setArguments(args);

		db = new TBLContactosManager(contexto);
		idContacto = id;

		return dialogo;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String titulo = getArguments().getString("titulo");
		return new AlertDialog.Builder(getActivity())
				.setIcon(R.drawable.eliminar)
				.setTitle(titulo)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

                                ActualizarContactoActivity actualizarContactoActivity = ((ActualizarContactoActivity) getActivity());



								db.open();
                                boolean eliminado = db.borrarContacto(idContacto);
								db.close();

                                actualizarContactoActivity.validarEliminacion(eliminado);

							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).create();
	}

}
