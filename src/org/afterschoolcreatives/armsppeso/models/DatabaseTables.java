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

import java.io.File;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import java.sql.SQLException;
import org.afterschoolcreatives.polaris.java.io.FileTool;

/**
 *
 * @author Jhon Melvin
 */
public class DatabaseTables {

    /**
     * Query for the creation of the Graduates Table.
     */
    private final static String TABLE_GRADUTES
            = "CREATE TABLE IF NOT EXISTS `graduated_students` (\n"
            + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "	`last_name`	TEXT,\n"
            + "	`first_name`	TEXT,\n"
            + "	`middle_name`	TEXT,\n"
            + "	`course`	TEXT,\n"
            + "	`graduation_date`	TEXT,\n"
            + "	`age`	INTEGER,\n"
            + "	`email`	TEXT,\n"
            + "	`contact`	TEXT,\n"
            + "	`address`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT,\n"
            + "	`last_modified_by`	TEXT,\n"
            + "	`last_modified_date`	TEXT\n"
            + ");";

    /**
     * Query for the creation of the Company Profile Table.
     */
    private final static String TABLE_COMPANY_PROFILE
            = "CREATE TABLE IF NOT EXISTS `company_profile` (\n"
            + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "	`name`	TEXT,\n"
            + "	`description`	TEXT,\n"
            + "	`contact_person`	TEXT,\n"
            + "	`contact_position`	TEXT,\n"
            + "	`contact_number`	TEXT,\n"
            + "	`preferred_course`	TEXT,\n"
            + "	`address`	TEXT,\n"
            + "	`email`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT,\n"
            + "	`last_modified_by`	TEXT,\n"
            + "	`last_modified_date`	TEXT\n"
            + ");";

    /**
     * Query for creating the account table.
     */
    private final static String TABLE_ACCOUNTS
            = "CREATE TABLE IF NOT EXISTS `accounts` (\n"
            + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "	`full_name`	TEXT,\n"
            + "	`username`	TEXT,\n"
            + "	`password`	TEXT,\n"
            + "	`account_type`	TEXT DEFAULT 'USER',\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT\n"
            + ");";

    /**
     * Create Inquiry Table.
     */
    private final static String TABLE_INQUIRY
            = "CREATE TABLE IF NOT EXISTS `inquiry` (\n"
            + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "	`name`	TEXT,\n"
            + "	`representative`	TEXT,\n"
            + "	`address`	TEXT,\n"
            + "	`contact`	TEXT,\n"
            + "	`description`	TEXT,\n"
            + "	`concern`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT,\n"
            + "	`last_modified_by`	TEXT,\n"
            + "	`last_modified_date`	TEXT\n"
            + ");";

    /**
     * Documents.
     */
    private final static String TABLE_COMPANY_PROFILE_DOCUMENTS
            = "CREATE TABLE IF NOT EXISTS `company_profile_documents` (\n"
            + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "	`company_profile_id`	INTEGER,\n"
            + "	`type`	TEXT,\n"
            + "	`description`	TEXT,\n"
            + "	`location`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT\n"
            + ");";

    /**
     * Create the tables if not existing.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static void create() throws SQLException {
        if (FileTool.checkFoldersQuietly(Context.DATA_DRIVE)) {
            // build connection
            try (ConnectionManager con = Context.app().getConnectionFactory()
                    .createConnectionManager()) {
                con.update(DatabaseTables.TABLE_GRADUTES);
                con.update(DatabaseTables.TABLE_COMPANY_PROFILE);
                con.update(DatabaseTables.TABLE_ACCOUNTS);
                con.update(DatabaseTables.TABLE_INQUIRY);
                con.update(DatabaseTables.TABLE_COMPANY_PROFILE_DOCUMENTS);
            }
        } else {
            throw new RuntimeException("Cannot Create Drive Directory");
        }
    }
    
}
