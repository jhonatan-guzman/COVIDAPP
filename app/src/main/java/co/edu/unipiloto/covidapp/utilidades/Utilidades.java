package co.edu.unipiloto.covidapp.utilidades;

public class Utilidades {

    public static final String
            TABLA_USUARIOS = "Usuarios",
            CAMPO_NOMBRE = "nombre",
            CAMPO_APELLIDO = "apellido",
            CAMPO_CEDULA = "cedula",
            CAMPO_CELULAR = "celular",
            CAMPO_EDAD = "edad",
            CAMPO_CORREO = "correo",
            CAMPO_USUARIO = "User",
            CAMPO_PASSWORD = "password",
            CAMPO_TIPOPERSONA = "tpersona",
            CAMPO_CORREODOCASIG = "docasig",
            CAMPO_ULTIMOSIN = "ultimosin",
            CAMPO_NOMDOC = "nombredoc",
            CAMPO_FASEVACUNACION = "fvacunacion",
            CAMPO_CIUDADMUNICIPIOD1 = "ciudmunicid1",
            CAMPO_FECHADEVACUNACIOND1 = "fechavacud1",
            CAMPO_CIUDADMUNICIPIOD2 = "ciudmunicid2",
            CAMPO_FECHADEVACUNACIOND2 = "fechavacud2",
            CAMPO_ASIGNACIONDEPACIENTES = "asigpasientes",
            CAMPO_VACUNADDO = "vacunado",
            CAMPO_NUMEROVACUNAS = "nvacunas";



    public static final String CREAR_TABLA_USUARIO = "create table " +TABLA_USUARIOS+
            " (" +CAMPO_NOMBRE+ " text, " +CAMPO_APELLIDO+ " text, " +CAMPO_CEDULA+ " int primary key, "
            +CAMPO_CELULAR+ " int, " +CAMPO_EDAD+ " int, "+ CAMPO_CORREO+ " text, " +CAMPO_USUARIO+ " text, "
            +CAMPO_PASSWORD+ " text, "+CAMPO_TIPOPERSONA+ " text, "+CAMPO_CORREODOCASIG+ " text, "+CAMPO_ULTIMOSIN+ " text, "+CAMPO_NOMDOC+ " text, "
            +CAMPO_FASEVACUNACION+ " text, "+ CAMPO_CIUDADMUNICIPIOD1+ " text, "
            +CAMPO_FECHADEVACUNACIOND1+ " text, " +CAMPO_CIUDADMUNICIPIOD2+ " text, " +CAMPO_FECHADEVACUNACIOND2+ " text, "
            +CAMPO_ASIGNACIONDEPACIENTES+ " int, " +CAMPO_VACUNADDO+ " int, " +CAMPO_NUMEROVACUNAS+ " int)";
}
