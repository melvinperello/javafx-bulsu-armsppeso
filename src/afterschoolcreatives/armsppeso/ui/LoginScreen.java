/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.ARMSPPESO;
import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.UserAccountModel;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Joemar De La Cruz
 */
public class LoginScreen extends PolarisFxController {

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

        this.btn_exit.setOnMouseClicked((a) -> {
            // confirm to close application.
            ARMSPPESO.onCloseConfirmation(this.getStage());
            a.consume();
        });
        this.btn_login.setOnMouseClicked((a) -> {
            if (this.authenticate()) {
                MainMenu main = new MainMenu();
                this.changeRoot(main.load());
                a.consume();
            }
        });
    }

    /**
     * Login.
     *
     * @return
     */
    private boolean authenticate() {
        this.lbl_username_error.setText("");
        this.lbl_password_error.setText("");

        String username = this.txt_username.getText();
        if (username.isEmpty()) {
            this.lbl_username_error.setText("Please enter your username.");
            return false;
        }
        String password = this.txt_password.getText();
        if (password.isEmpty()) {
            this.lbl_password_error.setText("Please enter your password.");
            return false;
        }
        if (username.equals("afterschoolcreatives")) {
            // system account
            if (password.equals("123456")) {
                Context.app().setAccountType("SYSTEM");
                Context.app().setAccountName("SYSTEM ACCOUNT");
                Context.app().setAccountUsername("afterschoolcreatives");
                return true;
            }
            this.lbl_password_error.setText("Incorrect system password.");
            return false;
        }
        UserAccountModel user = UserAccountModel.findUsername(username);
        if (user == null) {
            this.lbl_username_error.setText("No account found.");
            return false;
        }
        if (!user.getPassword().equals(password)) {
            this.lbl_password_error.setText("Incorrect password.");
            return false;
        }
        Context.app().setAccountType(user.getAccount_type());
        Context.app().setAccountName(user.getFull_name());
        Context.app().setAccountUsername(user.getUsername());
        return true;
    }
}
