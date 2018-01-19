/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jhon Melvin
 */
public class LoginScreen extends PolarisFxController {
    
    @FXML
    private VBox application_root;

    @FXML
    private TextField txt_username;

    @FXML
    private Label lbl_username_error;

    @FXML
    private PasswordField txt_password;

    @FXML
    private Label lbl_password_error;

    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXButton btn_exit;

    @Override
    protected void setup() {
        this.lbl_username_error.setText("");
        this.lbl_password_error.setText("");
        
        this.btn_exit.setOnMouseClicked((a)->{
            System.exit(0);
            a.consume();
        });
        this.btn_login.setOnMouseClicked((a)->{
            MainMenu main = new MainMenu();
            this.changeRoot(main.load());
            a.consume();
        });
    }
    
    public void changeRoot(Parent newRoot) throws NullPointerException {
        Stage currentStage = this.getStage();
        this.getRootPane().setPrefWidth(currentStage.getWidth());
        this.getRootPane().setPrefHeight(currentStage.getHeight());
        this.getRootPane().getScene().setRoot(newRoot);
    }
    
    
}
