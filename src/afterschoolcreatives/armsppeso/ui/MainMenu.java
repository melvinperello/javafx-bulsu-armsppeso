package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.polaris.javafx.FXController;
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
public class MainMenu extends FXController {

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
        try (ConnectionManager con = Context.app().getConnectionFactory().createConnectionManager()) {
            final String countStudent = "SELECT COUNT(*) as count FROM (SELECT `_rowid_`,* FROM `graduated_students` ORDER BY `_rowid_` ASC);";
            final String countProfile = "SELECT COUNT(*) as count FROM (SELECT `_rowid_`,* FROM `company_profile` ORDER BY `_rowid_` ASC);";
            DataSet student_ds = con.fetch(countStudent);
            DataSet profile_ds = con.fetch(countProfile);
            // store values
            String student_count = student_ds.get(0).getValue("count").toString();
            String profile_count = profile_ds.get(0).getValue("count").toString();
            // set values
            this.lblStudentCount.setText(student_count);
            this.lblProfileCount.setText(profile_count);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

}
