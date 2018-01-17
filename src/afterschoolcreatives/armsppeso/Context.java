package afterschoolcreatives.armsppeso;

import afterschoolcreatives.polaris.java.sql.ConnectionFactory;
import java.text.SimpleDateFormat;
import javafx.scene.image.Image;

/**
 * A Class that manages the resources of the project.
 *
 * @author Jhon Melvin
 */
public final class Context {

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
        connectionFactory.setSQLiteURL("armsppeso.db3");
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

    /**
     * Display Date Format November 22, 1997 09:08:16 PM.
     *
     * @return
     */
    public SimpleDateFormat getDisplayDateFormat() {
        return new SimpleDateFormat("MMMMMMMMMMM dd,yyyy hh:mm:ss a");
    }

    /**
     * Converts JavaFX Image from the drawable folder.
     *
     * @param name
     * @return
     */
    public Image getDrawableImage(String name) {
        return new Image(this.getClass().getResourceAsStream("/res/drawable/" + name));
    }

}
