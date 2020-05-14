package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class indexcontroller implements Initializable {

    @FXML
    AnchorPane mainStage = new AnchorPane();

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.showDashboardStage();
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void showUsuariosStage() throws IOException {
        System.out.println("mostrando usuarios");
        StackPane usersAnchor = FXMLLoader.load(getClass().getResource("/org.users/users.table.fxml"));
        this.mainStage.getChildren().setAll(usersAnchor);
        new FadeInRight(this.mainStage).play();
    }

    @FXML
    void showDashboardStage() throws IOException {
        System.out.println("mostrando dashboard");
        try {

            AnchorPane usersAnchor = FXMLLoader.load(getClass().getResource("/org/example/dashboard.fxml"));
            this.mainStage.getChildren().setAll(usersAnchor);
            new FadeInRight(this.mainStage).play();
        }catch (Exception e){
            throw e;
        }
    }

    @FXML
    void showProveedoresStage() throws IOException {
        System.out.println("mostrando proveedores");
        try {
            StackPane usersAnchor = FXMLLoader.load(getClass().getResource("/org.proveedores/proveedores.fxml"));
            this.mainStage.getChildren().setAll(usersAnchor);
            new FadeInRight(this.mainStage).play();
        }catch (Exception e){
            throw e;
        }
    }

    @FXML
    void showProductsStage() throws IOException {
        System.out.println("mostrando productos");
        try {
            StackPane usersAnchor = FXMLLoader.load(getClass().getResource("/org.products/productos.fxml"));
            this.mainStage.getChildren().setAll(usersAnchor);
            new FadeInRight(this.mainStage).play();
        }catch (Exception e){
            throw e;
        }
    }
}