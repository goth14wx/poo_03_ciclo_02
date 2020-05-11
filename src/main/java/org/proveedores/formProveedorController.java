package org.proveedores;

import com.jfoenix.controls.JFXTextArea;
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
import org.modelos.proveedores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class formProveedorController implements Initializable {


    public int id = 0;
    @FXML
    public Label idlbl = new Label();
    @FXML
    public Button btnAgregar = new Button();
    @FXML
    public JFXTextArea desc = new JFXTextArea();
    @FXML
    public JFXTextField name = new JFXTextField();
    @FXML
    public JFXToggleButton admin = new JFXToggleButton();
    @FXML
    public JFXToggleButton seller = new JFXToggleButton();

    Integer seAgrego=0;
    Boolean seActualizo = false;


    proveedores proveedor;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.seller.setSelected(true);

    }


    @FXML
    void agregarProveedor() throws SQLException {

        if (!this.name.getText().equals("") && !this.desc.getText().equals("")){
            System.out.println("Agregando");
            ObservableList<String> proveedorAgregar = FXCollections.observableArrayList();
            proveedorAgregar.setAll(this.name.getText(),this.desc.getText());
            Conexion connection = new Conexion();
            connection.establecerConexion();
            proveedores agregarProveedor = new proveedores();
            if (id==0){ //AGREGAR
                seAgrego = agregarProveedor.agregarProveedor(connection.getConnection(),proveedorAgregar); // Esta variable se leera en el controlador principal que esta llamando este formulario

            }else{

                System.out.println("Actualizando");
                proveedorAgregar.add(this.id+"");
                seActualizo= agregarProveedor.editarProveedor(connection.getConnection(),proveedorAgregar);
            }
            proveedor = new proveedores(
                    (seAgrego > 0 ? seAgrego.toString() : id+"" ),
                    proveedorAgregar.get(0),
                    proveedorAgregar.get(1)
            );
        }
    }


    proveedores returnProveedor(){

        return proveedor;

    }

}
