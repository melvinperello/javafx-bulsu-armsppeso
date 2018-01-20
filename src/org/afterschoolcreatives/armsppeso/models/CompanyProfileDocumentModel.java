/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.models;

import java.sql.SQLException;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyProfileDocumentModel {

    private Integer id;
    private Integer companyProfileId;
    private String type;
    private String description;
    private String location;
    private String createdBy;
    private String createdDate;

    /**
     * Blank.
     */
    public CompanyProfileDocumentModel() {
        //
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCompanyProfileId(Integer companyProfileId) {
        this.companyProfileId = companyProfileId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    //--------------------------------------------------------------------------
    // Getters.
    //--------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public Integer getCompanyProfileId() {
        return companyProfileId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    //--------------------------------------------------------------------------
    // Class Methods.
    //--------------------------------------------------------------------------
    /**
     * Inserts a new record.
     *
     * @return
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `company_profile_documents`(`company_profile_id`,`type`,`description`,`location`,`created_by`,`created_date`) VALUES (?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.companyProfileId,
                this.type,
                this.description,
                this.location,
                this.createdBy,
                this.createdDate
        );
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(insertQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }
}
