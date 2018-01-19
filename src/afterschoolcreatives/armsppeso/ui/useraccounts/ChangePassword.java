/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.ui.useraccounts;

import afterschoolcreatives.armsppeso.ui.MainMenu;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

/**
 *
 * @author Jhon Melvin
 */
public class ChangePassword extends PolarisFxController {

    @FXML
    private PasswordField txt_current;

    @FXML
    private PasswordField txt_new;

    @FXML
    private PasswordField txt_confirm;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    @Override
    protected void setup() {
        this.btn_cancel.setOnMouseClicked(value -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            value.consume();
        });

        this.btn_save.setOnMouseClicked(value -> {
            // code here
            value.consume();
        });
    }

}
