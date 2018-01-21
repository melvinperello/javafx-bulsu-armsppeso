/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.ui;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.InquiryModel;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class InquiryRecords extends PolarisFxController {

    @FXML
    private JFXButton btn_main_from_student;

    @FXML
    private TextField txt_search;

    @FXML
    private TableView<InquiryModel> tbl_information;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_representative;

    @FXML
    private TextField txt_address;

    @FXML
    private ComboBox<String> cmb_concern;

    @FXML
    private TextField txt_contact;

    @FXML
    private TextArea txt_description;

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

    public InquiryRecords() {
        this.tableData = FXCollections.observableArrayList();
        this.loggedUser = Context.app().getAccountName();
        this.loggedUserType = Context.app().getAccountType();
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
                .setTitle("Inquiry")
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
                .setTitle("Inquiry")
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
                .setTitle("Inquiry")
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
    private final ObservableList<InquiryModel> tableData;

    private final String loggedUser;
    private final String loggedUserType;

    @Override
    protected void setup() {
        if (this.loggedUserType.equalsIgnoreCase("USER")) {
            this.btn_add_record.setDisable(true);
            this.btn_delete_record.setDisable(true);
            this.btn_update_record.setDisable(true);
        }

        this.createTable();
        this.populateTable();
        /**
         *
         */
        this.lbl_created.setText("");
        this.lbl_updated.setText("");
        /**
         * Initialize combo box.
         */
        this.cmb_concern.getItems().setAll("Tie Up", "Job Fair", "Posting of Vacancy", "List of Graduates", "Others");
        this.cmb_concern.getSelectionModel().selectFirst();

        /**
         * Back to main menu.
         */
        this.btn_main_from_student.setOnMouseClicked(click -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            click.consume();
        });

        /**
         * Clear Fields
         */
        this.btn_clear.setOnMouseClicked(click -> {
            this.clearFields();
            click.consume();
        });

        /**
         * Add new record.
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
                    this.showWarningMessage("Failed to Insert Record.");
                }

            }
        });

        /**
         * Delete Records
         */
        this.btn_delete_record.setOnMouseClicked(click -> {
            InquiryModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to delete");
                return;
            }
            if (!this.showConfirmation("Are you sure you want to delete this entry ?")) {
                return; // cancel
            }
            boolean res = InquiryModel.delete(gsRow.getId());
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
         * Update Record.
         */
        this.btn_update_record.setOnMouseClicked(click -> {
            InquiryModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
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
         * Table Listener
         */
        this.tbl_information.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends InquiryModel> observable, InquiryModel oldValue, InquiryModel newValue) -> {
            InquiryModel selected = newValue;
            if (selected == null) {
                this.clearFields();
                return;
            }

            this.txt_name.setText(this.checkString(newValue.getName()));
            this.txt_representative.setText(this.checkString(newValue.getRepresentative()));
            this.txt_address.setText(this.checkString(newValue.getAddress()));
            // get concern
            if (newValue.getConcern() != null) {
                boolean match = false;
                for (int x = 0; x < this.cmb_concern.getItems().size(); x++) {
                    if (this.cmb_concern.getItems().get(x).equalsIgnoreCase(newValue.getConcern())) {
                        this.cmb_concern.getSelectionModel().select(x);
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    this.cmb_concern.getSelectionModel().selectFirst();
                }
            } else {
                this.cmb_concern.getSelectionModel().selectFirst();
            }
            //
            this.txt_contact.setText(this.checkString(newValue.getContact()));
            this.txt_description.setText(this.checkString(newValue.getDescription()));
            //
            this.lbl_created.setText(this.checkString(selected.getCreatedDate()) + " " + this.checkString(selected.getCreatedBy()));
            this.lbl_updated.setText(this.checkString(selected.getLastModifiedDate()) + " " + this.checkString(selected.getLastModifiedBy()));
        });

    }

    /**
     * Listener Check Value to avoid null when listening to values.
     *
     * @param value
     * @return
     */
    private String checkString(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    private String frm_name;
    private String frm_representative;
    private String frm_address;
    private String frm_concern;
    private String frm_contact;
    private String frm_description;

    /**
     * Validate Fields.
     *
     * @return
     */
    private boolean validateFields() {
        String name = this.getTextString(this.txt_name);
        if (name.isEmpty()) {
            this.showWarningMessage("Please enter company name.");
            return false;
        }

        String representative = this.getTextString(this.txt_name);
        if (representative.isEmpty()) {
            this.showWarningMessage("Please enter company representative.");
            return false;
        }

        String address = this.getTextString(this.txt_address);
        if (address.isEmpty()) {
            this.showWarningMessage("Please enter company address.");
            return false;
        }
        //
        String concern = this.cmb_concern.getSelectionModel().getSelectedItem().toUpperCase(Locale.ENGLISH);
        //
        String contact = this.getTextString(this.txt_contact);
        //
        String description = this.txt_description.getText().trim();
        //
        this.frm_name = name;
        this.frm_representative = representative;
        this.frm_address = address;
        this.frm_concern = concern;
        this.frm_contact = contact;
        this.frm_description = description;
        // ok
        return true;
    }

    /**
     * Create the table including predicate.
     */
    private void createTable() {
        TableColumn<InquiryModel, String> numberCol = new TableColumn<>("#");
        numberCol.setPrefWidth(50.0);
        numberCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getId().toString()));

        TableColumn<InquiryModel, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(130.0);
        dateCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCreatedDate()));

        TableColumn<InquiryModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(200.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));

        TableColumn<InquiryModel, String> repCol = new TableColumn<>("Representative");
        repCol.setPrefWidth(200.0);
        repCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getRepresentative()));

        TableColumn<InquiryModel, String> addressCol = new TableColumn<>("Address");
        addressCol.setPrefWidth(220.0);
        addressCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress()));

        TableColumn<InquiryModel, String> concernCol = new TableColumn<>("Concern");
        concernCol.setPrefWidth(150.0);
        concernCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getConcern()));

        this.tbl_information.getColumns().addAll(numberCol, dateCol, nameCol, repCol, addressCol, concernCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<InquiryModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((InquiryModel company) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (company.getName().toLowerCase().contains(filterString)) {
                    return true;
                }

                // if contains in the name
                if (company.getCreatedDate().toLowerCase().contains(filterString)) {
                    return true;
                }

                // same id
                if (company.getId().toString().equalsIgnoreCase(filterString)) {
                    return true;
                }

                if (company.getRepresentative().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getAddress().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getConcern().toLowerCase().contains(filterString)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<InquiryModel> sortedData = new SortedList<>(filteredResult);

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
        ArrayList<InquiryModel> inquiries = InquiryModel.getAllRecords();
        this.tableData.addAll(inquiries);
    }

    /**
     * Inserts a new record on the database when validation is successful.
     */
    private boolean insertRecord() {
        InquiryModel inquiry = new InquiryModel();
        inquiry.setName(frm_name);
        inquiry.setRepresentative(frm_representative);
        inquiry.setAddress(frm_address);
        inquiry.setConcern(frm_concern);
        inquiry.setContact(frm_contact);
        inquiry.setDescription(frm_description);
        // created
        inquiry.setCreatedBy(this.loggedUser);
        inquiry.setCreatedDate(Context.app().getStandardDateFormat().format(new Date()));
        // modified
        inquiry.setLastModifiedBy(this.loggedUser);
        inquiry.setLastModifiedDate(Context.app().getStandardDateFormat().format(new Date()));
        return inquiry.insert();
    }

    /**
     * Updates the record.
     *
     * @param inquiry
     * @return
     */
    private boolean updateRecord(InquiryModel inquiry) {
        inquiry.setName(frm_name);
        inquiry.setRepresentative(frm_representative);
        inquiry.setAddress(frm_address);
        inquiry.setConcern(frm_concern);
        inquiry.setContact(frm_contact);
        inquiry.setDescription(frm_description);
        // created
        inquiry.setCreatedBy(inquiry.getCreatedBy());
        inquiry.setCreatedDate(inquiry.getCreatedDate());
        // modified
        inquiry.setLastModifiedBy(this.loggedUser);
        inquiry.setLastModifiedDate(Context.app().getStandardDateFormat().format(new Date()));
        return inquiry.update();
    }

    /**
     * Clear the fields of the form.
     */
    private void clearFields() {
        this.txt_name.setText("");
        this.txt_representative.setText("");
        this.txt_address.setText("");
        this.cmb_concern.getSelectionModel().selectFirst();
        this.txt_contact.setText("");
        this.txt_description.setText("");
        this.lbl_created.setText("");
        this.lbl_updated.setText("");
    }

}
