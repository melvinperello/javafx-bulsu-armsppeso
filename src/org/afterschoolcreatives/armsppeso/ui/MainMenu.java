package org.afterschoolcreatives.armsppeso.ui;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import org.afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import org.afterschoolcreatives.armsppeso.ui.useraccounts.ChangePassword;
import org.afterschoolcreatives.armsppeso.ui.useraccounts.UserAccounts;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Jhon Melvin
 */
public class MainMenu extends PolarisFxController {

    @FXML
    private Label lblStudentCount;

    @FXML
    private JFXButton btnManageStudents;

    @FXML
    private Label lblProfileCount;

    @FXML
    private JFXButton btnManageProfiles;

    @FXML
    private Label lbl_inquiry_count;

    @FXML
    private JFXButton btn_manage_inquiries;

    @FXML
    private Label lbl_user;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_type;

    @FXML
    private JFXButton btn_useraccounts;

    @FXML
    private JFXButton btn_change_password;

    @Override
    protected void setup() {
        // make 0
        this.lblStudentCount.setText("0");
        this.lblProfileCount.setText("0");
        this.lbl_inquiry_count.setText("0");
        // get record count
        this.countRecords();

        btnManageProfiles.setOnMouseClicked(value -> {
            CompanyProfileRecords gsr = new CompanyProfileRecords();
            this.changeRoot(gsr.load());
            value.consume();
        });

        btnManageStudents.setOnMouseClicked(value -> {
            GraduateStudentRecords gsr = new GraduateStudentRecords();
            this.changeRoot(gsr.load());
            value.consume();
        });

        this.btn_change_password.setOnMouseClicked(value -> {
            ChangePassword cp = new ChangePassword();
            this.changeRoot(cp.load());
            value.consume();
        });

        this.setupDisplay();

        this.btn_useraccounts.setOnMouseClicked(value -> {
            UserAccounts ua = new UserAccounts();
            this.changeRoot(ua.load());
            value.consume();
        });

        this.btn_useraccounts.setDisable(Context.app().getAccountType().equalsIgnoreCase("USER"));
    }

    /**
     * Counts the records from the tables.
     */
    private void countRecords() {

        this.lblStudentCount.setText(GraduatedStudentModel.getTotalRecords());
        this.lblProfileCount.setText(CompanyProfileModel.getTotalRecords());

    }

    /**
     * Account information display.
     */
    private void setupDisplay() {
        this.lbl_name.setText(Context.app().getAccountName());
        this.lbl_type.setText(Context.app().getAccountType());
        this.lbl_user.setText(Context.app().getAccountUsername());

        this.btn_change_password.setDisable(Context.app().getAccountType().equalsIgnoreCase("SYSTEM"));
    }
}
