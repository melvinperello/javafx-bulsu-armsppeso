package org.afterschoolcreatives.armsppeso;

import java.io.File;
import java.io.IOException;
import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;
import java.text.SimpleDateFormat;
import javafx.scene.image.Image;
import org.afterschoolcreatives.polaris.java.io.FileTool;

/**
 * A Class that manages the resources of the project.
 *
 * @author Jhon Melvin
 */
public final class Context {

    public final static String DATA_DRIVE = "disk_drive";

    /**
     * Application Context Instance.
     */
    private final static Context instance = new Context();

    /**
     * Application Global Connection Factory.
     */
    private ConnectionFactory connectionFactory;

    /**
     * Context Constructor.
     */
    public Context() {
        this.createConnectionFactory();
    }

    /**
     * Create the connection factory instance.
     */
    private void createConnectionFactory() {
        /**
         * Please download the JDBC Driver for SQLITE
         *
         * https://bitbucket.org/xerial/sqlite-jdbc/downloads/
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // select the database that you are using.
        connectionFactory.setConnectionDriver(ConnectionFactory.Driver.SQLite);
        connectionFactory.setSQLiteURL(Context.DATA_DRIVE + File.separator + "armsppeso.db3");
        this.connectionFactory = connectionFactory;
    }

    /**
     * Singleton.
     *
     * @return
     */
    public static Context app() {
        return Context.instance;
    }

    /**
     * Get the global connection from the context.
     *
     * @return
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Date Format for 24 HR 00:00 to 23:59
     *
     * @return
     */
    public SimpleDateFormat getStandardDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

//    /**
//     * Display Date Format November 22, 1997 09:08:16 PM.
//     *
//     * @return
//     */
//    public SimpleDateFormat getDisplayDateFormat() {
//        return new SimpleDateFormat("MMMMMMMMMMM dd,yyyy hh:mm:ss a");
//    }
    /**
     * Converts JavaFX Image from the drawable folder.
     *
     * @param name
     * @return
     */
    public Image getDrawableImage(String name) {
        return new Image(this.getClass().getResourceAsStream("/res/drawable/" + name));
    }

    //--------------------------------------------------------------------------
    // User Information
    //--------------------------------------------------------------------------
    private String accountUsername;
    private String accountName;
    private String accountType;
    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    //--------------------------------------------------------------------------
    // Documents
    //--------------------------------------------------------------------------
    /**
     * Upload a document.
     *
     * @param document
     * @param savedFilename
     * @return
     */
    public boolean uploadDocument(File document, String savedFilename) {
        final String driveDirectory = Context.DATA_DRIVE + File.separator + "documents";
        // check upload directory.
        if (!FileTool.createDirectory(driveDirectory)) {
            return false;
        }
        try {
            //
            return FileTool.copyChannel(document, new File(driveDirectory + File.separator + savedFilename));
        } catch (IOException ex) {
            return false;
        }
    }
}
