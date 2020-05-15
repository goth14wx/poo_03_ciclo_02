package org.products;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import org.Conexion;
import org.modelos.product;
import org.modelos.proveedores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductsForm implements Initializable {

    public int id = 0;
    @FXML
    public Label idlbl = new Label();
    @FXML
    public Button btnAgregar = new Button();
    @FXML
    public JFXTextField txtDesc = new JFXTextField();
    @FXML
    public JFXTextField txtName = new JFXTextField();
    @FXML
    public Spinner<Double> spPrecio = new Spinner<Double>();
    @FXML
    public Spinner<Integer> spStock = new Spinner<Integer>();
    @FXML
    public JFXComboBox<proveedores> cbProveedor;

    Integer seAgrego=0;
    Boolean seActualizo = false;


    org.modelos.product product;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void agregarProveedor() throws SQLException {

        if (!this.txtName.getText().equals("") && !this.txtDesc.getText().equals("")){
            System.out.println("Agregando");
            ObservableList<String> proveedorAgregar = FXCollections.observableArrayList();
            proveedorAgregar.setAll(this.txtName.getText(),this.txtDesc.getText());
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
           /* product = new product(
                    (seAgrego > 0 ? seAgrego.toString() : id+"" ),
                    proveedorAgregar.get(0),
                    proveedorAgregar.get(1)
            );*/
        }
    }


    public product returnProduct(){

        return product;

    }

}
