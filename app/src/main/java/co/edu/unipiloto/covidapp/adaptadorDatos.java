package co.edu.unipiloto.covidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorDatos extends RecyclerView.Adapter<adaptadorDatos.ViewHolderDatos>{

    private List<listElement> listaPasientes;
    private Context context;

    public adaptadorDatos(List<listElement> listaPasientes, Context context) {

        this.listaPasientes = listaPasientes;
        this.context = context;
    }

    @Override
    public adaptadorDatos.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_generate,null);

        return new adaptadorDatos.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(adaptadorDatos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaPasientes.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPasientes.size();
    }

    public void setItems(List<listElement> items){
        listaPasientes = items;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView nombre, cedula, edad, correo, fase, vacuna, celular;
        EditText sintomas;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tv_nomape);
            cedula = (TextView)itemView.findViewById(R.id.tv_cedula);
            edad = (TextView)itemView.findViewById(R.id.tv_edad);
            correo = (TextView)itemView.findViewById(R.id.tv_correo);
            fase = (TextView)itemView.findViewById(R.id.tv_faseva);
            vacuna = (TextView)itemView.findViewById(R.id.tv_vacuna);
            celular = (TextView)itemView.findViewById(R.id.tv_cel);
            sintomas = (EditText)itemView.findViewById(R.id.etm_sinto);
        }

        public void asignarDatos(final listElement item) {
            nombre.setText("Nombre: " + item.getNombre() + " "+ item.getApellido());
            cedula.setText("Cedula: "+item.getCedula());
            edad.setText("Edad: "+item.getEdad());
            correo.setText("Correo: "+item.getCorreo());
            fase.setText("Fase: "+item.getFasevacu());
            celular.setText("Celular: "+item.getCelular());
            vacuna.setText("Ndosis: "+item.getVacunado());
            sintomas.setText("Sintomas: "+item.getSintomas());
        }
    }
}
