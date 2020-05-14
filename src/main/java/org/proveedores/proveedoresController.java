package org.proveedores;

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
import org.modelos.Usuarios;
import org.modelos.proveedores;
import org.users.formUserController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class proveedoresController implements Initializable {

    Integer getUser = -1;

    @FXML
    Button btnAdd = new Button();
    @FXML
    Button btnDelete = new Button();
    @FXML
    Button btnEdit = new Button();


    @FXML
    StackPane stackDialog;

    @FXML
    TableView<proveedores> proveedoresTable = new TableView<proveedores>();
    @FXML public TableColumn<proveedores, String> tcId;
    @FXML public TableColumn<proveedores,String> tcNombre;
    @FXML public TableColumn<proveedores,String> tcDescripcion;
    private ObservableList<proveedores> proveedoresList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Conexion connection = new Conexion();
        connection.establecerConexion();
        this.setTable();
        proveedores llenarTabla = new proveedores();
        connection.establecerConexion();
        new Thread(new Runnable() {
            public void run(){
                llenarTabla.llenarInformacion(connection.getConnection(),getProveedoresList());
            }
        }).start();
        this.setButtons(false);
    }

    ObservableList<proveedores> getProveedoresList(){
        return this.proveedoresList;
    }


    public void setTable(){
        this.tcId.setCellValueFactory(new PropertyValueFactory<proveedores,String>("id"));
        this.tcNombre.setCellValueFactory(nombre->nombre.getValue().nombreProperty());
        this.tcDescripcion.setCellValueFactory(nickname->nickname.getValue().descripcionProperty());
        this.proveedoresTable.setItems(this.getProveedoresList());
    }

    @FXML
    void deleteUsuario(){
        if(this.getUser>-1){

            this.getUser=-1;
        }
    }

    @FXML
    void selectUser(){

        try {
            this.getUser =this.proveedoresTable.getSelectionModel().getSelectedIndex();
            System.out.println("seleccionado Index "+this.getUser);
            this.btnEdit.setDisable(false);
            this.btnDelete.setDisable(false);
        }catch (Exception e){

        }
    }

    void setButtons(Boolean tipe){
        this.btnEdit.setDisable(tipe);
        this.btnDelete.setDisable(tipe);
    }

    @FXML
    void agregarProveedor() throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        FXMLLoader Formulario = new FXMLLoader(getClass().getResource("/org.proveedores/proveedorForm.fxml"));
        content.setHeading(new Text("Agregar proveedor"));
        AnchorPane formularioAnchor = Formulario.load();
        formProveedorController formularioController = Formulario.getController();
        content.setBody(formularioAnchor);
        System.out.println("sin problemas");
        JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);

       formularioController.btnAgregar.setOnMouseClicked(e->{
            if (formularioController.seAgrego>0){ // se agrego
                proveedoresList.add(formularioController.returnProveedor());
                formularioController.seAgrego=-1;
                dialog.close();
                content.setHeading(new Text("Usuario agregado"));
                content.setBody(new Text("Se agrego correctamente!!"));
                JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                dialogAlert.show();
            }
        });

        dialog.show();
    }

    @FXML
    void eliminarProveedor() throws SQLException {
        JFXDialogLayout content = new JFXDialogLayout();
        Conexion connection = new Conexion();
        connection.establecerConexion();
        if (this.getUser>-1){
            proveedores eliminar = new proveedores();
            Integer seElimino =eliminar.eliminarProveedor(connection.getConnection(),Integer.parseInt(this.proveedoresList.get(this.getUser).getId()));
            if (seElimino>=0){
                this.proveedoresList.remove(this.proveedoresList.get(getUser));
                //System.out.println(this.getUser);
                //System.out.println(this.proveedoresList.size());
                content.setHeading(new Text("Proveedor Eliminado"));
                content.setBody(new Text("El proveedor se ha eliminado correctamente"));
                JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                this.getUser = -1;
                dialogAlert.show();
            }else{
                content.setHeading(new Text("Error"));
                content.setBody(new Text("Parece que no se pudo realizar la petición"));
                JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                dialogAlert.show();
            }


        }
        this.setButtons(false);
    }

    @FXML
    void editProveedor() throws IOException {

        if (this.getUser>-1){

            JFXDialogLayout content = new JFXDialogLayout();
            FXMLLoader Formulario = new FXMLLoader(getClass().getResource("/org.proveedores/proveedorForm.fxml"));
            content.setHeading(new Text("Editar Proveedor"));
            AnchorPane formularioAnchor = Formulario.load(); // PRIMERO DEBE DE CARGARSE EL FORMULARIO
            formProveedorController formularioController = Formulario.getController(); // PARA DESPUES TOMAR EL CONTROLADOR
            content.setBody(formularioAnchor);
            formularioController.name.setText(this.proveedoresList.get(getUser).getNombre());
            formularioController.desc.setText(this.proveedoresList.get(getUser).getDescripcion());
            formularioController.idlbl.setText("Proveedor id:"+this.proveedoresList.get(getUser).getId());
            formularioController.id = Integer.parseInt(this.proveedoresList.get(getUser).getId());
            JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();

            formularioController.btnAgregar.setOnMouseClicked(e->{

                if (formularioController.seActualizo){ // se actualizo
                    proveedoresList.get(this.getUser).setNombre(formularioController.returnProveedor().getNombre());
                    proveedoresList.get(this.getUser).setId(formularioController.returnProveedor().getId());
                    proveedoresList.get(this.getUser).setDescripcion(formularioController.returnProveedor().getDescripcion());

                    formularioController.seAgrego=-1;
                    formularioController.seActualizo=false;
                    dialog.close();
                    content.setHeading(new Text("Proveedor Actualizado"));
                    content.setBody(new Text("el Proveedor "+proveedoresList.get(this.getUser).getNombre()+" se " +
                            "ha actualizado correctamente!!"));
                    JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                    dialogAlert.show();
                }

            });

        }

        this.setButtons(false);
    }

}
