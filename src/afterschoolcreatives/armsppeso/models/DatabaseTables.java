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
            + "	`address`	TEXT,\n"
            + "	`email`	TEXT,\n"
            + "	`contact_person`	TEXT,\n"
            + "	`contact_number`	TEXT,\n"
            + "	`created_by`	TEXT,\n"
            + "	`created_date`	BLOB,\n"
            + "	`last_modified_by`	TEXT,\n"
            + "	`last_modified_date`	TEXT\n"
            + ");";

    /**
     * Create the tables if not existing.
     * @return 
     */
    public static boolean create() {
        // build connection
        try (ConnectionManager con = Context.app().getConnectionFactory()
                .createConnectionManager()) {
            con.update(DatabaseTables.TABLE_GRADUTES);
            con.update(DatabaseTables.TABLE_COMPANY_PROFILE);
            return true;
        } catch (SQLException sqlEx) {
            return false;
        }
    }

}
