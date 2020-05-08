package org.example;

import java.io.IOException;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.Conexion;
import org.modelos.Usuarios;

public class loginController {



    @FXML
    StackPane stackDialog = new StackPane();
    Conexion conexion = new Conexion();
    @FXML
    JFXTextField nicknametxt = new JFXTextField();
    @FXML
    JFXPasswordField pwdtxt = new JFXPasswordField();

    Usuarios usersdb = new Usuarios();
    @FXML
    private void switchToSecondary() throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
    if (!nicknametxt.getText().equals("") && !pwdtxt.getText().equals("")){
        ObservableList<String> credentials = FXCollections.observableArrayList();
        credentials.setAll(nicknametxt.getText(),pwdtxt.getText());
        conexion.establecerConexion();
        Boolean isCoorect = usersdb.login(conexion.getConnection(),credentials);
        if (isCoorect){
            App.setRoot("index");
        }else{
            content.setHeading(new Text("Error"));
            content.setBody(new Text("Las credenciales no son correctas!"));

            JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
        }
    }else{

        content.setHeading(new Text("Error"));
        content.setBody(new Text("Ingrese un nombre de usuario y su contraseña por favor!"));

        JFXDialog dialog=new JFXDialog(stackDialog, content, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        System.out.println("no hay nada en los textbox");
    }
    }




    /**
     * SYSTEM
     * */

    @FXML private HBox titleBar;

    double x,y;

    @FXML
    void pressed(MouseEvent event){
        x = event.getSceneX();
        y=event.getSceneY();
    }
    @FXML
    void dragged(MouseEvent  event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }
    @FXML
    void close(MouseEvent event){
        Stage stage = (Stage)  ((Node)  event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void min(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
