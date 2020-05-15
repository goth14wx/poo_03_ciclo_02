package org.products;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.Conexion;
import org.modelos.product;
import org.proveedores.formProveedorController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class productosController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Conexion connection = new Conexion();
        connection.establecerConexion();
        product llenarTabla = new product();
        connection.establecerConexion();
        this.setTable();
        System.out.println("tabla puesta");
        new Thread(new Runnable() {
            public void run(){
                llenarTabla.llenarInformacion(connection.getConnection(),getProductsList());
            }
        }).start();
    }

    @FXML
    void selectUser(){

        try {
            this.getProduct =this.productsTable.getSelectionModel().getSelectedIndex();
            System.out.println("seleccionado Index "+this.getProduct);
            this.btnEdit.setDisable(false);
            this.btnDelete.setDisable(false);
        }catch (Exception e){

        }
    }

    @FXML
    public void agregarProducto() throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        FXMLLoader Formulario = new FXMLLoader(getClass().getResource("/org.products/productsForm.fxml"));
        content.setHeading(new Text("Agregar Producto"));
        AnchorPane formularioAnchor = Formulario.load();
        ProductsForm formularioController = Formulario.getController();
        content.setBody(formularioAnchor);
        System.out.println("sin problemas");
        JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);

        formularioController.btnAgregar.setOnMouseClicked(e->{
            if (formularioController.seAgrego>0){ // se agrego
                productsList.add(formularioController.returnProduct());
                formularioController.seAgrego=-1;
                dialog.close();
                content.setHeading(new Text("Producto agregado"));
                content.setBody(new Text("Se agrego correctamente!!"));
                JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                dialogAlert.show();
            }
        });

        dialog.show();
    }

    @FXML
    public void eliminarProducto(){

    }

    @FXML
    public void editarProducto(){

    }

    Integer getProduct = -1;

    @FXML
    Button btnAdd = new Button();
    @FXML
    Button btnDelete = new Button();
    @FXML
    Button btnEdit = new Button();


    @FXML
    StackPane stackDialog;

    @FXML
    TableView<product> productsTable = new TableView<product>();
    @FXML public TableColumn<product, String> tcId;
    @FXML public TableColumn<product,String> tcNombre;
    @FXML public TableColumn<product,String> tcDescripcion;
    @FXML public TableColumn<product,String> tcPrecio;
    @FXML public TableColumn<product,String> tcStock;
    @FXML public TableColumn<product,String> tcProveedor;
    @FXML public TableColumn<product,String> tcIdProveedor;
    ObservableList<product> productsList = FXCollections.observableArrayList();
    public void setTable(){
        this.tcId.setCellValueFactory(new PropertyValueFactory<product,String>("id"));
        this.tcNombre.setCellValueFactory(nombre->nombre.getValue().nombreProperty());
        this.tcDescripcion.setCellValueFactory(descripcion->descripcion.getValue().descripcionProperty());
        this.tcPrecio.setCellValueFactory(precio->precio.getValue().precioProperty());
        this.tcStock.setCellValueFactory(stock->stock.getValue().stockProperty());
        this.tcProveedor.setCellValueFactory(proveedor->proveedor.getValue().proveedorProperty());
        this.tcIdProveedor.setCellValueFactory(idProveedor->idProveedor.getValue().idProveedorProperty());
        this.productsTable.setItems(this.getProductsList());
    }

    ObservableList<product> getProductsList(){

    return this.productsList;

    }
}
