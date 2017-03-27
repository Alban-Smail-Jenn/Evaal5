/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import model.AccessBackoffice;

/**
 *
 * @author happy
 */
public class AccessBackofficeDAO extends DAO<AccessBackoffice, Long> {

    public AccessBackofficeDAO() {
        super();
    }

    /**
     *
     * Create access_backoffice
     *
     * @param access_backoffice
     * @return access_backoffice object
     */
    @Override
    public AccessBackoffice create(AccessBackoffice access_backoffice) {

        AccessBackoffice access_backofficeCreate = new AccessBackoffice();

        if (this.bddmanager.connect()) {

            try {

                // create request
                String requete = "INSERT INTO access_backoffice ( "
                        + "user_id,\n"
                        + " nickname,\n"
                        + " password,\n"
                        + " isAdmin,\n"
                        + " isBlocked,\n"
                        + " hasChanged,\n"
                        + ") VALUES (?,?,MD5(?),?,?,?)";
                
                // prepared request and returned generated key
                PreparedStatement pst = this.bddmanager.getConnectionManager()
                        .prepareStatement(requete);
                
                // insert value's request
                pst.setLong(1, access_backoffice.getUser_id());
                pst.setString(2, access_backoffice.getNickname());
                pst.setString(3, access_backoffice.getPassword());
                pst.setBoolean(4, access_backoffice.isIsAdmin());
                pst.setBoolean(5, access_backoffice.isIsBlocked());
                pst.setBoolean(6, access_backoffice.isHasChanged());
                
                // excute insert row in table
                int insert = pst.executeUpdate();
                
                // Insert in table 
                if (insert != 0) {

                // assign find access_backoffice params primary key user_id in object returned
                    access_backofficeCreate = this.find(access_backoffice.getUser_id());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return access_backofficeCreate;
            }

        } else {
            return access_backofficeCreate;
        }

        return access_backofficeCreate;
    }

    
    @Override
    public boolean update(AccessBackoffice access_backoffice) {
        boolean success = false;

        if (this.bddmanager.connect()) {

            try {

                // create request 
                String requete = "Update access_backoffice set";
                requete += " nickname = ?";
                requete += ", isAdmin = ?";
                requete += ", isBlocked = ?";
                requete += ", hasChanged = ?";
                if (!access_backoffice.getPassword().isEmpty()) {
                    requete += ", password = MD5(?)";
                }
                requete += " WHERE user_id = ?";
                
                // prepared request 
                PreparedStatement pst = this.bddmanager
                        .getConnectionManager().prepareStatement(requete);
                // insert value's request
                pst.setString(1, access_backoffice.getNickname());
                pst.setBoolean(2, access_backoffice.isIsAdmin());
                pst.setBoolean(3, access_backoffice.isIsBlocked());
                pst.setBoolean(4, access_backoffice.isHasChanged());
                if (!access_backoffice.getPassword().isEmpty()) {
                    pst.setString(5, access_backoffice.getPassword());
                }
                if (!access_backoffice.getPassword().isEmpty()) {
                    pst.setLong(6, access_backoffice.getUser_id());
                } else {
                    pst.setLong(5, access_backoffice.getUser_id());
                }

                // execute update row in table
                
                int insert = pst.executeUpdate();
                
                // Insert in table 
                if (insert != 0) {
                    success = true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return success;
            }

        } else {
            return success;
        }
        return success;
    }

   
    @Override
    public boolean delete(Long primary_key) {
        boolean success = false;

        if (this.bddmanager.connect()) {

            try {
                // create request
                String requete = "DELETE FROM access_backoffice WHERE user_id = ?";
                
                // prepared request 
                PreparedStatement pst = this.bddmanager.getConnectionManager()
                        .prepareStatement(requete);
                
                // insert value's request
                pst.setLong(1, primary_key);
                
                // execute delete row in table
                int insert = pst.executeUpdate();
                
                // if delete in table 
                if (insert != 0) {
                    success = true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return success;
            }

        } else {
            return success;
        }
        return success;

    }

    @Override
    public ArrayList getAll() {
        
        // create array list AccessBackoffice empty
        ArrayList<AccessBackoffice> listAccessBackoffice = new ArrayList<>();
        if (this.bddmanager.connect()) {

            try {
                
                // create statement 
                Statement st = this.bddmanager
                        .getConnectionManager()
                        .createStatement();
                
                // create request 
                String requete = "SELECT * FROM access_backoffice";
                
                // execute request
                ResultSet rs = st.executeQuery(requete);
                
                // insert all users's information in arrayobject AccessBackoffice

                while (rs.next()) {
                    AccessBackoffice el = new AccessBackoffice(
                            rs.getLong("user_id"),
                            rs.getString("nickname"),
                            rs.getString("password"),
                            rs.getBoolean("isAdmin"),
                            rs.getBoolean("isBlocked"),
                            rs.getBoolean("hasChanged")
                    );
                    listAccessBackoffice.add(el);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return listAccessBackoffice;
            }

        } else {
            return listAccessBackoffice;
        }

        return listAccessBackoffice;
    }

    
    @Override
    public AccessBackoffice find(Long primary_key) {
        // create array AccessBackoffice empty
        AccessBackoffice access_backoffice = new AccessBackoffice();
        
        //check if connected to database
        if (this.bddmanager.connect()) {

            try {
                
                // create statement for find 
                Statement st = this.bddmanager.getConnectionManager()
                        .createStatement();
                
                // create request's add primary key
                String requete = "SELECT * FROM access_backoffice WHERE user_id = " + primary_key;
                
                // excute request
                ResultSet rs = st.executeQuery(requete);
                
                // if result is full
                if (rs.next()) {
                    
                    // insert AccessBackoffice's informations in object                   
                    access_backoffice.setUser_id(rs.getLong("user_id"));
                    access_backoffice.setNickname(rs.getString("nickname"));
                    access_backoffice.setPassword(rs.getString("password"));
                    access_backoffice.setIsAdmin(rs.getBoolean("isAdmin"));
                    access_backoffice.setIsBlocked(rs.getBoolean("isBlocked"));
                    access_backoffice.setHasChanged(rs.getBoolean("hasChanged"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return access_backoffice;
            }

        } else {
            return access_backoffice;
        }

        return access_backoffice;
    }
   

    public AccessBackoffice find(String login) {
        // create array users empty
        AccessBackoffice access_backoffice = new AccessBackoffice();
        
        //check if connected to database
        if (this.bddmanager.connect()) {

            try {
                // create statement for find 
                Statement st = this.bddmanager.getConnectionManager()
                        .createStatement();
                
                // create request's add primary key
                String requete = "SELECT * FROM access_backoffice WHERE nickname LIKE '" + login+"'";
                
                // excute requete
                ResultSet rs = st.executeQuery(requete);
                
                // if result is full
                if (rs.next()) {
                   
                    // insert AccessBackoffice's informations in object                   
                    access_backoffice.setUser_id(rs.getLong("user_id"));
                    access_backoffice.setNickname(rs.getString("nickname"));
                    access_backoffice.setPassword(rs.getString("password"));
                    access_backoffice.setIsAdmin(rs.getBoolean("isAdmin"));
                    access_backoffice.setIsBlocked(rs.getBoolean("isBlocked"));
                    access_backoffice.setHasChanged(rs.getBoolean("hasChanged"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return access_backoffice;
            }

        } else {
            return access_backoffice;
        }

        return access_backoffice;
    }
    
    public boolean isValid(AccessBackoffice access_backoffice) {
        boolean isValid = true;

        // if id is empty
        if (access_backoffice.getUser_id() == 0) {
            isValid = false;
        }

        return isValid;
    }

}
