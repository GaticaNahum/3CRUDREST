package mx.edu.utez.model;

import mx.edu.utez.util.ConnectionMysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProduct_Lines {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;


    public List<Product_Lines> findAll(){
        List<Product_Lines> productLinesArrayList = new ArrayList<>();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT productLine,textDescription,htmlDescription,image FROM productlines;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Product_Lines productLines = new Product_Lines();
                productLines.setProductLine(rs.getString("productLine"));
                productLines.setTextDescription(rs.getString("textDescription"));
                productLines.setHtmlDescription(rs.getString("htmlDescription"));
                productLines.setImage(rs.getString("image"));
                productLinesArrayList.add(productLines);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return productLinesArrayList;
    }

    public Product_Lines findById(String productLine){
        Product_Lines productLines = new Product_Lines();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT productLine,textDescription,htmlDescription,image FROM productlines WHERE productLine = ?";
            pstm = con.prepareStatement(query);
            pstm.setString(1,productLine);
            rs = pstm.executeQuery();
            if (rs.next()){
                productLines.setProductLine(rs.getString("productLine"));
                productLines.setTextDescription(rs.getString("textDescription"));
                productLines.setHtmlDescription(rs.getString("htmlDescription"));
                productLines.setImage(rs.getString("image"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return productLines;
    }

    public boolean insertProductLine(Product_Lines productLines, boolean insert){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            if(insert){
                String query = "INSERT INTO productlines(productLine,textDescription,htmlDescription,image) values(?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, productLines.getProductLine());
                pstm.setString(2, productLines.getTextDescription());
                pstm.setString(3, productLines.getHtmlDescription());
                pstm.setString(4, productLines.getImage());
            }else{
                String query = "UPDATE productlines SET textDescription =?,htmlDescription =?,image =? WHERE productlines = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, productLines.getTextDescription());
                pstm.setString(2, productLines.getHtmlDescription());
                pstm.setString(3, productLines.getImage());
                pstm.setString(4, productLines.getProductLine());
            }
            state = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return state;
    }

    public boolean delete(String productLine){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            String query = "delete from productlines where  = productLine ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, productLine);
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
