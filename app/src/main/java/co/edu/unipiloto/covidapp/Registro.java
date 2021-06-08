package co.edu.unipiloto.covidapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class Registro extends AppCompatActivity {

    private EditText etnom, etapell, etcedu, etcelu, etedad, etpass, etciumuni, etcorreo;
    private RadioButton rbc, rbt, rbr;
    private Spinner sface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etnom = (EditText)findViewById(R.id.et_nombre);
        etapell = (EditText)findViewById(R.id.et_apellido);
        etcedu = (EditText)findViewById(R.id.et_cedula);
        etcelu = (EditText)findViewById(R.id.et_celular);
        etedad = (EditText)findViewById(R.id.et_edad);
        etpass = (EditText)findViewById(R.id.etpassword);
        etciumuni = (EditText)findViewById(R.id.et_ciumunici);
        sface = (Spinner) findViewById(R.id.spinner);
        etcorreo = (EditText)findViewById(R.id.et_correo);


    }

    public void Registrar(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre = etnom.getText().toString();
        String apellido = etapell.getText().toString();
        String cedula = etcedu.getText().toString();
        String celular = etcelu.getText().toString();
        String edad = etedad.getText().toString();
        String correo = etcorreo.getText().toString();
        String password = etpass.getText().toString();
        String ciumuni = etciumuni.getText().toString();
        String face = String.valueOf(sface.getSelectedItem()).substring(0,2);
        String tipoPersona = null;
        String numPasiente = "";
        String vacunado = "0";

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(edad, fmt);
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(fechaNac, ahora);
        edad = String.valueOf(periodo.getYears());



            if(cedula.equals(1070709120) | cedula.equals(1070709121) | cedula.equals(1070709122) | cedula.equals(1070709123) |
                    cedula.equals(1070709124) | cedula.equals(1070709125) | cedula.equals(1070709126) | cedula.equals(1070709127) | cedula.equals(1070709128) | cedula.equals(1070709129)){

                tipoPersona = "trabajador" ;
                correo = "supermov16@gmail.com";
                numPasiente = "0";

            }else {

                if (cedula.equals(1070709110) | cedula.equals(1070709111)) {
                    tipoPersona = "representante";
                    correo = "supermov16@gmail.com";

                }else{
                    tipoPersona = "cliente";
                }
            }


            if (!nombre.isEmpty() && !apellido.isEmpty() && !cedula.isEmpty() && !celular.isEmpty() && !edad.isEmpty() && !tipoPersona.isEmpty() && !password.isEmpty() && !correo.isEmpty()) {

                ContentValues registro = new ContentValues();
                registro.put(Utilidades.CAMPO_NOMBRE, nombre);
                registro.put(Utilidades.CAMPO_APELLIDO, apellido);
                registro.put(Utilidades.CAMPO_CEDULA, cedula);
                registro.put(Utilidades.CAMPO_CELULAR, celular);
                registro.put(Utilidades.CAMPO_EDAD, edad);
                registro.put(Utilidades.CAMPO_CORREO, correo);
                registro.put(Utilidades.CAMPO_USUARIO, nombre + "" + cedula);
                registro.put(Utilidades.CAMPO_PASSWORD, password);
                registro.put(Utilidades.CAMPO_CIUDADMUNICIPIOD1, ciumuni);
                registro.put(Utilidades.CAMPO_FASEVACUNACION, face);
                registro.put(Utilidades.CAMPO_TIPOPERSONA, tipoPersona);
                registro.put(Utilidades.CAMPO_ASIGNACIONDEPACIENTES, numPasiente);
                registro.put(Utilidades.CAMPO_VACUNADDO, vacunado);

                BaseDeDatos.insert("Usuarios", null, registro);
                BaseDeDatos.close();


                etnom.setText("");
                etapell.setText("");
                etcedu.setText("");
                etcelu.setText("");
                etedad.setText("");
                etpass.setText("");
                etcorreo.setText("");
                etciumuni.setText("");



                Toast.makeText(this, "El registro fue exitoso"+ nombre +""+ cedula, Toast.LENGTH_SHORT).show();

                confirmacion();

                finish();


            } else {
                Toast.makeText(this, "Desbes llenar todos los espacios por favor", Toast.LENGTH_SHORT).show();

            }



    }

    public void confirmacion(){

        String correo = etcorreo.getText().toString();;
        String asunto = "confirmacion";
        String mensage = "bienvenido a nuestra app, esperamos ser de muhco servivio para usted";
        String tipo = "0";



        if(!correo.isEmpty() && !asunto.isEmpty() && !mensage.isEmpty()) {

            Intent i = new Intent(this, Mail_send.class);
            i.putExtra("correo", correo);
            i.putExtra("asunto", asunto);
            i.putExtra("mensage", mensage);
            i.putExtra("tipo", tipo);
            startActivity(i);
        }else{
            Toast.makeText(this, "hace falta alguna informacion", Toast.LENGTH_SHORT).show();
        }
    }
}