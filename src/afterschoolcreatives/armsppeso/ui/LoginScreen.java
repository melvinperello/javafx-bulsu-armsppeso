/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.UserAccountModel;
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
            if(this.authenticate()) {
                MainMenu main = new MainMenu();
                this.changeRoot(main.load());
                a.consume();
            }
        });
    }
    
    private boolean authenticate() {
        this.lbl_username_error.setText("");
        this.lbl_password_error.setText("");
        
        String username = this.txt_username.getText();
        if(username.isEmpty()) {
            this.lbl_username_error.setText("Please enter your username.");
            return false;
        }
        String password = this.txt_password.getText();
        if(password.isEmpty()) {
            this.lbl_password_error.setText("Please enter your password.");
            return false;
        }
        if(username.equals("afterschoolcreatives")) {
            // system account
            if(password.equals("iloveafterschoolcreatives")) {
                Context.setAccount_type("SYSTEM");
                Context.setName("SYSTEM");
                Context.setUsername("afterschoolcreatives");
                return true;
            }
            this.lbl_password_error.setText("Incorrect system password.");
            return false;
        } else {
            UserAccountModel user = UserAccountModel.findUsername(username);
            if(user == null) {
                this.lbl_username_error.setText("No account found.");
                return false;
            }
            if(!user.getPassword().equals(password)) {
                this.lbl_password_error.setText("Incorrect password.");
                return false;
            }
            return true;
        }
    }
}
