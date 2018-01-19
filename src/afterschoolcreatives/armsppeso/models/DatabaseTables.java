package afterschoolcreatives.armsppeso.models;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.polaris.java.sql.ConnectionManager;
import java.sql.SQLException;

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
            + "	`preferred_course`	TEXT,\n"
            + "	`address`	TEXT,\n"
            + "	`email`	TEXT,\n"
            + "	`contact_person`	TEXT,\n"
            + "	`contact_number`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	TEXT,\n"
            + "	`last_modified_by`	TEXT,\n"
            + "	`last_modified_date`	TEXT\n"
            + ");";

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
     * Create the tables if not existing.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static void create() throws SQLException {
        // build connection
        try (ConnectionManager con = Context.app().getConnectionFactory()
                .createConnectionManager()) {
            con.update(DatabaseTables.TABLE_GRADUTES);
            con.update(DatabaseTables.TABLE_COMPANY_PROFILE);
            con.update(DatabaseTables.TABLE_ACCOUNTS);
        }
    }

}
