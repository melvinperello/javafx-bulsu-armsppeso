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

    /**
     * Main Drive of the system.
     */
    public final static String DATA_DRIVE = "disk_drive";
    /**
     * Documents Folder of this computer.
     */
    public final static String DOC_DRIVE = Context.DATA_DRIVE + File.separator + "documents";

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

        // check upload directory.
        if (!FileTool.createDirectory(Context.DOC_DRIVE)) {
            return false;
        }
        try {
            //
            return FileTool.copyChannel(document, new File(Context.DOC_DRIVE + File.separator + savedFilename));
        } catch (IOException ex) {
            return false;
        }
    }
}
