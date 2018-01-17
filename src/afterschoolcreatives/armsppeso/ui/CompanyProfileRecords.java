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
public class CompanyProfileRecords extends FXController {

    @FXML
    private JFXButton btn_main_from_profile;

    @FXML
    private TextField txt_search;

    @FXML
    private TableView<?> tbl_information;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_contact_person;

    @FXML
    private TextField txt_contact_number;

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
        this.btn_main_from_profile.setOnMouseClicked(value -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            value.consume();
        });
    }

}
