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
package org.afterschoolcreatives.armsppeso.ui;

import org.afterschoolcreatives.armsppeso.ARMSPPESO;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.UserAccountModel;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
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
                Context.app().setAccountId(null);
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
        Context.app().setAccountId(user.getId());
        Context.app().setAccountType(user.getAccount_type());
        Context.app().setAccountName(user.getFull_name());
        Context.app().setAccountUsername(user.getUsername());
        return true;
    }
}
