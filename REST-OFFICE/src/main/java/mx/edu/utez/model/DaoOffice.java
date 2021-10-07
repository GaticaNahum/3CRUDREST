package mx.edu.utez.model;

import mx.edu.utez.util.ConnectionMysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoOffice {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<Office> findAll(){
        List<Office> offices = new ArrayList<>();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT officeCode,city,phone,addressLine1,addressLine2, state, country, postalCode, territory FROM offices;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Office office = new Office();
                office.setOfficeCode(rs.getString("officeCode"));
                office.setCity(rs.getString("city"));
                office.setPhone(rs.getString("phone"));
                office.setAddressLine1(rs.getString("addressLine1"));
                office.setAddressLine2(rs.getString("addressLine2"));
                office.setState(rs.getString("state"));
                office.setCountry(rs.getString("country"));
                office.setPostalCode(rs.getString("postalCode"));
                office.setTerritory(rs.getString("territory"));
                offices.add(office);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return offices;
    }

    public Office findById(String officeCode){
        Office office = new Office();
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT officeCode,city,phone,addressLine1,addressLine2, state, country, postalCode, territory FROM offices WHERE officeCode = ?";
            pstm = con.prepareStatement(query);
            pstm.setString(1,officeCode);
            rs = pstm.executeQuery();
            if (rs.next()){
                office.setOfficeCode(rs.getString("officeCode"));
                office.setCity(rs.getString("city"));
                office.setPhone(rs.getString("phone"));
                office.setAddressLine1(rs.getString("addressLine1"));
                office.setAddressLine2(rs.getString("addressLine2"));
                office.setState(rs.getString("state"));
                office.setCountry(rs.getString("country"));
                office.setPostalCode(rs.getString("postalCode"));
                office.setTerritory(rs.getString("territory"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return office;
    }

    public boolean insertOffice(Office office, boolean insert){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            if(insert){
                String query = "INSERT INTO offices(officeCode,city,phone,addressLine1,addressLine2, state, country, postalCode, territory) values(?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, office.getOfficeCode());
                pstm.setString(2, office.getCity());
                pstm.setString(3, office.getPhone());
                pstm.setString(4, office.getAddressLine1());
                pstm.setString(5, office.getAddressLine2());
                pstm.setString(6, office.getState());
                pstm.setString(7, office.getCountry());
                pstm.setString(8, office.getPostalCode());
                pstm.setString(9, office.getTerritory());
            }else{
                String query = "UPDATE offices SET city =?,phone =?,addressLine1 =?,addressLine2 =?,state =?,country =?,postalCode =?,territory =? WHERE officeCode = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, office.getCity());
                pstm.setString(2, office.getPhone());
                pstm.setString(3, office.getAddressLine1());
                pstm.setString(4, office.getAddressLine2());
                pstm.setString(5, office.getState());
                pstm.setString(6, office.getCountry());
                pstm.setString(7, office.getPostalCode());
                pstm.setString(8, office.getTerritory());
                pstm.setString(9, office.getOfficeCode());

            }
            state = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return state;
    }

    public boolean delete(String officeCode){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            String query = "delete from offices where  = officeCode ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, officeCode);
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
