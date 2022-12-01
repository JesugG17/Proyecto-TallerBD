package com.example.esparzaproyecto.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Conexion {

    private static Connection conexion;
    private static String error = "";

    private Conexion(String [] datos) {
        String url = "jdbc:sqlserver://" + datos[0] + ";databaseName="+ datos[1] +";encrypt=true;trustServerCertificate=true";

        String user = datos[2];
        String password = datos[3];
        try {

             conexion = DriverManager.getConnection(url, user, password);
             if (!error.isEmpty()) {
                 error = "";
             }
        } catch (SQLException error) {
            conexion = null;
            Conexion.error = error.getMessage();
        }
    }


    public static void conectar(String ...datos) {
        if (conexion == null) {
            System.out.println("ENTRA");
            Arrays.stream(datos).forEach( System.out::println );
            new Conexion( datos );
        }
    }

    public static Connection getConexion() {
        return conexion;
    }

    public static String error() {
        return error;
    }

}
