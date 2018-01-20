/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.models;

import java.sql.SQLException;
import org.afterschoolcreatives.armsppeso.Context;
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
    
    

}
