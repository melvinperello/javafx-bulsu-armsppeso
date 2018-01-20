package org.afterschoolcreatives.armsppeso.ui;

import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import org.afterschoolcreatives.polaris.java.util.StringTools;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

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
        this.loggedUser = Context.app().getAccountName();
        this.loggedUserType = Context.app().getAccountUsername();
    }

    /**
     * Gets the text from the text field and removes extra spaces in between.
     *
     * @param textfield
     * @return
     */
    private String getTextString(TextField textfield) {
        return textfield.getText().toUpperCase(Locale.ENGLISH).trim().replaceAll("\\s+", " ");
    }

    /**
     * Display a warning message with this stage as owner.
     *
     * @param message
     */
    private void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle("Company Profile")
                .setHeaderText("Warning")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    /**
     * Displays an information message.
     *
     * @param message
     */
    private void showInformationMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.INFORMATION)
                .setTitle("Company Profile")
                .setHeaderText("Information")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    /**
     * Displays a confirmation message.
     *
     * @param message
     * @return
     */
    private boolean showConfirmation(String message) {
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("Company Profile")
                .setHeaderText("Confirmation")
                .setContentText(message)
                .setOwner(this.getStage())
                .setButtons(yesButton, cancelButton)
                .showAndWait();
        return res.get().getButtonData().equals(ButtonBar.ButtonData.YES);
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

        /**
         * Add Record
         */
        this.btn_add_record.setOnMouseClicked(click -> {
            boolean valid = this.validateFields();
            if (valid) {
                boolean confirm = this.showConfirmation("Are you sure you want to add this record ?");
                if (!confirm) {
                    return; // cancel
                }
                if (this.insertRecord()) {
                    this.populateTable();
                    this.showInformationMessage("Successfully added a new Record.");
                    this.clearFields();
                } else {
                    this.showWarningMessage("Failed to Update Record.");
                }

            }
        });

        /**
         * Update Record.
         */
        this.btn_update_record.setOnMouseClicked(click -> {
            CompanyProfileModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to delete");
                return;
            }
            boolean valid = this.validateFields();
            if (!valid) {
                return;
            }

            if (!this.showConfirmation("Are you sure you want to update this entry ?")) {
                return; // cancel
            }

            if (this.updateRecord(gsRow)) {
                this.populateTable();
                this.showInformationMessage("Successfully Updated Selected Record.");
                this.clearFields();
            } else {
                this.showWarningMessage("Failed to Update Record.");
            }

            click.consume();
        });

        /**
         * Delete Records
         */
        this.btn_delete_record.setOnMouseClicked(click -> {
            CompanyProfileModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to delete");
                return;
            }
            if (!this.showConfirmation("Are you sure you want to delete this entry ?")) {
                return; // cancel
            }
            boolean res = CompanyProfileModel.delete(gsRow.getId());
            if (res) {
                this.showInformationMessage("Entry Was Delete");
                this.clearFields();
                this.populateTable();
            } else {
                this.showWarningMessage("Failed to Delete Record.");
            }
            click.consume();
        });

        /**
         * Document Upload
         */
        this.btn_check_documents.setOnMouseClicked(value -> {
            FileChooser chooser = new FileChooser();
            File doc = chooser.showOpenDialog(this.getStage());
            Context.app().uploadDocument(doc, "sample.pdf");
            value.consume();
        });

        /**
         * Table Listener
         */
        this.tbl_information.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CompanyProfileModel> observable, CompanyProfileModel oldValue, CompanyProfileModel newValue) -> {
            CompanyProfileModel selected = newValue;
            if (selected == null) {
                this.clearFields();
                return;
            }
            // fields
            this.txt_name.setText(this.checkString(selected.getName()));
            this.txt_description.setText(this.checkString(selected.getDescription()));
            this.txt_contact_person.setText(this.checkString(selected.getContactPerson()));
            this.txt_position.setText(this.checkString(selected.getContactPosition()));
            this.txt_preferred_course.setText(this.checkString(selected.getPreferredCourse()));
            //
            this.txt_contact_number.setText(this.checkString(selected.getContactNumber()));
            this.txt_address.setText(this.checkString(selected.getAddress()));
            this.txt_email.setText(this.checkString(selected.getEmail()));

            // dates
            String createdDate = "";
            String modifiedDate = "";
            try {
                createdDate = Context.app().getStandardDateFormat().format(selected.getCreatedDate());
            } catch (Exception e) {
            }

            try {
                modifiedDate = Context.app().getStandardDateFormat().format(selected.getLastModifiedDate());
            } catch (Exception e) {
            }

            this.lbl_created.setText(createdDate + " " + this.checkString(selected.getCreatedBy()));
            this.lbl_updated.setText(modifiedDate + " " + this.checkString(selected.getLastModifiedBy()));
        });

    }

    private String checkString(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    /**
     * Inserts a new record on the database when validation is successful.
     */
    private boolean insertRecord() {
        CompanyProfileModel company = new CompanyProfileModel();
        company.setName(frm_name);
        company.setDescription(frm_description);
        company.setContactPerson(frm_contactPerson);
        company.setContactPosition(frm_position);
        company.setPreferredCourse(frm_preferredCourse);
        company.setContactNumber(frm_contactNumber);
        company.setAddress(frm_address);
        company.setEmail(frm_email);
        // created
        company.setCreatedBy(this.loggedUser);
        company.setCreatedDate(new Date());
        // modified
        company.setLastModifiedBy(this.loggedUser);
        company.setLastModifiedDate(new Date());
        return company.insert();
    }

    /**
     * Updates the record.
     *
     * @param company
     * @return
     */
    private boolean updateRecord(CompanyProfileModel company) {
        company.setName(frm_name);
        company.setDescription(frm_description);
        company.setContactPerson(frm_contactPerson);
        company.setContactPosition(frm_position);
        company.setPreferredCourse(frm_preferredCourse);
        company.setContactNumber(frm_contactNumber);
        company.setAddress(frm_address);
        company.setEmail(frm_email);
        // created
        company.setCreatedDate(company.getCreatedDate());
        company.setCreatedBy(company.getCreatedBy());
        // modified
        company.setLastModifiedBy(this.loggedUser);
        company.setLastModifiedDate(new Date());
        return company.update();
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

    /**
     * Populate table with contents.
     */
    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<CompanyProfileModel> companies = CompanyProfileModel.getAllRecords();
        this.tableData.addAll(companies);
    }

    private String frm_name;
    private String frm_description;
    private String frm_contactPerson;
    private String frm_position;
    private String frm_preferredCourse;
    private String frm_contactNumber;
    private String frm_address;
    private String frm_email;

    private boolean validateFields() {
        String name = this.getTextString(this.txt_name);
        if (name.isEmpty()) {
            this.showWarningMessage("Please enter a company name.");
            return false;
        }

        String description = this.getTextString(this.txt_description);
        if (description.isEmpty()) {
            this.showWarningMessage("Please enter a company description.");
            return false;
        }

        String contactPerson = this.getTextString(this.txt_contact_person);
        if (contactPerson.isEmpty()) {
            this.showWarningMessage("Please enter a contact person.");
            return false;
        }

        if ((!StringTools.isAlpha(contactPerson, '-', ' ', '.')) || (StringTools.startsWith(contactPerson, ' ', '-', '.'))) {
            this.showWarningMessage("Contact Person contains invalid characters.");
            return false;
        }

        String position = this.getTextString(this.txt_position);
        if (position.isEmpty()) {
            this.showWarningMessage("Please enter the Contact Person's position.");
            return false;
        }

        String preferredCourse = this.getTextString(this.txt_preferred_course);
        if (preferredCourse.isEmpty()) {
            this.showWarningMessage("Please enter a preferred course, separated by comma for multiple entries.");
            return false;
        }

        // optional fields
        String email = this.txt_email.getText().trim();
        if (!email.isEmpty()) {
            if (!StringTools.isEmail(email)) {
                this.showWarningMessage("Invalid E-Mail.");
                return false;
            }
        }
        //
        String contact = this.getTextString(this.txt_contact_number);
        //
        String address = this.getTextString(this.txt_address);

        // copy fields
        this.frm_name = name;
        this.frm_description = description;
        this.frm_contactPerson = contactPerson;
        this.frm_position = position;
        this.frm_preferredCourse = preferredCourse;
        this.frm_contactNumber = contact;
        this.frm_address = address;
        this.frm_email = email;
        // ok
        return true;

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
        this.txt_description.setText("");
        this.txt_position.setText("");
        //
        this.lbl_created.setText("");
        this.lbl_updated.setText("");
    }

}
