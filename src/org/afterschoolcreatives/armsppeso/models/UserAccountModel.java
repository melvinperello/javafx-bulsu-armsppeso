/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.models;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.PolarisException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataRow;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
    private String createdBy;
    private String createdDate;

    public UserAccountModel() {
        createdDate = null;
    }
    
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate == null) {
            return;
        }
        this.createdDate = Context.app().getStandardDateFormat().format(createdDate);
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
    
    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        if (this.createdDate == null) {
            return null;
        }
        return this.createdDate;
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
            DataRow dr = con.fetchFirst(query, username.toUpperCase());
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
        String query = "INSERT INTO `accounts`(`username`,`password`,`full_name`,`account_type`, 'created_by', 'created_date') VALUES (?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.username,
                this.password,
                this.full_name,
                this.account_type,
                this.createdBy,
                this.createdDate
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
        updateQuery.addStatement("UPDATE ") // operation
                .addStatement("accounts ") // table
                // fields here
                .addStatementWithParameter("SET username = ?,", this.username)
                .addStatementWithParameter("password = ?,", this.password)
                .addStatementWithParameter("full_name = ?,", this.full_name)
                .addStatementWithParameter("account_type = ? ", this.account_type)
                .addStatementWithParameter("WHERE _rowid_= ?;", this.id);

        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(updateQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
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
    
    public static int isExisting(String username, Integer id) {
        ArrayList<UserAccountModel> accountRecords = new ArrayList<>();
        String query = "SELECT * FROM `accounts` where username = ? ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query, username);
            if(ds.isEmpty()) {
                return 0;
            }
            ds.forEach(row -> {
                UserAccountModel ua = new UserAccountModel();
                ua.setId(row.getValue("id"));
                ua.setFull_name(row.getValue("full_name"));
                ua.setAccount_type(row.getValue("account_type"));
                ua.setPassword(row.getValue("password"));
                ua.setUsername(row.getValue("username"));
                accountRecords.add(ua);
            });
            if(id != null) {
                System.out.println("ID: " + id);
                for(UserAccountModel each : accountRecords) {
                    if(!each.getId().equals(id)) {
                        return 2;
                    }
                }
            }
        } catch (SQLException sqlEx) {
            throw new PolarisException("Cannot execute fetch all records", sqlEx);
        }
        return (accountRecords.size());
    }
}
