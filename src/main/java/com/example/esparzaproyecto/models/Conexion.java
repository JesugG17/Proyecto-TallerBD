package com.example.esparzaproyecto.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Connection conexion;
    private static String error = "";

    private Conexion(String[] datos) {
        String url = "jdbc:sqlserver://" + datos[0] + ";databaseName=" + datos[1] + ";encrypt=true;trustServerCertificate=true";

        String user = datos[2];
        String password = datos[3];
        try {
            DriverManager.setLoginTimeout(3);
            conexion = DriverManager.getConnection(url, user, password);
            if (!error.isEmpty()) {
                error = "";
            }
        } catch (SQLException error) {
            conexion = null;
            System.out.println(error.getErrorCode());
            if (error.getErrorCode() == 18456) {
                Conexion.error = "Algun dato es incorrecto";
                return;
            }
            Conexion.error = "El usuario " + datos[2] + " no tiene acceso a la base de datos " + datos[1];
        }
    }


    public static void conectar(String... datos) {
        if (conexion == null) {
            new Conexion(datos);
        }
    }

    public static Connection getConexion() {
        return conexion;
    }

    public static String error() {
        return error;
    }

}
