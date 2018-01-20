/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.models;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.polaris.java.PolarisException;
import afterschoolcreatives.polaris.java.sql.ConnectionManager;
import afterschoolcreatives.polaris.java.sql.DataRow;
import afterschoolcreatives.polaris.java.sql.DataSet;
import afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Joemar
 */
public class UserAccountModel {
    private Integer id;
    private String full_name;
    private String username;
    private String password;
    private String account_type;

    /**
     * SETTERS
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    /**
     * GETTERS 
     */
    public Integer getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccount_type() {
        return account_type;
    }
    
    /**
     * Deletes an entry from Accounts Table using the primary key.
     * @param id
     * @return 
     */
    public static boolean delete(Integer id) {
        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `accounts` WHERE `_rowid_` IN (?);";
        deleteQuery.addStatementWithParameter(query, id);
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(deleteQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves all the records of the accounts.
     *
     * @return
     */
    public static UserAccountModel findUsername(String username) {
        UserAccountModel gs;
        String query = "SELECT * FROM `accounts` WHERE username = ? LIMIT 1;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataRow dr = con.fetchFirst(query, username);
            if(dr.isEmpty()) {
                return null;
            }
            gs = new UserAccountModel();
            gs.setId(dr.getValue("id"));
            gs.setAccount_type(dr.getValue("account_type"));
            gs.setFull_name(dr.getValue("full_name"));
            gs.setPassword(dr.getValue("password"));
            gs.setUsername(dr.getValue("username"));
        } catch (SQLException sqlEx) {
            throw new PolarisException("Cannot execute fetch all records", sqlEx);
        }
        return gs;
    }
    
    /**
     * Inserts a new Account Record.
     *
     * @return true or false
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `accounts`(`username`,`password`,`full_name`,`account_type`) VALUES (?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.username,
                this.password,
                this.full_name,
                this.account_type
        );
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(insertQuery);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }
    
    /**
     * Update entries of the the accounts table.
     *
     * @return
     */
    public boolean update() {
        SimpleQuery updateQuery = new SimpleQuery();
        updateQuery.addStatement("UPDATE") // operation
                .addStatement("accounts") // table
                // fields here
                .addStatementWithParameter("SET username = ?,", this.username)
                .addStatementWithParameter("password = ?,", this.password)
                .addStatementWithParameter("full_name = ?,", this.full_name)
                .addStatementWithParameter("account_type = ?,", this.account_type)
                .addStatementWithParameter("WHERE _rowid_= ?;", this.id);

        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(updateQuery);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }
    
    public static ArrayList<UserAccountModel> getAllRecords() {
        ArrayList<UserAccountModel> accountRecords = new ArrayList<>();
        String query = "SELECT * FROM `accounts` ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query);
            ds.forEach(row -> {
                UserAccountModel ua = new UserAccountModel();
                ua.setId(row.getValue("id"));
                ua.setFull_name(row.getValue("full_name"));
                ua.setAccount_type(row.getValue("account_type"));
                ua.setPassword(row.getValue("password"));
                ua.setUsername(row.getValue("username"));
                accountRecords.add(ua);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisException("Cannot execute fetch all records", sqlEx);
        }
        return accountRecords;
    }
}
