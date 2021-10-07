package mx.edu.utez.model;

import mx.edu.utez.util.ConnectionMysql;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProducts {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<Products> findAll(){
        List<Products> customers = new ArrayList<>();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT productCode,productName,productLine,productScale,productVendor, productDescription, quantityInStock, buyPrice, MSRP FROM products;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Products products = new Products();
                products.setProductCode(rs.getString("productCode"));
                products.setProductName(rs.getString("productName"));
                products.setProductLine(rs.getString("productLine"));
                products.setProductScale(rs.getString("productScale"));
                products.setProductVendor(rs.getString("productVendor"));
                products.setProductDescription(rs.getString("productDescription"));
                products.setQuantityInStock(rs.getString("quantityInStock"));
                products.setBuyPrice(rs.getString("buyPrice"));
                products.setMSRP(rs.getString("MSRP"));
                customers.add(products);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return customers;
    }

    public Products findById(String productCode){
        Products products = new Products();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT productCode,productName,productLine,productScale,productVendor, productDescription, quantityInStock, buyPrice, MSRP FROM products WHERE productCode = ?";
            pstm = con.prepareStatement(query);
            pstm.setString(1,productCode);
            rs = pstm.executeQuery();
            if (rs.next()){
                products.setProductCode(rs.getString("productCode"));
                products.setProductName(rs.getString("productName"));
                products.setProductLine(rs.getString("productLine"));
                products.setProductScale(rs.getString("productScale"));
                products.setProductVendor(rs.getString("productVendor"));
                products.setProductDescription(rs.getString("productDescription"));
                products.setQuantityInStock(rs.getString("quantityInStock"));
                products.setBuyPrice(rs.getString("buyPrice"));
                products.setMSRP(rs.getString("MSRP"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return products;
    }

    public boolean insertProduct(Products products, boolean insert){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            if(insert){
                String query = "INSERT INTO products(productCode,productName,productLine,productScale,productVendor, productDescription, quantityInStock, buyPrice, MSRP) values(?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, products.getProductCode());
                pstm.setString(2, products.getProductName());
                pstm.setString(3, products.getProductLine());
                pstm.setString(4, products.getProductScale());
                pstm.setString(5, products.getProductVendor());
                pstm.setString(6, products.getProductDescription());
                pstm.setString(7, products.getQuantityInStock());
                pstm.setString(8, products.getBuyPrice());
                pstm.setString(9, products.getMSRP());
            }else{
                String query = "UPDATE products SET productName =?,productLine =?,productScale =?,productVendor =?,productDescription =?,quantityInStock =?,buyPrice =?,MSRP =? WHERE products = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, products.getProductName());
                pstm.setString(2, products.getProductLine());
                pstm.setString(3, products.getProductScale());
                pstm.setString(4, products.getProductVendor());
                pstm.setString(5, products.getProductDescription());
                pstm.setString(6, products.getQuantityInStock());
                pstm.setString(7, products.getBuyPrice());
                pstm.setString(8, products.getMSRP());
                pstm.setString(9, products.getProductCode());
            }
            state = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return state;
    }

    public boolean delete(String productCode){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            String query = "delete from products where  = productCode ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, productCode);
            state = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return state;
    }

    public void closeConnection(){
        try{
            if(con != null){
                con.close();
            }
            if(pstm != null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
            if(statement != null){
                statement.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
