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
public class GraduatedStudentModel {

    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String course;
    private Date graduationDate;
    private Integer age;
    private String email;
    private String contact;
    private String address;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    public GraduatedStudentModel() {
        this.graduationDate = null;
        this.createdDate = null;
        this.lastModifiedDate = null;
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGraduationDate(Date graduationDate) {
        if (graduationDate == null) {
            return;
        }
        this.graduationDate = new Date(graduationDate.getTime());
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getCourse() {
        return course;
    }

    public Date getGraduationDate() {
        if (this.graduationDate == null) {
            return null;
        }
        return new Date(this.graduationDate.getTime());
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedDate() {
        if (this.createdDate == null) {
            return null;
        }
        return new Date(this.createdDate.getTime());
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        if (this.lastModifiedDate == null) {
            return null;
        }
        return new Date(this.lastModifiedDate.getTime());
    }

    //--------------------------------------------------------------------------
    // Static Methods.
    //--------------------------------------------------------------------------
    /**
     * Deletes an entry from GraduatedStudentModel Table using the primary key.
     *
     * @param id
     * @return
     */
    public static boolean delete(Integer id) {
        SimpleQuery deleteQuery = new SimpleQuery();
        String query = "DELETE FROM `graduated_students` WHERE `_rowid_` IN (?);";
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
            final String countStudent = "SELECT COUNT(*) as count FROM (SELECT `_rowid_`,* FROM `graduated_students` ORDER BY `_rowid_` ASC);";
            DataSet student_ds = con.fetch(countStudent);
            // store values
            String student_count = student_ds.get(0).getValue("count").toString();
            return student_count;
        } catch (SQLException sqlEx) {
            throw new RuntimeException("Cannot get count.", sqlEx);
        }
    }

    /**
     * Retrieves all the records of the graduated students.
     *
     * @return
     */
    public static ArrayList<GraduatedStudentModel> getAllRecords() {
        ArrayList<GraduatedStudentModel> graduateRecords = new ArrayList<>();
        String query = "SELECT * FROM `graduated_students` ORDER BY `_rowid_` DESC;";
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            DataSet ds = con.fetch(query);
            ds.forEach(row -> {
                GraduatedStudentModel gs = new GraduatedStudentModel();
                gs.setId(row.getValue("id"));
                gs.setLastName(row.getValue("last_name"));
                gs.setFirstName(row.getValue("first_name"));
                gs.setMiddleName(row.getValue("middle_name"));
                gs.setCourse(row.getValue("course"));
                gs.setGraduationDate(GraduatedStudentModel.convertStorageStringToDate(row.getValue("graduation_date")));
                gs.setAge(row.getValue("age"));
                gs.setEmail(row.getValue("email"));
                gs.setContact(row.getValue("contact"));
                gs.setAddress(row.getValue("address"));
                gs.setCreatedBy(row.getValue("created_by"));
                gs.setCreatedDate(GraduatedStudentModel.convertStorageStringToDate(row.getValue("created_date")));
                gs.setLastModifiedBy(row.getValue("last_modified_by"));
                gs.setLastModifiedDate(GraduatedStudentModel.convertStorageStringToDate(row.getValue("last_modified_date")));
                // add entry
                graduateRecords.add(gs);
            });
        } catch (SQLException sqlEx) {
            throw new PolarisException("Cannot execute fetch all records", sqlEx);
        }
        return graduateRecords;
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
    private static String convertDateToStorageString(Date date) {
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
     * Inserts a new Graduated Student Record.
     *
     * @return true or false
     */
    public boolean insert() {
        SimpleQuery insertQuery = new SimpleQuery();
        String query = "INSERT INTO `graduated_students`(`last_name`,`first_name`,`middle_name`,`course`,`graduation_date`,`age`,`email`,`contact`,`address`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        insertQuery.addStatementWithParameter(query,
                this.lastName,
                this.firstName,
                this.middleName,
                this.course,
                GraduatedStudentModel.convertDateToStorageString(this.graduationDate),
                this.age,
                this.email,
                this.contact,
                this.address,
                this.createdBy,
                GraduatedStudentModel.convertDateToStorageString(this.createdDate),
                this.lastModifiedBy,
                GraduatedStudentModel.convertDateToStorageString(this.lastModifiedDate)
        );
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            con.update(insertQuery);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }

    /**
     * Update entries of the the graduated student table.
     *
     * @return
     */
    public boolean update() {
        SimpleQuery updateQuery = new SimpleQuery();
        updateQuery.addStatement("UPDATE") // operation
                .addStatement("graduated_students") // table
                // fields here
                .addStatementWithParameter("SET last_name = ?,", this.lastName)
                .addStatementWithParameter("first_name = ?,", this.firstName)
                .addStatementWithParameter("middle_name = ?,", this.middleName)
                .addStatementWithParameter("course = ?,", this.course)
                .addStatementWithParameter("graduation_date = ?,", GraduatedStudentModel.convertDateToStorageString(this.graduationDate))
                .addStatementWithParameter("age = ?,", this.age)
                .addStatementWithParameter("email = ?,", this.email)
                .addStatementWithParameter("contact = ?,", this.contact)
                .addStatementWithParameter("address = ?,", this.address)
                .addStatementWithParameter("created_by = ?,", this.createdBy)
                .addStatementWithParameter("created_date = ?,", GraduatedStudentModel.convertDateToStorageString(this.createdDate))
                .addStatementWithParameter("last_modified_by = ?,", this.lastModifiedBy)
                .addStatementWithParameter("last_modified_date = ?", GraduatedStudentModel.convertDateToStorageString(this.lastModifiedDate))
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
