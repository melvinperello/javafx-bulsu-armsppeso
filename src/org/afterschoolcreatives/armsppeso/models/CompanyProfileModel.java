package org.afterschoolcreatives.armsppeso.models;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.PolarisException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyProfileModel {

    private Integer id;
    private String name;
    private String preferredCourse;
    private String address;
    private String email;
    private String contactPerson;
    private String contactNumber;
    //
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    //
    private String contactPosition;
    private String description;

    public CompanyProfileModel() {
        this.createdDate = null;
        this.lastModifiedDate = null;
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPreferredCourse(String preferredCoursed) {
        this.preferredCourse = preferredCoursed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate == null) {
            return;
        }
        this.createdDate = new Date(createdDate.getTime());
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        if (lastModifiedDate == null) {
            return;
        }
        this.lastModifiedDate = new Date(lastModifiedDate.getTime());
    }

    //--------------------------------------------------------------------------
    // Getters
    //--------------------------------------------------------------------------
    public String getContactPosition() {
        return contactPosition;
    }

    public String getDescription() {
        return description;
    }

    public String getPreferredCourse() {
        return preferredCourse;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedDate() {
        return new Date(createdDate.getTime());
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return new Date(lastModifiedDate.getTime());
    }

    //--------------------------------------------------------------------------
    // Static Methods.
    //--------------------------------------------------------------------------
    /**
     * Retrieves all company profiles.
     *
     * @return
     */
    public static ArrayList<CompanyProfileModel> getAllRecords() {
        ArrayList<CompanyProfileModel> graduateRecords = new ArrayList<>();
        String query = "SELECT * FROM `company_profile` ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query);
            ds.forEach(row -> {
                CompanyProfileModel csm = new CompanyProfileModel();
                csm.setId(row.getValue("id"));
                csm.setName(row.getValue("name"));
                csm.setPreferredCourse(row.getValue("preferred_course"));
                csm.setAddress(row.getValue("address"));
                csm.setEmail(row.getValue("email"));
                csm.setContactPerson(row.getValue("contact_person"));
                csm.setContactNumber(row.getValue("contact_number"));
                //
                csm.setCreatedBy(row.getValue("created_by"));
                csm.setCreatedDate(CompanyProfileModel.convertStorageStringToDate(row.getValue("created_date")));
                csm.setLastModifiedBy(row.getValue("last_modified_by"));
                csm.setLastModifiedDate(CompanyProfileModel.convertStorageStringToDate(row.getValue("last_modified_date")));
                //
                csm.setContactPosition(row.getValue("contact_position"));
                csm.setDescription(row.getValue("description"));
                // add entry
                graduateRecords.add(csm);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisException("Cannot execute fetch all records", sqlEx);
        }
        return graduateRecords;
    }

    /**
     * Deletes an entry from Company Profile Table using the primary key.
     *
     * @param id
     * @return
     */
    public static boolean delete(Integer id) {
        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `company_profile` WHERE `_rowid_` IN (?);";
        deleteQuery.addStatementWithParameter(query, id);
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(deleteQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }

    /*        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `company_profile` WHERE `_rowid_` IN (?);";
        deleteQuery.addStatementWithParameter(query, id);
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(deleteQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }*
     * Get the total number of records.
     *
     * @return
     */
    public static String getTotalRecords() {
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            final String countProfile = "SELECT COUNT(*) as count FROM (SELECT `_rowid_`,* FROM `company_profile` ORDER BY `_rowid_` ASC);";
            DataSet profile_ds = con.fetch(countProfile);
            // store values
            String profile_count = profile_ds.get(0).getValue("count").toString();
            return profile_count;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "0";
        }
    }

    //--------------------------------------------------------------------------
    // Class Methods.
    //--------------------------------------------------------------------------
    /**
     * Since SQLITE does not support date and time. this method converts the
     * current date in a string recognizable format which later on can be
     * converted again to a Date object.
     *
     * @param date
     * @return
     */
    private String convertDateToStorageString(Date date) {
        if (date == null) {
            return null;
        }
        return Context.app().getStandardDateFormat().format(date);
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

    /**
     * Inserts a new Company Profile Record.
     *
     * @return true or false
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `company_profile`(`name`,`preferred_course`,`address`,`email`,`contact_person`,`contact_number`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,contact_position,description) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.name,
                this.preferredCourse,
                this.address,
                this.email,
                this.contactPerson,
                this.contactNumber,
                this.createdBy,
                this.convertDateToStorageString(this.createdDate),
                this.lastModifiedBy,
                this.convertDateToStorageString(this.lastModifiedDate),
                // new
                this.contactPosition,
                this.description
        );
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(insertQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }

    /**
     * Update entries of the the company profile table.
     *
     * @return
     */
    public boolean update() {
        SimpleQuery updateQuery = new SimpleQuery();
        updateQuery.addStatement("UPDATE") // operation
                .addStatement("company_profile") // table
                // fields here
                .addStatementWithParameter("SET name = ?,", this.name)
                .addStatementWithParameter("preferred_course = ?,", this.preferredCourse)
                .addStatementWithParameter("address = ?,", this.address)
                .addStatementWithParameter("email = ?,", this.email)
                .addStatementWithParameter("contact_person = ?,", this.contactPerson)
                .addStatementWithParameter("contact_number = ?,", this.contactNumber)
                .addStatementWithParameter("created_by = ?,", this.createdBy)
                .addStatementWithParameter("created_date = ?,", this.convertDateToStorageString(this.createdDate))
                .addStatementWithParameter("last_modified_by = ?,", this.lastModifiedBy)
                .addStatementWithParameter("last_modified_date = ?,", this.convertDateToStorageString(this.lastModifiedDate))
                // new
                .addStatementWithParameter("contact_position = ?,", this.contactPosition)
                .addStatementWithParameter("description = ?", this.description)
                // where clause
                .addStatementWithParameter("WHERE _rowid_= ?;", this.id);

        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(updateQuery);
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        }
    }

}
