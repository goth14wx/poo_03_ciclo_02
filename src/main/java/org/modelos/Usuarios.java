package org.modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuarios {

    private static Statement statement;
    public SimpleStringProperty name;
    public SimpleStringProperty nickname;
    public SimpleIntegerProperty id;
    public SimpleIntegerProperty isAdmin;
    public SimpleIntegerProperty isSeller;
    public SimpleIntegerProperty admin;
    public SimpleIntegerProperty seller;

    public Usuarios(String name, String nickname, Integer id, Integer isAdmin, Integer isSeller) {
        this.name = new SimpleStringProperty(name);
        this.nickname = new SimpleStringProperty(nickname);
        this.id = new SimpleIntegerProperty(id);
        this.isAdmin = new SimpleIntegerProperty(isAdmin);
        this.isSeller = new SimpleIntegerProperty(isSeller);
    }

    public Usuarios() {

    }

    public Usuarios(Connection connection) {

    }

    public static void llenarInformacion(Connection Localconnection, ObservableList<Usuarios> lista){
        try {
            Statement Localstatement = Localconnection.createStatement();
            ResultSet resultado = Localstatement.executeQuery(
                    "SELECT name, "
                            + "nickname, isAdmin,isSeller"
                            + "IF(isAdmin = 1,'Administrador','Vendedor') AS 'admin',"
                            + "IF(isSeller = 1,'Vendedor','Deshabilitado') AS 'seller'"
                            + "FROM javaproductos"
            );
            while (resultado.next()){
                lista.add(
                        new Usuarios(
                                resultado.getString("name"),
                                resultado.getString("nickname"),
                                resultado.getInt("id"),
                                resultado.getInt("isAdmin"),
                                resultado.getInt("isSeller")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public boolean login(Connection Localconnection,ObservableList<String> credentials){

        try{
            Statement Localstatement = Localconnection.createStatement();
            ResultSet resultado = Localstatement.executeQuery("SELECT * FROM users where " +
                    "isAdmin=1 && " +
                    "isSeller=1 && " +
                    "nickname='"+credentials.get(0)+"' && " +
                    "pwd ='"+credentials.get(1)+"'");
            resultado.last();
            if (resultado.getRow()>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNickname() {
        return nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getIsAdmin() {
        return isAdmin.get();
    }

    public SimpleIntegerProperty isAdminProperty() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public int getIsSeller() {
        return isSeller.get();
    }

    public SimpleIntegerProperty isSellerProperty() {
        return isSeller;
    }

    public void setIsSeller(int isSeller) {
        this.isSeller.set(isSeller);
    }
}
