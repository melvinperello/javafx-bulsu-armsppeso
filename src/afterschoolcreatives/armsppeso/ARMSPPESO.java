package afterschoolcreatives.armsppeso;

import afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import afterschoolcreatives.armsppeso.ui.MainMenu;
import afterschoolcreatives.armsppeso.ui.SplashScreen;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jhon Melvin
 */
public class ARMSPPESO extends Application {

    /**
     * Default Call for Java FX.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        SplashScreen ss = new SplashScreen();
        Pane root = ss.load();

        // prepare settings for splash screen
        Scene splashScene = new Scene(root, 600, 400);
        Stage splashStage = new Stage();
        splashStage.initModality(Modality.APPLICATION_MODAL);
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setResizable(false);
        splashStage.setScene(splashScene);
        splashStage.centerOnScreen();
        splashStage.show();

        Thread splashWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(3000); // show splash screen for 3 seconds
                Platform.runLater(() -> {
                    splashStage.close(); // close the splash screen
                    // display main menu
                    this.showMain(primaryStage);
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        splashWaitingThread.setDaemon(true);
        splashWaitingThread.start();
    }

    /**
     * Show the main menu.
     *
     * @param primaryStage
     */
    public void showMain(Stage primaryStage) {
        MainMenu main = new MainMenu();
        Pane root = main.load();
        Scene primaryScene = new Scene(root);
        primaryScene.setFill(Color.web("#FFFFFF"));
        //
        primaryStage.setMinHeight(700.0);
        primaryStage.setMinWidth(1024.0);
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Automated Record Management System of Public Placement and Employment Service Office");
        primaryStage.getIcons().add(Context.app().getDrawableImage("app_icon.png"));
        // maximized
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
