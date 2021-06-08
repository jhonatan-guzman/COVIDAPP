package co.edu.unipiloto.covidapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class cliente extends AppCompatActivity {

    private TextView tvuser, tvdoctor, tvface, tvfecha;
    private EditText multiline;
    private String user;
    private EditText etcita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        tvuser = (TextView)findViewById(R.id.tv_user);
        etcita = (EditText)findViewById(R.id.et_cita);
        tvface = (TextView)findViewById(R.id.tv_face);
        tvfecha = (TextView)findViewById(R.id.tv_fecha);
        tvdoctor = (TextView)findViewById(R.id.tv_doctor);
        multiline =(EditText)findViewById(R.id.etm_sintomas);
        user = getIntent().getStringExtra("datos");
        tvuser.setText(user);
    }

    public void PedirFace(View view){

        String f = "";

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery
                ("select "+Utilidades.CAMPO_FASEVACUNACION+" from Usuarios where User ='" + user +"'" ,null);

        if(fila.moveToFirst()) {

            f = fila.getString(0);
            tvface.setText(f);
            BaseDeDatos.close();

        }else{
            Toast.makeText(this, "error del sistema intente mas tarde", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }

        confirmacion("Vacunacion face", "Te correspondio la face " + f, "0");

    }

    public void ver_cita(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery
                ("select "+Utilidades.CAMPO_FECHADEVACUNACIOND1+", "+Utilidades.CAMPO_FECHADEVACUNACIOND2+ " from Usuarios where User ='" + user +"'" ,null);

        if(fila.moveToFirst()) {

            String f = fila.getString(0);
            String f2 = fila.getString(1);
            tvfecha.setText("cita 1 = " + f + " cita 2=" + f2);
            BaseDeDatos.close();

        }else{
            Toast.makeText(this, "No has agendado ninguna cita", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }


    }

    public void asignardoc(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery
                ("select "+Utilidades.CAMPO_NOMDOC+ " from Usuarios where User = '"+ user +"'" ,null);

        if(fila.moveToFirst()) {
            try{
                if (!fila.getString(0).isEmpty()) {

                    Toast.makeText(this, "ya tienes un doctor asignado", Toast.LENGTH_SHORT).show();
                    tvdoctor.setText(fila.getString(0));

                    BaseDeDatos.close();
                }
            }catch (Exception e){
                String doc[] = elegirdoctor();

                ContentValues registro = new ContentValues();
                registro.put(Utilidades.CAMPO_NOMDOC, doc[0]);
                registro.put(Utilidades.CAMPO_CORREODOCASIG, doc[1]);

                BaseDeDatos.update("Usuarios", registro, "User='" + user + "'", null);
                BaseDeDatos.close();

                tvdoctor.setText(doc[0]);
            }

        }else{
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();

    }

    public String[] elegirdoctor(){


        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery
                ("select "+Utilidades.CAMPO_NOMBRE+", "+Utilidades.CAMPO_CORREO+", "+ Utilidades.CAMPO_ASIGNACIONDEPACIENTES+", "+Utilidades.CAMPO_CEDULA+ " from Usuarios where tpersona = 'trabajador'" ,null);

        int nf = fila.getCount();

        if(fila.moveToFirst()) {

            String aux[] = new String[4];

            aux[0] = fila.getString(0);
            aux[1] = fila.getString(1);
            aux[2] = fila.getString(2);
            aux[3] = fila.getString(3);

            for(int i=0;i < nf-1;i++){


                fila.moveToNext();

                if(Integer.parseInt(aux[2]) <= Integer.parseInt(fila.getString(2))){


                }else{
                    aux[0] = fila.getString(0);
                    aux[1] = fila.getString(1);
                    aux[2] = fila.getString(2);
                    aux[3] = fila.getString(3);
                }

            }

            ContentValues registro = new ContentValues();
            registro.put(Utilidades.CAMPO_ASIGNACIONDEPACIENTES, aux[2] + 1);
            BaseDeDatos.update("Usuarios", registro, "cedula='" + aux[3] + "'", null);

            BaseDeDatos.close();
            return aux;

        }else{
            Toast.makeText(this, "No hay informacion", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    return null;
    }

    public void enviar(View view){

        String sintomas = multiline.getText().toString();

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put(Utilidades.CAMPO_ULTIMOSIN, sintomas);

            BaseDeDatos.update("Usuarios", registro, "User='" + user + "'", null);
            BaseDeDatos.close();

        confirmacion(user + " sintomas presentes", sintomas, "1");

    }

    public void confirmacion(String as, String men, String ti){

            String correo = "";

            AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
            Cursor fila;
            if(ti.equals("0")) {
                 fila = BaseDeDatos.rawQuery
                        ("select " + Utilidades.CAMPO_CORREO + " from Usuarios where User ='" + user + "'", null);
                        fila.moveToFirst();
            }else{
                 fila = BaseDeDatos.rawQuery
                        ("select " + Utilidades.CAMPO_CORREODOCASIG + " from Usuarios where User ='" + user + "'", null);
                        fila.moveToFirst();
            }
            BaseDeDatos.close();

                try {
                    correo = fila.getString(0);

                    String asunto = as;
                    String mensage = men;

                    if(!correo.isEmpty() && !asunto.isEmpty() && !mensage.isEmpty()) {

                        Intent i = new Intent(this, Mail_send.class);
                        i.putExtra("correo", correo);
                        i.putExtra("asunto", asunto);
                        i.putExtra("mensage", mensage);
                        startActivity(i);
                    }else{
                        Toast.makeText(this, "sin correo de confirmacion", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(this, "Deves asignar un doctor", Toast.LENGTH_SHORT).show();
                }

    }

    public void fecha_para_vacunacion(View view){

        String cita = String.valueOf(etcita.getText());

        if(cita.length()<5){

            Toast.makeText(cliente.this, "Digite una fecha en el formato deseado", Toast.LENGTH_SHORT).show();

        }else {

            Pattern patron = Pattern.compile("[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}");
            Matcher mat = patron.matcher(cita);

            if (!mat.matches() | Integer.parseInt(cita.substring(0, 2)) > 12 | Integer.parseInt(cita.substring(3, 5)) > 31) {

                Toast.makeText(cliente.this, "Digite el formato deseado", Toast.LENGTH_SHORT).show();

            } else {

                Date dt = new Date();
                Date cita1 = null;
                String aux = new SimpleDateFormat("yyyy").format(dt);
                cita = aux + "-" + cita;

                AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                Cursor fila = BaseDeDatos.rawQuery
                        ("select " + Utilidades.CAMPO_VACUNADDO + " from Usuarios where User ='" + user + "'", null);

                if (fila.moveToFirst()) {

                    String f = fila.getString(0);

                    if (f.equals("0")) {

                        SimpleDateFormat aux2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            cita1 = aux2.parse(cita);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Calendar c = Calendar.getInstance();
                        String fechacita = new SimpleDateFormat("yyyy-MM-dd").format(cita1);
                        c.setTime(cita1);
                        c.add(Calendar.DATE, 21);
                        cita1 = c.getTime();
                        String fechacita2 = new SimpleDateFormat("yyyy-MM-dd").format(cita1);


                        ContentValues registro = new ContentValues();
                        registro.put(Utilidades.CAMPO_FECHADEVACUNACIOND1, fechacita);
                        registro.put(Utilidades.CAMPO_FECHADEVACUNACIOND2, fechacita2);

                        BaseDeDatos.update("Usuarios", registro, "User='" + user + "'", null);


                        tvfecha.setText("fecha 1 : " + fechacita + "  fecha 2 : " + fechacita2);


                    } else {

                        if (f.equals("1")) {

                            AlertDialog.Builder alerta = new AlertDialog.Builder(cliente.this);

                            alerta.setMessage("Ya te has aplicado la primera dosis de la vacuna, " +
                                    "recuerda que la segunda dosis de la vacuna deve ser minimo 21 dias despues, " +
                                    "¿deseas cambiar la fecha de vacunacion de la segunda dosis por la anterior digitada en le recuadro?");
                            alerta.setTitle("Informacion");

                            String finalCita = cita;
                            alerta.setPositiveButton("si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                                    Cursor fecha = BaseDeDatos.rawQuery
                                            ("select " + Utilidades.CAMPO_FECHADEVACUNACIOND1 + " from Usuarios where User ='" + user + "'", null);

                                    fecha.moveToFirst();
                                    String f1 = fecha.getString(0);

                                    LocalDate dBefore = LocalDate.parse(f1);
                                    LocalDate dAfter = LocalDate.parse(finalCita);

                                    long difer = dBefore.until(dAfter, ChronoUnit.DAYS);

                                    if (difer < 21) {

                                        AlertDialog.Builder alerta = new AlertDialog.Builder(cliente.this);
                                        alerta.setMessage("Recuerda que la fecha ingresada dever ser minimo 21 dias despues de la aplicacion de la primera dosis, " +
                                                "y no maximo a 30 dias por motivos de logistica")
                                                .setTitle("Informacion");
                                        AlertDialog dialogg = alerta.create();
                                        dialogg.show();

                                    } else {

                                        ContentValues registro = new ContentValues();
                                        registro.put(Utilidades.CAMPO_FECHADEVACUNACIOND2, finalCita);

                                        BaseDeDatos.update("Usuarios", registro, "User='" + user + "'", null);
                                        Toast.makeText(cliente.this,"su cita para la segunda vacunacion fue modificada", Toast.LENGTH_SHORT).show();
                                        tvfecha.setText("fecha 2 : " + finalCita);
                                    }
                                    BaseDeDatos.close();
                                }
                            });

                            alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(cliente.this,"no se a modificado tu fecha de vacunanion", Toast.LENGTH_SHORT).show();
                                }
                            });

                            AlertDialog dialog = alerta.create();
                            dialog.show();


                        } else {

                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(this);

                            alerta1.setMessage("Usted ya a sido vacunado en su totalidad con las dos dosis, " +
                                    "no es nesesario agendar ni modificar fechas de citas, gracias")
                                    .setTitle("Informacion");
                            AlertDialog dialog = alerta1.create();
                            dialog.show();

                        }
                    }
                } else {

                    Toast.makeText(this, "error del sistemas reinicia la aplicacion", Toast.LENGTH_SHORT).show();

                }
                BaseDeDatos.close();
            }
            confirmacion("cita para vacunacion", "sus fechas de vacunacion fueron modificadas o asignadas " + tvface.getText(), "0");
        }


    }

}
