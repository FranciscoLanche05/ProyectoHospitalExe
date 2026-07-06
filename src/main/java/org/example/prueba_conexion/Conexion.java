package org.example.prueba_conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;


public class Conexion {
    private static Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static String URL = dotenv.get("AIVEN_DB_URL");
    private static String USER = dotenv.get("AIVEN_DB_USER");
    private static String PASSWORD = dotenv.get("AIVEN_DB_PASSWORD");


    public static Connection getConnection()throws SQLException {
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);

        }catch (SQLException e){
            System.err.println("No se pudo establecer la conexión" + e.getMessage());
            throw e;
        }
    }
}
