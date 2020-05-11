package org.modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class proveedores {

    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty descripcion;

    public proveedores(String id, String nombre, String descripcion) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public proveedores() {

    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }


    /**
     * CONSULTAS A LA BASE DE DATOS
     * */

    public Integer agregarProveedor(Connection Localconnection, ObservableList<String> proveedor) throws SQLException {
        Integer id = -1;
        try{
            String querySQL = "INSERT INTO `suppliers` ( `name`,`desc`)" +
                    "VALUES ( '"+proveedor.get(0)+"','"+proveedor.get(1)+"');";
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

    public static void llenarInformacion(Connection Localconnection, ObservableList<proveedores> lista){
        try {
            Statement Localstatement = Localconnection.createStatement();
            ResultSet resultado = Localstatement.executeQuery("SELECT * FROM suppliers");
            while (resultado.next()){
                lista.add(
                        new proveedores(
                                resultado.getString("id"),
                                resultado.getString("name"),
                                resultado.getString("desc")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public Integer eliminarProveedor(Connection Localconnection,Integer id) throws SQLException {
        Integer idS = -1;
        try{
            String querySQL = "DELETE FROM suppliers where id = "+id;
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

    public boolean editarProveedor(Connection connection, ObservableList<String> proveedor) throws SQLException {
        Boolean returnThis = false;
        try{
            String querySQL = "UPDATE `suppliers` SET " +
                    "    `name` = ?," +
                    " `desc` = ? where id = "+proveedor.get(2);
            //System.out.println(querySQL);
            PreparedStatement Localstatement = connection.prepareStatement(querySQL,Statement.RETURN_GENERATED_KEYS);
            Localstatement.setString(1,proveedor.get(0));
            Localstatement.setString(2,proveedor.get(1));
            Localstatement.executeUpdate();
            returnThis = true;
        }catch (Exception e){
            throw  e;
            //returnThis = false;
        }
        return  returnThis;
    }



}
