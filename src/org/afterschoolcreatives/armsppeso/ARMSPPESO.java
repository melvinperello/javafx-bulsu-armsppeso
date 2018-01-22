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

import org.afterschoolcreatives.armsppeso.models.DatabaseTables;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.afterschoolcreatives.armsppeso.ui.LoginScreen;
import org.afterschoolcreatives.armsppeso.ui.SplashScreen;

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
        splashStage.getIcons().add(Context.app().getDrawableImage("ac_icon.png"));
        splashStage.setScene(splashScene);
        splashStage.centerOnScreen();
        splashStage.show();

        Thread splashWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(3000); // verify splash screen for 3 seconds
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

        try {
            DatabaseTables.create();
        } catch (SQLException | RuntimeException ex) {
            PolarisDialog.create(PolarisDialog.Type.ERROR)
                    .setTitle("Error")
                    .setHeaderText("Internal Error")
                    .setContentText("The System cannot verify the existence of the database file. " + ex.getMessage())
                    .showAndWait();
            Platform.exit(); // exit java fx
            System.exit(-1); // internal error
        }

//        MainMenu main = new MainMenu();
        LoginScreen main = new LoginScreen();
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
        // on close
        primaryStage.setOnCloseRequest(close -> {
            ARMSPPESO.onCloseConfirmation(primaryStage);
            close.consume();
        });
        primaryStage.show();
    }

    public static void onCloseConfirmation(Stage owner) {
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("Exit")
                .setOwner(owner)
                .setHeaderText("Close Application ?")
                .setContentText("Are you sure you want to close the application ?")
                .showAndWait();
        if (res.get().getText().equals("OK")) {
            Platform.exit(); // exit java fx
            System.exit(0); // ok exit
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
