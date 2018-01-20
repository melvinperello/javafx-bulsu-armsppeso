/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.ui.useraccounts;


import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.UserAccountModel;
import org.afterschoolcreatives.armsppeso.ui.MainMenu;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class ChangePassword extends PolarisFxController {

    @FXML
    private PasswordField txt_current;

    @FXML
    private Label lbl_error_current;

    @FXML
    private PasswordField txt_new;

    @FXML
    private Label lbl_error_new;

    @FXML
    private PasswordField txt_confirm;

    @FXML
    private Label lbl_error_confirm;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    private UserAccountModel userAccount = null;
    private MainMenu mm = new MainMenu();
    @Override
    protected void setup() {
        this.resetErrorLabels();
        
        this.btn_cancel.setOnMouseClicked(value -> {
            this.changeRoot(mm.load());
            value.consume();
        });
        
        if(Context.app().getAccountId() != null) {
            userAccount = UserAccountModel.find(Context.app().getAccountId());
        }
        
        this.btn_save.setOnMouseClicked(value -> {
            this.resetErrorLabels();
            if(userAccount != null) {
                String current = this.txt_current.getText();
                if(!current.equals(userAccount.getPassword())) {
                    this.lbl_error_current.setText("Incorrect current password. *");
                    return;
                }
                String newPass = this.txt_new.getText();
                if(newPass.length()<6) {
                    this.lbl_error_new.setText("Password must be at least six(6) in length. *");
                    return;
                }
                String confirm = this.txt_confirm.getText();
                if(!confirm.equals(newPass)) {
                    this.lbl_error_confirm.setText("New password not matched. *");
                    return;
                }
                userAccount.setPassword(newPass);
                boolean res = userAccount.update();
                if(res) {
                    PolarisDialog.create(PolarisDialog.Type.INFORMATION)
                            .setOwner(this.getStage())
                            .setHeaderText("Successfully Updated!")
                            .setContentText("Account password successfully updated.")
                            .setTitle("Change Password")
                            .show();
                    this.changeRoot(mm.load());
                    value.consume();
                } else {
                    PolarisDialog.create(PolarisDialog.Type.ERROR)
                            .setOwner(this.getStage())
                            .setHeaderText("Failed!")
                            .setContentText("Account password failed in update.")
                            .setTitle("Change Password")
                            .show();
                }
            } else {
                PolarisDialog.create(PolarisDialog.Type.ERROR)
                        .setHeaderText("Failed!")
                        .setContentText("No account found to continue change password.")
                        .setTitle("Change Password")
                        .show();
            }
            value.consume();
        });
    }

    private void resetErrorLabels() {
        this.lbl_error_confirm.setText("");
        this.lbl_error_current.setText("");
        this.lbl_error_new.setText("");
    }
}
