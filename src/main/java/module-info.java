module org.example.prueba_conexion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens org.example.prueba_conexion to javafx.fxml;
    exports org.example.prueba_conexion;


}