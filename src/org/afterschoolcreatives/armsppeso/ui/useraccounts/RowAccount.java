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
