package co.edu.unipiloto.covidapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class representante extends AppCompatActivity {

    private String user;
    private TextView tvuser, tvfafaltan, tvfadosfal, tvvacunados;
    private EditText etnvacunas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representante);

        etnvacunas = (EditText)findViewById(R.id.et_nvacunas);
        tvfadosfal = (TextView)findViewById(R.id.tv_sinface2);
        tvvacunados = (TextView)findViewById(R.id.tv_vacunados);
        tvfafaltan = (TextView)findViewById(R.id.tv_facesinvacu);
        tvuser = (TextView)findViewById(R.id.tv_userr);
        user = getIntent().getStringExtra("datos");
        tvuser.setText(user);
    }

    public void ingresar_vacunas_rcibidas(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nvacunas = String.valueOf(etnvacunas.getText());

        ContentValues registro = new ContentValues();
        registro.put(Utilidades.CAMPO_NUMEROVACUNAS, nvacunas);

        BaseDeDatos.update("Usuarios", registro, "User ='" + user + "'", null);
        BaseDeDatos.close();

    }
    public void consultar_pacientes_sin_vacunar_por_fase(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select count(*) from Usuarios", null);
        Cursor fila1 = BaseDeDatos.rawQuery("select count(*) from Usuarios where fvacunacion = 'f1' AND vacunado = 0" , null);
        Cursor fila2 = BaseDeDatos.rawQuery("select count(*) from Usuarios where vacunado = '2'", null);

        if (fila.moveToFirst() && fila1.moveToFirst() && fila2.moveToFirst()) {

            int a =  Integer.parseInt(fila.getString(0));
            int b =  Integer.parseInt(fila1.getString(0));
            int c =  Integer.parseInt(fila2.getString(0));

            int face1 = a - (b + c);
            int face2 = a - c;

            BaseDeDatos.close();

            tvfafaltan.setText("face1 = " + String.valueOf(face1) + " face2 = " + String.valueOf(face2));


        }
    }
    public void consultar_pacientes_segunda_dosis_por_vacunar(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila1 = BaseDeDatos.rawQuery("select count(*) from Usuarios where vacunado = '1'", null);

        if (fila1.moveToFirst()) {
            String b = String.valueOf(fila1.getString(0));
            tvfadosfal.setText("face2 = " + b);
        }
        BaseDeDatos.close();




    }
    public void consultar_paceintes_vacunados(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila1 = BaseDeDatos.rawQuery("select count(*) from Usuarios where vacunado = '2'", null);

        if (fila1.moveToFirst()) {
            String b = String.valueOf(fila1.getString(0));
            tvvacunados.setText("vacunados = " + b);
        }
        BaseDeDatos.close();



    }
}