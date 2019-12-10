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
//import org.afterschoolcreatives.polaris.java.PolarisException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
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
     * Delete an entry.
     *
     * @param id
     * @return
     */
    public static boolean delete(Integer id) {
        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `company_profile_documents` WHERE `_rowid_` IN (?);";
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
     * Get all records from a company.
     *
     * @param companyId
     * @return
     */
    public static ArrayList<CompanyProfileDocumentModel> getAllRecords(Integer companyId) {
        ArrayList<CompanyProfileDocumentModel> companyDocuments = new ArrayList<>();
        String query = "SELECT * FROM `company_profile_documents` WHERE company_profile_id = ? ORDER BY id DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query, companyId);
            ds.forEach(row -> {
                CompanyProfileDocumentModel cpd = new CompanyProfileDocumentModel();
                cpd.setId(row.getValue("id"));
                cpd.setCompanyProfileId(row.getValue("company_profile_id"));
                cpd.setType(row.getValue("type"));
                cpd.setDescription(row.getValue("description"));
                cpd.setLocation(row.getValue("location"));
                cpd.setCreatedBy(row.getValue("created_by"));
                cpd.setCreatedDate(row.getValue("created_date"));
                // add entry
                companyDocuments.add(cpd);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisRuntimeException("Cannot execute fetch all records", sqlEx);
        }
        return companyDocuments;
    }

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
