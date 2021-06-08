package co.edu.unipiloto.covidapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class barlistas extends AppCompatActivity {

    List<listElement> listap;
    RecyclerView recycler;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barlistas);

        user = getIntent().getStringExtra("user");
        listap = new ArrayList<>();
    }

    private void init() {


        adaptadorDatos adaptador = new adaptadorDatos(listap, this);
        recycler = (RecyclerView)findViewById(R.id.rv_lista);
        recycler.clearFocus();
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(adaptador);


    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menuover, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        int id=item.getItemId();

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Date dt = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dt);

        Cursor aux = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + " FROM "+Utilidades.TABLA_USUARIOS+" WHERE User ='"+user+"'",null);
        aux.moveToFirst();
        String nombreDoc = aux.getString(0);


        if(id == R.id.item1){
            listap.clear();
            Cursor fila1 = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "
                    + Utilidades.CAMPO_CEDULA + ", "+ Utilidades.CAMPO_EDAD + ", " +Utilidades.CAMPO_CORREO + ", "
                    + Utilidades.CAMPO_FASEVACUNACION + ", "+ Utilidades.CAMPO_CELULAR + ", "
                    + Utilidades.CAMPO_VACUNADDO + ", " + Utilidades.CAMPO_ULTIMOSIN +
                    " FROM "+Utilidades.TABLA_USUARIOS+
                    " WHERE("+Utilidades.CAMPO_NOMDOC+"='"+nombreDoc+"') AND ((" + Utilidades.CAMPO_FECHADEVACUNACIOND1 + " = '"+date+"') OR (" + Utilidades.CAMPO_FECHADEVACUNACIOND2 + "= '"+date+"'))", null);
           presentar(fila1);
        }

        if(id == R.id.item2){
            listap.clear();
            Cursor fila1 = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "
                    + Utilidades.CAMPO_CEDULA + ", "+ Utilidades.CAMPO_EDAD + ", " +Utilidades.CAMPO_CORREO + ", "
                    + Utilidades.CAMPO_FASEVACUNACION + ", "+ Utilidades.CAMPO_CELULAR + ", "
                    + Utilidades.CAMPO_VACUNADDO + ", " + Utilidades.CAMPO_ULTIMOSIN +
                    " FROM "+Utilidades.TABLA_USUARIOS+
                    " WHERE(" + Utilidades.CAMPO_NOMDOC+"='"+nombreDoc+"')", null);
            presentar(fila1);
        }

        if(id == R.id.item3){
            listap.clear();
            Cursor fila1 = BaseDeDatos.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "
                    + Utilidades.CAMPO_CEDULA + ", "+ Utilidades.CAMPO_EDAD + ", " +Utilidades.CAMPO_CORREO + ", "
                    + Utilidades.CAMPO_FASEVACUNACION + ", "+ Utilidades.CAMPO_CELULAR + ", "
                    + Utilidades.CAMPO_VACUNADDO + ", " + Utilidades.CAMPO_ULTIMOSIN +
                    " FROM "+Utilidades.TABLA_USUARIOS+
                    " WHERE(" + Utilidades.CAMPO_NOMDOC+"= '" +nombreDoc+ "') AND (" + Utilidades.CAMPO_VACUNADDO + " = '" + 1 + "')", null);
            presentar(fila1);
        }

            BaseDeDatos.close();


        return super.onOptionsItemSelected(item);
    }

    public void presentar(Cursor fila){

        int pasiHoy = fila.getCount();

        if(fila.moveToFirst()){

            for(int i = 0;i < pasiHoy;i++) {

                listap.add(new listElement(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4),
                        fila.getString(5), fila.getString(6), fila.getString(7), fila.getString(8)));

                if(i != pasiHoy){ fila.moveToNext();}
            }
        }else{
            Toast.makeText(this, "falla en el sistema", Toast.LENGTH_SHORT).show();
        }
        init();



    }




}