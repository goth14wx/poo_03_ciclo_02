package org.modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class product {

    SimpleStringProperty nombre;
    SimpleStringProperty id;
    SimpleStringProperty descripcion;
    SimpleStringProperty precio;
    SimpleStringProperty stock;
    SimpleStringProperty idProveedor;
    SimpleStringProperty proveedor;

    public product(String nombre, String id, String descripcion,
                   String precio, String stock, String idProveedor,String proveedor) {
        this.nombre = new SimpleStringProperty(nombre);
        this.id = new SimpleStringProperty(id);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleStringProperty(precio);
        this.stock = new SimpleStringProperty(stock);
        this.idProveedor = new SimpleStringProperty(idProveedor);
        this.proveedor = new SimpleStringProperty(proveedor);
    }

    public product(){

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

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getPrecio() {
        return precio.get();
    }

    public SimpleStringProperty precioProperty() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio.set(precio);
    }

    public String getStock() {
        return stock.get();
    }

    public SimpleStringProperty stockProperty() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock.set(stock);
    }

    public String getIdProveedor() {
        return idProveedor.get();
    }

    public SimpleStringProperty idProveedorProperty() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor.set(idProveedor);
    }

    public String getProveedor() {
        return proveedor.get();
    }

    public SimpleStringProperty proveedorProperty() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor.set(proveedor);
    }

    /**
     * CONSULTAS A LA BASE DE DATOS
     * */

    public Integer agregarProducto(Connection Localconnection, ObservableList<String> producto) throws SQLException {
        Integer id = -1;
        try{
            String querySQL = "INSERT INTO `suppliers` ( `name`,`desc`)" +
                    "VALUES ( '"+producto.get(0)+"','"+producto.get(1)+"');";
            PreparedStatement Localstatement = Localconnection.prepareStatement(querySQL, Statement.RETURN_GENERATED_KEYS);
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

    public static void llenarInformacion(Connection Localconnection, ObservableList<product> lista){
        try {
            Statement Localstatement = Localconnection.createStatement();
            String sql = "select products.id as 'id',products.name as 'name'," +
                    "precio,descripcion,stock,suppliers.name as 'proveedor',supplier_product" +
                    ".suppliers_id as 'idProveedor' " +
                    "from products " +
                    "left join supplier_product " +
                    "on supplier_product.products_id = products.id " +
                    "left join suppliers " +
                    "on suppliers.id = supplier_product.suppliers_id;";
            System.out.println(sql);
            ResultSet resultado = Localstatement.executeQuery(sql);
            while (resultado.next()){
                lista.add(
                        new product(
                                resultado.getString("name"),
                                resultado.getString("id"),
                                resultado.getString("descripcion"),
                                resultado.getString("precio"),
                                resultado.getString("stock"),
                                resultado.getString("idProveedor"),
                                resultado.getString("proveedor")
                        )
                );
            }
            System.out.println("done");
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
