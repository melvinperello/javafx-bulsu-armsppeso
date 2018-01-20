package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyProfileRecords extends PolarisFxController {

    @FXML
    private JFXButton btn_main_from_profile;

    @FXML
    private TextField txt_search;

    @FXML
    private TableView<CompanyProfileModel> tbl_information;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_description;

    @FXML
    private TextField txt_contact_person;

    @FXML
    private TextField txt_position;

    @FXML
    private TextField txt_contact_number;

    @FXML
    private TextField txt_preferred_course;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_email;

    @FXML
    private Label lbl_created;

    @FXML
    private Label lbl_updated;

    @FXML
    private JFXButton btn_add_record;

    @FXML
    private JFXButton btn_update_record;

    @FXML
    private JFXButton btn_check_documents;

    @FXML
    private JFXButton btn_delete_record;

    public CompanyProfileRecords() {
        this.tableData = FXCollections.observableArrayList();
        this.loggedUser = "JHON MELVIN PERELLO";
        this.loggedUserType = "ADMINISTRATOR";
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<CompanyProfileModel> tableData;

    private final String loggedUser;
    private final String loggedUserType;

    @Override
    protected void setup() {

        this.createTable(); // create table with predicate
        this.populateTable(); //refresh

        this.lbl_created.setText("");
        this.lbl_updated.setText("");
        /**
         * Back to main menu.
         */
        this.btn_main_from_profile.setOnMouseClicked(value -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            value.consume();
        });

        /**
         * Clear fields.
         */
        this.btn_clear.setOnMouseClicked(value -> {
            this.clearFields();
            value.consume();
        });

    }

    /**
     * Create the table including predicate.
     */
    private void createTable() {
        TableColumn<CompanyProfileModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(180.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));

        TableColumn<CompanyProfileModel, String> descriptCol = new TableColumn<>("Description");
        descriptCol.setPrefWidth(300.0);
        descriptCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDescription()));

        TableColumn<CompanyProfileModel, String> contactPersonCol = new TableColumn<>("Contact Person");
        contactPersonCol.setPrefWidth(150.0);
        contactPersonCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getContactPerson()));

        TableColumn<CompanyProfileModel, String> positionCol = new TableColumn<>("Position");
        positionCol.setPrefWidth(150.0);
        positionCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getContactPosition()));

        TableColumn<CompanyProfileModel, String> prefCol = new TableColumn<>("Preferred Course");
        prefCol.setPrefWidth(150.0);
        prefCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getPreferredCourse()));

        this.tbl_information.getColumns().addAll(nameCol, descriptCol, contactPersonCol, positionCol, prefCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<CompanyProfileModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((CompanyProfileModel company) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (company.getName().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getDescription().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getContactPerson().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getContactPosition().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getPreferredCourse().toLowerCase().contains(filterString)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<CompanyProfileModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_information.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_information.setItems(sortedData);
    }

    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<CompanyProfileModel> companies = CompanyProfileModel.getAllRecords();
        this.tableData.addAll(companies);
    }

    /**
     * Clear all fields.
     */
    private void clearFields() {
        this.txt_name.setText("");
        this.txt_preferred_course.setText("");
        this.txt_address.setText("");
        this.txt_contact_number.setText("");
        this.txt_contact_person.setText("");
        this.txt_email.setText("");
        //
        this.lbl_created.setText("");
        this.lbl_updated.setText("");
    }

}
