package org.users;

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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class UsersTableController implements Initializable {

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
    TableView<Usuarios> usersTable = new TableView<Usuarios>();
    @FXML public TableColumn<Usuarios, String> tcId;
    @FXML public TableColumn<Usuarios,String> tcNombre;
    @FXML public TableColumn<Usuarios,String> tcNickname;
    @FXML public TableColumn<Usuarios,String> tcAcceso;
    @FXML public TableColumn<Usuarios,String> tcTipo;
    private ObservableList<Usuarios> usuariosList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Conexion connection = new Conexion();
        connection.establecerConexion();
        this.setTable();
        Usuarios llenarTabla = new Usuarios();
        connection.establecerConexion();
        new Thread(new Runnable() {
            public void run(){

                llenarTabla.llenarInformacion(connection.getConnection(),getUsuariosLista());
            }
        }).start();
        this.btnEdit.setDisable(true);
        this.btnDelete.setDisable(true);
    }

    ObservableList<Usuarios> getUsuariosLista(){
        return this.usuariosList;
    }


    public void setTable(){
        this.tcId.setCellValueFactory(new PropertyValueFactory<Usuarios,String>("id"));
        /**
         * LAMNDA x
         * */
        this.tcNombre.setCellValueFactory(nombre->nombre.getValue().nameProperty());
        this.tcNickname.setCellValueFactory(nickname->nickname.getValue().nicknameProperty());
        this.tcAcceso.setCellValueFactory(new PropertyValueFactory<Usuarios,String>("admin"));
        this.tcTipo.setCellValueFactory(new PropertyValueFactory<Usuarios,String>("seller"));
        this.usersTable.setItems(this.getUsuariosLista());
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
            this.getUser =this.usersTable.getSelectionModel().getSelectedIndex();
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
    void agregarUsuario() throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        FXMLLoader Formulario = new FXMLLoader(getClass().getResource("/org.users/UserForm.fxml"));
        content.setHeading(new Text("Agregar Usuario"));
        AnchorPane formularioAnchor = Formulario.load();
        formUserController formularioController = Formulario.getController();
        content.setBody(formularioAnchor);
        JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);

        formularioController.btnAgregar.setOnMouseClicked(e->{
            if (formularioController.seAgrego>0){ // se agrego
                usuariosList.add(formularioController.returnUsuario());
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
    void EliminarUsuario(){
        JFXDialogLayout content = new JFXDialogLayout();
        Conexion connection = new Conexion();
        connection.establecerConexion();
        if (this.getUser>-1){
            Usuarios eliminar = new Usuarios();
            Integer seElimino =eliminar.eliminarUsuario(connection.getConnection(),this.usuariosList.get(this.getUser).getId());
            if (seElimino>=0){
                this.usuariosList.remove(this.usuariosList.get(getUser));
                //System.out.println(this.getUser);
                //System.out.println(this.usuariosList.size());
                content.setHeading(new Text("usuario Eliminado"));
                content.setBody(new Text("El usuario se ha eliminado correctamente"));
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
    void editUsuario() throws IOException {

        if (this.getUser>-1){

            JFXDialogLayout content = new JFXDialogLayout();
            FXMLLoader Formulario = new FXMLLoader(getClass().getResource("/org.users/UserForm.fxml"));
            content.setHeading(new Text("Editar Usuario"));
            AnchorPane formularioAnchor = Formulario.load();
            formUserController formularioController = Formulario.getController();
            content.setBody(formularioAnchor);
            formularioController.name.setText(this.usuariosList.get(getUser).getName());
            formularioController.nickname.setText(this.usuariosList.get(getUser).getNickname());
            formularioController.admin.setSelected((this.usuariosList.get(getUser).getIsAdmin()==1 ? true : false));
            formularioController.seller.setSelected((this.usuariosList.get(getUser).getIsSeller()==1 ? true : false));
            formularioController.idlbl.setText("usuario id:"+this.usuariosList.get(getUser).getId());
            formularioController.id = this.usuariosList.get(getUser).getId();
            JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();

            formularioController.btnAgregar.setOnMouseClicked(e->{

                if (formularioController.seActualizo){ // se actualizo
                    usuariosList.get(this.getUser).setName(formularioController.returnUsuario().getName());
                    usuariosList.get(this.getUser).setNickname(formularioController.returnUsuario().getNickname());
                    usuariosList.get(this.getUser).setId(formularioController.returnUsuario().getId());
                    usuariosList.get(this.getUser).setAdmin(formularioController.returnUsuario().getAdmin());
                    usuariosList.get(this.getUser).setIsAdmin(formularioController.returnUsuario().getIsAdmin());
                    usuariosList.get(this.getUser).setSeller(formularioController.returnUsuario().getSeller());
                    usuariosList.get(this.getUser).setIsSeller(formularioController.returnUsuario().getIsSeller());

                    formularioController.seAgrego=-1;
                    formularioController.seActualizo=false;
                    dialog.close();
                    content.setHeading(new Text("Usuario Actualizado"));
                    content.setBody(new Text("el Usuario "+usuariosList.get(this.getUser).getName()+" se " +
                            "ha actualizado correctamente!!"));
                    JFXDialog dialogAlert=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
                    dialogAlert.show();

                }

            });

        }

        this.setButtons(false);
    }

}
