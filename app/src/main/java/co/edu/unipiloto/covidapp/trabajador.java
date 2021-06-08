package co.edu.unipiloto.covidapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class trabajador extends AppCompatActivity {

    private TextView tvuser, tvlugar, tvnumero, tvvacunados,tvpashoy;
    private EditText etcedula, etvacuna, etdesde, ethasta;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajador);

        tvuser = (TextView)findViewById(R.id.tv_usert);
        tvlugar = (TextView)findViewById(R.id.tv_lugar);
        user = getIntent().getStringExtra("datos");
        tvuser.setText(user);
        tvpashoy = (TextView)findViewById(R.id.tv_pashoy);
        tvnumero = (TextView)findViewById(R.id.tv_numero);
        tvvacunados = (TextView)findViewById(R.id.tv_nvacunados);
        etcedula = (EditText)findViewById(R.id.et_cedula2);
        etvacuna = (EditText)findViewById(R.id.et_vacuna);
        etdesde = (EditText)findViewById(R.id.et_desde);
        ethasta = (EditText)findViewById(R.id.et_hasta);
    }

    public void lugar_de_vacunacion(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery
                ("select "+ Utilidades.CAMPO_CIUDADMUNICIPIOD1+" from Usuarios where User ='" + user +"'" ,null);

        if(fila.moveToFirst()) {

            String f = fila.getString(0);
            tvlugar.setText(f);
            BaseDeDatos.close();

        }else{
            Toast.makeText(this, "error del sistema intente mas tarde", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }

    }

    public void personas_a_vacunar(View view) {

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select "+ Utilidades.CAMPO_ASIGNACIONDEPACIENTES+" from Usuarios where User = '"+user+"'", null);
        pasientesHoy(0);
        if (fila.moveToFirst()) {

            String f = fila.getString(0);
                tvnumero.setText("Total = "+f);


                fila.close();
                BaseDeDatos.close();
        }
    }

    public void pasientesHoy(int a){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Date dt = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dt);


        Cursor aux = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + " FROM "+Utilidades.TABLA_USUARIOS+" WHERE User ='"+user+"'",null);
        aux.moveToFirst();
        String nombreDoc = aux.getString(0);

        Cursor fila = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "
                                                            + Utilidades.CAMPO_CEDULA + ", "+ Utilidades.CAMPO_EDAD + ", " +Utilidades.CAMPO_CELULAR + ", "
                                                            + Utilidades.CAMPO_FASEVACUNACION + ", "+ Utilidades.CAMPO_CORREO + ", "
                                                            + Utilidades.CAMPO_ULTIMOSIN + ", " + Utilidades.CAMPO_VACUNADDO +
                                                            " FROM "+Utilidades.TABLA_USUARIOS+
                                                            " WHERE("+Utilidades.CAMPO_NOMDOC+"='"+nombreDoc+"') AND ((" + Utilidades.CAMPO_FECHADEVACUNACIOND1 + " = '"+date+"') OR (" + Utilidades.CAMPO_FECHADEVACUNACIOND2 + "= '"+date+"'))", null);
        int pasiHoy = fila.getCount();
        if(a == 0){
            tvpashoy.setText(String.valueOf(pasiHoy));
        }else{
            if(fila.moveToFirst()){

                Toast.makeText(this, "todo va bien", Toast.LENGTH_SHORT).show();

            }else{
            Toast.makeText(this, "falla en el sistema", Toast.LENGTH_SHORT).show();
            }
        }
        BaseDeDatos.close();
    }

    public void confirmacion_de_vacunacion(View view){

        String vacuna = String.valueOf(etvacuna.getText());
        String cedula = String.valueOf(etcedula.getText());

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put(Utilidades.CAMPO_VACUNADDO, vacuna);

        BaseDeDatos.update("Usuarios", registro, "cedula ='" + cedula + "'", null);
        BaseDeDatos.close();

    }

    public void personas_vacunadas_en_un_periodo_de_tiempo(View view){

        String fecha1 = String.valueOf(etdesde.getText());
        String fecha2 = String.valueOf(ethasta.getText());

        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date desde = fecha.parse(fecha1);
            Date hasta = fecha.parse(fecha2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select count(*) from Usuarios where fechavacud2 ='" +fecha1+"'"  , null);

        BaseDeDatos.close();

    }

    public void barlistar(View view){

        Intent i = new Intent(trabajador.this, barlistas.class);
        i.putExtra("user", user);
        startActivity(i);

    }
}