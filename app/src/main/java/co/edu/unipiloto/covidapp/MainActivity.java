package co.edu.unipiloto.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import co.edu.unipiloto.covidapp.AdminSQLDB;

import co.edu.unipiloto.covidapp.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    private TextView etregis;
    private EditText usuario;
    private EditText passw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etregis = (TextView)findViewById(R.id.tvreg);
        usuario = (EditText)findViewById(R.id.et_usuario);
        passw = (EditText)findViewById(R.id.etpass);




    }

    public void Ingresar(View view){

        AdminSQLDB admin = new AdminSQLDB(this, "covid", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String user = usuario.getText().toString();
        String pass = passw.getText().toString();

        if(!user.isEmpty() && !pass.isEmpty()){

            Cursor fila = BaseDeDatos.rawQuery
                    ("select User, password, tpersona from Usuarios where User ='" + user +"'" ,null);

            if(fila.moveToFirst()){

                String u = fila.getString(0);
                String p = fila.getString(1);
                String tp = fila.getString(2);

                Toast.makeText(this, "es == "+ tp, Toast.LENGTH_LONG).show();

                if(p.equals(pass)){

                    BaseDeDatos.close();

                    if(tp.equals("cliente")){

                        Intent i = new Intent(this, cliente.class);
                        i.putExtra("datos", u);
                        startActivity(i);

                    }else{

                        if(tp.equals("trabajador")){

                            Intent i = new Intent(this, trabajador.class);
                            i.putExtra("datos", u);
                            startActivity(i);

                        }else{

                            if(tp.equals("representante")){

                                Intent i = new Intent(this, representante.class);
                                i.putExtra("datos", u);
                                startActivity(i);

                            }
                        }
                    }

                }else{
                    Toast.makeText(this, "contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    BaseDeDatos.close();
                }

            } else{
                Toast.makeText(this, "el usuario no existe", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else {
            Toast.makeText(this, "llene todos los espacios", Toast.LENGTH_LONG).show();
            BaseDeDatos.close();
        }


    }

    public void siguiente(View view) {

        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

}