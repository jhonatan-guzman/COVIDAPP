package co.edu.unipiloto.covidapp;

public class listElement {


    public String nombre;
    public String apellido;
    public String cedula;
    public String edad;
    public String correo;
    public String fasevacu;
    public String vacunado;
    public String sintomas;
    public String celular;

    public listElement( String nombre, String apellido, String cedula, String edad, String correo, String fasevacu, String vacunado,String celular, String sintomas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.celular = celular;
        this.edad = edad;
        this.correo = correo;
        this.fasevacu = fasevacu;
        this.vacunado = vacunado;
        this.sintomas = sintomas;

    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFasevacu() {
        return fasevacu;
    }

    public String getVacunado() {
        return vacunado;
    }

    public String getSintomas() {
        return sintomas;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFasevacu(String fasevacu) {
        this.fasevacu = fasevacu;
    }

    public void setVacunado(String vacunado) {
        this.vacunado = vacunado;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

}
