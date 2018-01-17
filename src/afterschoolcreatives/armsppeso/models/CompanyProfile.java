package afterschoolcreatives.armsppeso.models;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.polaris.java.sql.ConnectionManager;
import afterschoolcreatives.polaris.java.sql.DataSet;
import afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyProfile {

    private Integer id;
    private String name;
    private String address;
    private String email;
    private String contactPerson;
    private String contactNumber;
    //
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    public CompanyProfile() {
        this.createdDate = null;
        this.lastModifiedDate = null;
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
        this.createdDate = new Date(createdDate.getTime());
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = new Date(lastModifiedDate.getTime());
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

    /**
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
            throw new RuntimeException("Cannot get count.", sqlEx);
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

    /**
     * Inserts a new Company Profile Record.
     *
     * @return true or false
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `company_profile`(`name`,`address`,`email`,`contact_person`,`contact_number`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`) VALUES (?,?,?,?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.name,
                this.address,
                this.email,
                this.contactPerson,
                this.contactNumber,
                this.createdBy,
                this.convertDateToStorageString(this.createdDate),
                this.lastModifiedBy,
                this.convertDateToStorageString(this.lastModifiedDate)
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
                .addStatement("graduated_students") // table
                // fields here
                .addStatementWithParameter("SET name = ?,", this.name)
                .addStatementWithParameter("SET address = ?,", this.address)
                .addStatementWithParameter("SET email = ?,", this.email)
                .addStatementWithParameter("SET contact_person = ?,", this.contactPerson)
                .addStatementWithParameter("SET contact_number = ?,", this.contactNumber)
                .addStatementWithParameter("created_by = ?,", this.createdBy)
                .addStatementWithParameter("created_date = ?,", this.convertDateToStorageString(this.createdDate))
                .addStatementWithParameter("last_modified_by = ?,", this.lastModifiedBy)
                .addStatementWithParameter("last_modified_date = ?", this.convertDateToStorageString(this.lastModifiedDate))
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
