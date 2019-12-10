/**
 *
 * Automated Record Management System
 * Public Placement and Employment Service Office
 * Bulacan State University, City of Malolos
 *
 * Copyright 2018 Jhon Melvin Perello, Joemar De La Cruz, Ericka Joy Dela Cruz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.afterschoolcreatives.armsppeso.models;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.exceptions.PolarisRuntimeException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataRow;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;

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
     *
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
            if (dr.isEmpty()) {
                return null;
            }
            gs = new UserAccountModel();
            gs.setId(dr.getValue("id"));
            gs.setAccount_type(dr.getValue("account_type"));
            gs.setFull_name(dr.getValue("full_name"));
            gs.setPassword(dr.getValue("password"));
            gs.setUsername(dr.getValue("username"));
            gs.setCreatedBy(dr.getValue("created_by"));
            gs.setCreatedDate(convertStorageStringToDate(dr.getValue("created_date")));
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
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
                ua.setCreatedBy(row.getValue("created_by"));
                ua.setCreatedDate(convertStorageStringToDate(row.getValue("created_date")));
                accountRecords.add(ua);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
        }
        return accountRecords;
    }

    public static int isExisting(String username, Integer id) {
        ArrayList<UserAccountModel> accountRecords = new ArrayList<>();
        String query = "SELECT * FROM `accounts` where username = ? ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query, username);
            if (ds.isEmpty()) {
                return 0;
            }
            ds.forEach(row -> {
                UserAccountModel ua = new UserAccountModel();
                ua.setId(row.getValue("id"));
                accountRecords.add(ua);
            });
            if (id != null) {
                System.out.println("ID: " + id);
                for (UserAccountModel each : accountRecords) {
                    if (!each.getId().equals(id)) {
                        return 2;
                    }
                }
            }
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
        }
        return (accountRecords.size());
    }

    public static UserAccountModel find(Integer id) {
        UserAccountModel gs;
        String query = "SELECT * FROM `accounts` WHERE id = ? LIMIT 1;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataRow dr = con.fetchFirst(query, id);
            if (dr.isEmpty()) {
                return null;
            }
            gs = new UserAccountModel();
            gs.setId(dr.getValue("id"));
            gs.setAccount_type(dr.getValue("account_type"));
            gs.setFull_name(dr.getValue("full_name"));
            gs.setPassword(dr.getValue("password"));
            gs.setUsername(dr.getValue("username"));
            gs.setCreatedBy(dr.getValue("created_by"));
            gs.setCreatedDate(convertStorageStringToDate(dr.getValue("created_date")));
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
        }
        return gs;
    }

    private static Date convertStorageStringToDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            return Context.app().getStandardDateFormat().parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
