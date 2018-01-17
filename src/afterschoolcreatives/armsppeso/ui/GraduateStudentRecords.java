package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.polaris.javafx.FXController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jhon Melvin
 */
public class GraduateStudentRecords extends FXController {

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_main_from_student;

    @FXML
    private TableView tbl_information;

    @FXML
    private TextField txt_last_name;

    @FXML
    private TextField txt_first_name;

    @FXML
    private TextField txt_middle_name;

    @FXML
    private TextField txt_course;

    @FXML
    private TextField txt_graduation_date;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_contact;

    @FXML
    private TextField txt_address;

    @FXML
    private Label lbl_created;

    @FXML
    private Label lbl_updated;

    @FXML
    private JFXButton btn_add_record;

    @FXML
    private JFXButton btn_update_record;

    @FXML
    private JFXButton btn_delete_record;

    @Override
    protected void setup() {
        this.btn_main_from_student.setOnMouseClicked(click -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            click.consume();
        });
    }

}
