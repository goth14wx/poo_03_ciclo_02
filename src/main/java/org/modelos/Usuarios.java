package org.modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class Usuarios {

    private static Statement statement;
    public SimpleStringProperty name;
    public SimpleStringProperty nickname;
    public SimpleIntegerProperty id;
    public SimpleIntegerProperty isAdmin;
    public SimpleIntegerProperty isSeller;
    public SimpleStringProperty admin;
    public SimpleStringProperty seller;



    public String getAdmin() {
        return admin.get();
    }

    public SimpleStringProperty adminProperty() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin.set(admin);
    }

    public String getSeller() {
        return seller.get();
    }

    public SimpleStringProperty sellerProperty() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller.set(seller);
    }

    public Usuarios(String name, String nickname, Integer id, Integer isAdmin, Integer isSeller, String seller, String admin) {
        this.name = new SimpleStringProperty(name);
        this.nickname = new SimpleStringProperty(nickname);
        this.id = new SimpleIntegerProperty(id);
        this.isAdmin = new SimpleIntegerProperty(isAdmin);
        this.isSeller = new SimpleIntegerProperty(isSeller);
        this.seller = new SimpleStringProperty(seller);
        this.admin = new SimpleStringProperty(admin);
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
                            + "id,nickname, isAdmin,isSeller,"
                            + "IF(isAdmin = 1,'Administrador','Vendedor') AS 'admin',"
                            + "IF(isSeller = 1,'Vendedor','Deshabilitado') AS 'seller'"
                            + "FROM users"
            );
            while (resultado.next()){
                lista.add(
                        new Usuarios(
                                resultado.getString("name"),
                                resultado.getString("nickname"),
                                resultado.getInt("id"),
                                resultado.getInt("isAdmin"),
                                resultado.getInt("isSeller"),
                                resultado.getString("seller"),
                                resultado.getString("admin")
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



    public Integer agregarusaurio(Connection Localconnection, ObservableList<String> usuario) {
        Integer id = -1;
        try{
            String querySQL = "INSERT INTO `users` ( `nickname`,`name`, `isAdmin`, `isSeller`)" +
                    "VALUES ( '"+usuario.get(0)+"','"+usuario.get(1)+"', "+usuario.get(2)+", "+usuario.get(3)+");";
            //System.out.println(querySQL);
            PreparedStatement Localstatement = Localconnection.prepareStatement(querySQL,Statement.RETURN_GENERATED_KEYS);
            int affectedRows = Localstatement.executeUpdate();
            ResultSet generatedKeys = Localstatement.getGeneratedKeys();
            if(generatedKeys.next()){
                id= generatedKeys.getInt(1);
            }
        }catch (Exception e){
            id= -2;
        }
        return id;
    }

    public Integer eliminarUsuario(Connection Localconnection,Integer id){
        Integer idS = -1;
        try{
            String querySQL = "DELETE FROM users where id = "+id;
            //System.out.println(querySQL);
            PreparedStatement Localstatement = Localconnection.prepareStatement(querySQL,Statement.RETURN_GENERATED_KEYS);
            int affectedRows = Localstatement.executeUpdate();
            if(affectedRows>=0){
                idS=1;
            }
        }catch (Exception e){
            id= -2;
        }
        return idS;
    }

    public boolean editarUsuario(Connection connection, ObservableList<String> usuario) throws SQLException {
      Boolean returnThis = false;
        try{
            String querySQL = "UPDATE `users` SET " +
                    "`nickname` = ?," +
                    "    `name` = ?," +
                    " `isAdmin` = ?," +
                    "`isSeller` = ? where id = "+usuario.get(4);
            //System.out.println(querySQL);
            PreparedStatement Localstatement = connection.prepareStatement(querySQL,Statement.RETURN_GENERATED_KEYS);
            Localstatement.setString(1,usuario.get(1));
            Localstatement.setString(2,usuario.get(0));
            Localstatement.setString(3,usuario.get(2));
            Localstatement.setString(4,usuario.get(3));
            Localstatement.executeUpdate();
            returnThis = true;
        }catch (Exception e){
            throw  e;
            //returnThis = false;
        }
        return  returnThis;
    }
}
