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

    }

    private void countRecords() {

        this.lblStudentCount.setText(GraduatedStudentModel.getTotalRecords());
        this.lblProfileCount.setText(CompanyProfileModel.getTotalRecords());

    }

}
