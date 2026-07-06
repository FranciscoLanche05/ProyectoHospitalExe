package org.example.prueba_conexion;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtEdad;

    @FXML
    private TableView<Persona> tbldatos;

    @FXML
    private TableColumn<Persona, Integer> colId;

    @FXML
    private TableColumn<Persona, String> colNombre;

    @FXML
    private TableColumn<Persona, String> colApellido;

    @FXML
    private TableColumn<Persona, String> colEdad;

    @FXML
    private TableColumn<Persona, String> colCedula;

    private PersonaDao personaDao;

    @FXML
    public void initialize() {

        personaDao = new PersonaDao();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        try {
            cargarTabla();
        } catch (SQLException e) {
            mostrarError("Error al cargar la tabla.");
        }
    }

    @FXML
    public void registrar() {

        if (!validarCampos()) {
            return;
        }

        try {

            Persona persona = new Persona(
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtEdad.getText().trim(),
                    txtCedula.getText().trim()
            );

            personaDao.registrar(persona);

            mostrarInformacion("Paciente registrado correctamente.");

            cargarTabla();
            limpiar();

        } catch (SQLException e) {
            mostrarError("No se pudo registrar el paciente.");
        }
    }

    @FXML
    public void editar() {

        Persona personaSeleccionada = tbldatos.getSelectionModel().getSelectedItem();

        if (personaSeleccionada == null) {
            mostrarAdvertencia("Seleccione un registro para editar.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        try {

            personaSeleccionada.setNombre(txtNombre.getText().trim());
            personaSeleccionada.setApellido(txtApellido.getText().trim());
            personaSeleccionada.setEdad(txtEdad.getText().trim());
            personaSeleccionada.setCedula(txtCedula.getText().trim());

            personaDao.editar(personaSeleccionada);

            mostrarInformacion("Paciente actualizado correctamente.");

            cargarTabla();
            limpiar();

        } catch (SQLException e) {
            mostrarError("No se pudo actualizar el registro.");
        }
    }

    @FXML
    public void eliminar() {

        Persona personaSeleccionada = tbldatos.getSelectionModel().getSelectedItem();

        if (personaSeleccionada == null) {
            mostrarAdvertencia("Seleccione un registro.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar este paciente?");

        if (confirmacion.showAndWait().get() != ButtonType.OK) {
            return;
        }

        try {

            personaDao.eliminar(Integer.parseInt(personaSeleccionada.getId()));

            mostrarInformacion("Paciente eliminado correctamente.");

            cargarTabla();
            limpiar();

        } catch (Exception e) {
            mostrarError("No se pudo eliminar.");
        }
    }

    @FXML
    public void limpiar() {

        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtCedula.clear();

        tbldatos.getSelectionModel().clearSelection();
    }

    @FXML
    public void seleccionar() {

        Persona p = tbldatos.getSelectionModel().getSelectedItem();

        if (p != null) {

            txtNombre.setText(p.getNombre());
            txtApellido.setText(p.getApellido());
            txtEdad.setText(p.getEdad());
            txtCedula.setText(p.getCedula());

        }
    }

    public void cargarTabla() throws SQLException {

        ObservableList<Persona> lista = personaDao.obtenerTodos();
        tbldatos.setItems(lista);

    }

    private boolean validarCampos() {

        if (txtNombre.getText().trim().isEmpty()
                || txtApellido.getText().trim().isEmpty()
                || txtEdad.getText().trim().isEmpty()
                || txtCedula.getText().trim().isEmpty()) {

            mostrarAdvertencia("Todos los campos son obligatorios.");
            return false;
        }


        int edad = Integer.parseInt(txtEdad.getText());

        if (edad < 0 || edad > 120) {

            mostrarAdvertencia("La edad debe estar entre 0 y 120 años.");
            return false;
        }

        if (!txtCedula.getText().matches("\\d{10}")) {

            mostrarAdvertencia("La cédula debe tener exactamente 10 dígitos.");
            return false;
        }

        return true;
    }

    private void mostrarInformacion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}