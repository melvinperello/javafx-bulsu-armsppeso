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
import java.util.ArrayList;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.exceptions.PolarisRuntimeException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;

/**
 * This class represents the view of the Inquiry Table. unlike the other models
 * this table are all strings except the id. THe model does not have Date type
 * for dates.
 *
 * @author Jhon Melvin
 */
public class InquiryModel {

    private Integer id;
    private String name;
    private String representative;
    private String address;
    private String contact;
    private String description;
    private String concern;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    /**
     * Blank Constructor.
     */
    public InquiryModel() {
        //
    }

    //--------------------------------------------------------------------------
    // Getters
    //--------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRepresentative() {
        return representative;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getConcern() {
        return concern;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    //--------------------------------------------------------------------------
    // Static Methods.
    //--------------------------------------------------------------------------
    /**
     * Deletes an entry from the Table using the primary key.
     *
     * @param id
     * @return
     */
    public static boolean delete(Integer id) {
        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `inquiry` WHERE `_rowid_` IN (?);";
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
     * Get the total number of records.
     *
     * @return
     */
    public static String getTotalRecords() {
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            final String countStudent = "SELECT COUNT(*) as count FROM (SELECT `_rowid_`,* FROM `inquiry` ORDER BY `_rowid_` ASC);";
            DataSet student_ds = con.fetch(countStudent);
            // store values
            String student_count = student_ds.get(0).getValue("count").toString();
            return student_count;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "0";
        }
    }

    /**
     * Retrieves all the records.
     *
     * @return
     */
    public static ArrayList<InquiryModel> getAllRecords() {
        ArrayList<InquiryModel> inquiryRecords = new ArrayList<>();
        String query = "SELECT * FROM `inquiry` ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query);
            ds.forEach(row -> {
                InquiryModel im = new InquiryModel();
                im.setId(row.getValue("id"));
                im.setName(row.getValue("name"));
                im.setRepresentative(row.getValue("representative"));
                im.setAddress(row.getValue("address"));
                im.setContact(row.getValue("contact"));
                im.setDescription(row.getValue("description"));
                im.setConcern(row.getValue("concern"));
                //
                im.setCreatedBy(row.getValue("created_by"));
                im.setCreatedDate(row.getValue("created_date"));
                im.setLastModifiedBy(row.getValue("last_modified_by"));
                im.setLastModifiedDate(row.getValue("last_modified_date"));
                inquiryRecords.add(im);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
        }
        return inquiryRecords;
    }

    //--------------------------------------------------------------------------
    // Class Methods.
    //--------------------------------------------------------------------------
    /**
     * Inserts a new Record.
     *
     * @return true or false
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `inquiry`(`name`,`representative`,`address`,`contact`,`description`,`concern`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`) VALUES (?,?,?,?,?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.name,
                this.representative,
                this.address,
                this.contact,
                this.description,
                this.concern,
                this.createdBy,
                this.createdDate,
                this.lastModifiedBy,
                this.lastModifiedDate
        );
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(insertQuery);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }

    /**
     * Update entries of the the table.
     *
     * @return
     */
    public boolean update() {
        SimpleQuery updateQuery = new SimpleQuery();
        updateQuery.addStatement("UPDATE") // operation
                .addStatement("inquiry") // table
                // fields here
                .addStatement("SET")
                .addStatementWithParameter("name = ?,", this.name)
                .addStatementWithParameter("representative = ?,", this.representative)
                .addStatementWithParameter("address = ?,", this.address)
                .addStatementWithParameter("contact = ?,", this.contact)
                .addStatementWithParameter("description = ?,", this.description)
                .addStatementWithParameter("concern = ?,", this.concern)
                //
                .addStatementWithParameter("created_by = ?,", this.createdBy)
                .addStatementWithParameter("created_date = ?,", this.createdDate)
                .addStatementWithParameter("last_modified_by = ?,", this.lastModifiedBy)
                .addStatementWithParameter("last_modified_date = ?", this.lastModifiedDate)
                // where clause
                .addStatementWithParameter("WHERE _rowid_= ?;", this.id);

        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(updateQuery);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }

}
