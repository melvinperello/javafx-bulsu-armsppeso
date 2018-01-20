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

/**
 *
 * @author Joemar
 */
public class RowAccount  extends PolarisFxController {

    @FXML
    private Label lbl_full_name;

    @FXML
    private Label lbl_position;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_created_by;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_remove;
    
    @Override
    protected void setup() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Label getLbl_full_name() {
        return lbl_full_name;
    }

    public Label getLbl_position() {
        return lbl_position;
    }

    public Label getLbl_date() {
        return lbl_date;
    }

    public Label getLbl_created_by() {
        return lbl_created_by;
    }

    public JFXButton getBtn_edit() {
        return btn_edit;
    }

    public JFXButton getBtn_remove() {
        return btn_remove;
    }
    
}
