package org.example.prueba_conexion;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao {

    public ObservableList<Persona> obtenerTodos() throws SQLException{
        ObservableList<Persona> lista = FXCollections.observableArrayList();

        String sql = "SELECT * FROM pacientes ORDER BY id ASC ";
        try(Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                lista.add(new Persona(

                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("edad"),
                        rs.getString("cedula")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }





    public static void registrar(Persona p) throws SQLException {
        String sql = "INSERT INTO pacientes (nombre, apellido, edad, cedula) VALUES (?, ?, ?, ?) ";

        try(Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql) ){

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3,p.getEdad());
            ps.setString(4, p.getCedula());
            ps.executeUpdate();


        }
    }


    public void editar(Persona p) throws SQLException {

        String sql = "UPDATE pacientes SET nombre=?, apellido=?, edad=?, cedula=? WHERE id=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getEdad());
            ps.setString(4, p.getCedula());
            ps.setString(5, p.getId());

            ps.executeUpdate();
        }
    }

    public void eliminar( int id) throws SQLException{
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try(Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql) ){
            ps.setInt(1, id);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
