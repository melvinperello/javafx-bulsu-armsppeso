package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import afterschoolcreatives.polaris.java.sql.ConnectionManager;
import afterschoolcreatives.polaris.java.sql.DataSet;
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
    private Label lbl_user;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_type;

    @FXML
    private JFXButton btn_adduser;

    @FXML
    private JFXButton btn_change_password;

    @Override
    protected void setup() {
        // make 0
        this.lblStudentCount.setText("0");
        this.lblProfileCount.setText("0");
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

        this.setupDisplay();
    }

    private void countRecords() {

        this.lblStudentCount.setText(GraduatedStudentModel.getTotalRecords());
        this.lblProfileCount.setText(CompanyProfileModel.getTotalRecords());

    }

    private void setupDisplay() {
        this.lbl_name.setText(Context.getName());
        this.lbl_type.setText(Context.getAccount_type());
        this.lbl_user.setText(Context.getUsername());
        
        this.btn_change_password.setDisable(Context.getAccount_type().equalsIgnoreCase("SYSTEM"));      
    }
}
