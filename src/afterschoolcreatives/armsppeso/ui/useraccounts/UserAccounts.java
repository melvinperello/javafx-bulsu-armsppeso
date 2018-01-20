/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afterschoolcreatives.armsppeso.ui.useraccounts;

import afterschoolcreatives.armsppeso.models.UserAccountModel;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import afterschoolcreatives.polaris.javafx.scene.control.simpletable.SimpleTable;
import afterschoolcreatives.polaris.javafx.scene.control.simpletable.SimpleTableCell;
import afterschoolcreatives.polaris.javafx.scene.control.simpletable.SimpleTableRow;
import afterschoolcreatives.polaris.javafx.scene.control.simpletable.SimpleTableView;
import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Joemar
 */
public class UserAccounts extends PolarisFxController {
    
    @FXML
    private TextField txt_search_key;

    @FXML
    private JFXButton btn_search;

    @FXML
    private VBox tbl_accounts;

    @FXML
    private TextField txt_full_name;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private ComboBox<String> cmb_account_type;

    @FXML
    private JFXButton btn_add_account;

    private SimpleTable tableAccounts = new SimpleTable();
    @Override
    protected void setup() {
        ArrayList<UserAccountModel> allRecords = UserAccountModel.getAllRecords();
        for(UserAccountModel account : allRecords) {
            this.createRow(account);
        }
        SimpleTableView simpleTableView = new SimpleTableView();
        simpleTableView.setTable(tableAccounts);
        simpleTableView.setFixedWidth(true);
        simpleTableView.setParentOnScene(tbl_accounts);
    }
    
    private void createRow(UserAccountModel account) {
        SimpleTableRow row = new SimpleTableRow();
        row.setRowHeight(70.0);
        RowAccount userRow = new RowAccount();
        userRow.load();
        userRow.getBtn_edit().setOnMouseClicked(value->{
            System.out.println("CLICKED");
        });
        
        SimpleTableCell cellParent = new SimpleTableCell();
        cellParent.setResizePriority(Priority.ALWAYS);
        cellParent.setContent(userRow.getRootPane());

        row.addCell(cellParent);
        tableAccounts.addRow(row);
    }
    
}
