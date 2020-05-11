package org.users;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.Conexion;
import org.modelos.Usuarios;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class formUserController implements Initializable {

    int id = 0;
    @FXML
    Label idlbl = new Label();
    @FXML
    Button btnAgregar = new Button();
    @FXML
    JFXTextField nickname = new JFXTextField();
    @FXML
    JFXTextField name = new JFXTextField();
    @FXML
    JFXToggleButton admin = new JFXToggleButton();
    @FXML
    JFXToggleButton seller = new JFXToggleButton();

    Integer seAgrego=0;
    Boolean seActualizo = false;


    Usuarios  user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.seller.setSelected(true);

    }


    @FXML
    void agregarCliente() throws SQLException {

        if (!this.name.getText().equals("") && !this.nickname.getText().equals("")){
            Integer administrador = 0;
            if (this.admin.isSelected()){
                administrador = 1;
            }else{
                administrador = 2;
            }

            Integer sellerIs = 0;
            if (this.seller.isSelected()){
                sellerIs=1;
            }else{
                sellerIs=2;
            }

            ObservableList<String> usuarioAagregar = FXCollections.observableArrayList();
            usuarioAagregar.setAll(this.name.getText(),this.nickname.getText(),""+administrador,""+sellerIs);
            Conexion connection = new Conexion();
            connection.establecerConexion();
            Usuarios agregarUsuario = new Usuarios();
            if (id==0){ //AGREGAR
                seAgrego = agregarUsuario.agregarusaurio(connection.getConnection(),usuarioAagregar); // Esta variable se leera en el controlador principal que esta llamando este formulario

        }else{
                usuarioAagregar.add(this.id+"");
               seActualizo= agregarUsuario.editarUsuario(connection.getConnection(),usuarioAagregar);
            }
            user = new Usuarios(
                    usuarioAagregar.get(0),
                    usuarioAagregar.get(1),
                    (seAgrego > 0 ? seAgrego : id),
                    administrador,
                    sellerIs,
                    (sellerIs==1 ? "Vendedor" : "Deshabilitado"),
                    (administrador==1 ? "Administrador":"Normal")
            );
        }
    }


    Usuarios returnUsuario(){

        return user;

    }

}
