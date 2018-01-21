/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.ui.documents;

import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.stage.FileChooser;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileDocumentModel;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import org.afterschoolcreatives.armsppeso.models.InquiryModel;
import org.afterschoolcreatives.armsppeso.ui.CompanyProfileRecords;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyProfileDocuments extends PolarisFxController {

    @FXML
    private JFXButton btn_main_from_student;

    @FXML
    private TextField txt_search;

    @FXML
    private TableView<CompanyProfileDocumentModel> tbl_information;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private Label lbl_company;

    @FXML
    private ComboBox<String> cmb_type;

    @FXML
    private TextArea txt_description;

    @FXML
    private Label lbl_file_location;

    @FXML
    private Label lbl_created;

    @FXML
    private JFXButton btn_upload;

    @FXML
    private JFXButton btn_open;

    @FXML
    private JFXButton btn_delete_record;

    /**
     * Construct.
     */
    public CompanyProfileDocuments(CompanyProfileRecords parent, CompanyProfileModel model) {
        this.parentController = parent;
        this.parentModel = model;
        // blank
        this.tableData = FXCollections.observableArrayList();
        this.loggedUser = Context.app().getAccountName();
        this.loggedUserType = Context.app().getAccountType();
    }

    private CompanyProfileRecords parentController;
    private CompanyProfileModel parentModel;

    /**
     * Contains the data of the table.
     */
    private final ObservableList<CompanyProfileDocumentModel> tableData;

    private final String loggedUser;
    private final String loggedUserType;

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

    @Override
    protected void setup() {
        if (this.loggedUserType.equalsIgnoreCase("USER")) {
            this.btn_upload.setDisable(true);
            this.btn_delete_record.setDisable(true);
        }

        this.createTable();
        this.populateTable();
        /**
         * Clear Fields
         */
        this.lbl_company.setText("");
        this.txt_description.setText("");
        this.lbl_file_location.setText("");
        this.lbl_created.setText("");

        /**
         * initialize.
         */
        this.cmb_type.getItems().setAll(
                "Letter of Intent",
                "Company Profile",
                "SEC Registration",
                "BIR Registration (FORM 2303)",
                "Mayor's Permit",
                "Phil-Job Net Registration",
                "Location Map",
                "Others"
        );
        this.cmb_type.getSelectionModel().selectFirst();
        this.lbl_company.setText(this.parentModel.getName());

        /**
         * Back to main menu.
         */
        this.btn_main_from_student.setOnMouseClicked(click -> {
            this.changeRoot(this.parentController.getRootPane());
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
         * Delete Records
         */
        this.btn_delete_record.setOnMouseClicked(click -> {
            CompanyProfileDocumentModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to delete");
                return;
            }
            if (!this.showConfirmation("Are you sure you want to delete this entry ?")) {
                return; // cancel
            }
            boolean res = CompanyProfileDocumentModel.delete(gsRow.getId());
            if (res) {
                this.showInformationMessage("Entry Was Delete");
                this.clearFields();
                this.populateTable();
            } else {
                this.showWarningMessage("Failed to Delete Record.");
            }
            click.consume();
        });

        this.btn_open.setOnMouseClicked(click -> {
            CompanyProfileDocumentModel gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to open");
                return;
            }
            File file = new File(Context.DOC_DRIVE + File.separator + gsRow.getLocation());
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                            Desktop.getDesktop().open(file);
                        } else {
                            this.showWarningMessage("The system does not supported opening this file. You can still get the file in the system drive.");
                        }
                    } catch (IOException e) {
                        this.showWarningMessage("There was an error opening the file.");
                    }
                } else {
                    this.showWarningMessage("The system does not supported opening this file. You can still get the file in the system drive.");
                }
            } else {
                this.showWarningMessage("The File is not already existing in the system drive, external modification might be the cause of this problem");
            }
        });

        /**
         * Add.
         */
        this.btn_upload.setOnMouseClicked(click -> {
            boolean confirm = this.showConfirmation("Are you sure you want to add this record ?");
            if (!confirm) {
                return; // cancel
            }

            boolean valid = this.validateFields();
            if (valid) {
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
         * Table Listener.
         */
        this.tbl_information.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CompanyProfileDocumentModel> observable, CompanyProfileDocumentModel oldValue, CompanyProfileDocumentModel newValue) -> {
            CompanyProfileDocumentModel selected = newValue;
            if (selected == null) {
                this.clearFields();
                return;
            }

            // get type
            if (newValue.getType() != null) {
                boolean match = false;
                for (int x = 0; x < this.cmb_type.getItems().size(); x++) {
                    if (this.cmb_type.getItems().get(x).equalsIgnoreCase(newValue.getType())) {
                        this.cmb_type.getSelectionModel().select(x);
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    this.cmb_type.getSelectionModel().selectFirst();
                }
            } else {
                this.cmb_type.getSelectionModel().selectFirst();
            }

            this.lbl_file_location.setText(this.checkString(selected.getLocation()));
            this.txt_description.setText(this.checkString(selected.getDescription()));
            this.lbl_created.setText(this.checkString(selected.getCreatedDate()) + " " + this.checkString(selected.getCreatedBy()));

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

    private String frm_type;
    private String frm_description;
    private String frm_file;

    private boolean validateFields() {
        FileChooser fileChooser = new FileChooser();
        // filters
        /**
         * Images
         */
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Images",
                "*.jpg",
                "*.jpeg",
                "*.png",
                "*.gif",
                "*.tiff",
                "*.bmp"
        );

        FileChooser.ExtensionFilter docFilter
                = new FileChooser.ExtensionFilter("Documents",
                        "*.doc",
                        "*.docx",
                        "*.xls",
                        "*.xlsx",
                        "*.pdf"
                );

        FileChooser.ExtensionFilter videoFilter
                = new FileChooser.ExtensionFilter("Videos",
                        "*.mp4",
                        "*.avi",
                        "*.flv",
                        "*.wmv",
                        "*.mov",
                        "*.mkv"
                );

        fileChooser.getExtensionFilters().setAll(imageFilter, docFilter, videoFilter);

        File file = fileChooser.showOpenDialog(this.getStage());

        if (file == null) {
            this.showWarningMessage("No File Was Selected");
            return false;
        }

        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.indexOf('.') + 1, fileName.length());

        SimpleDateFormat fileFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        String type = this.cmb_type.getSelectionModel().getSelectedItem().toUpperCase(Locale.ENGLISH);
        String description = this.txt_description.getText().trim();
        String uploadfile
                = this.parentModel.getName()
                + "_"
                + type
                + "_"
                + fileFormat.format(new Date())
                + "."
                + fileExtension;
        uploadfile = uploadfile.replaceAll("\\s+", "_").trim();

        try {
            boolean uploaded = Context.app().uploadDocument(file, uploadfile);
            if (!uploaded) {
                this.showWarningMessage("Failed to upload the file.");
                return false;
            }
        } catch (Exception e) {
            this.showWarningMessage("There was a problem uploading the file.");
            return false;
        }

        // copy
        this.frm_type = type;
        this.frm_description = description;
        this.frm_file = uploadfile;

        // ok
        return true;
    }

    /**
     * Inserts a new record on the database when validation is successful.
     */
    private boolean insertRecord() {
        CompanyProfileDocumentModel student = new CompanyProfileDocumentModel();
        student.setCompanyProfileId(this.parentModel.getId()); // foreign
        student.setType(frm_type);
        student.setDescription(frm_description);
        student.setLocation(frm_file);
        // created
        student.setCreatedBy(this.loggedUser);
        student.setCreatedDate(Context.app().getStandardDateFormat().format(new Date()));
        return student.insert();
    }

    private void clearFields() {
        this.cmb_type.getSelectionModel().selectFirst();
        this.txt_description.setText("");
        this.lbl_file_location.setText("");
        this.lbl_created.setText("");
    }

    /**
     * Create the table including predicate.
     */
    private void createTable() {
        TableColumn<CompanyProfileDocumentModel, String> numberCol = new TableColumn<>("#");
        numberCol.setPrefWidth(50.0);
        numberCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getId().toString()));

        TableColumn<CompanyProfileDocumentModel, String> dateCol = new TableColumn<>("Type");
        dateCol.setPrefWidth(200.0);
        dateCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getType()));

        TableColumn<CompanyProfileDocumentModel, String> nameCol = new TableColumn<>("Description");
        nameCol.setPrefWidth(200.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDescription()));

        TableColumn<CompanyProfileDocumentModel, String> repCol = new TableColumn<>("File");
        repCol.setPrefWidth(200.0);
        repCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getLocation()));

        TableColumn<CompanyProfileDocumentModel, String> addressCol = new TableColumn<>("Upload");
        addressCol.setPrefWidth(150.0);
        addressCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCreatedBy()));

        TableColumn<CompanyProfileDocumentModel, String> concernCol = new TableColumn<>("Date");
        concernCol.setPrefWidth(150.0);
        concernCol.setCellValueFactory(value -> {
            String dateString = "";
            try {
                Date date = Context.app().getStandardDateFormat().parse(value.getValue().getCreatedDate());
                dateString = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(date);
            } catch (ParseException e) {
                // ignore
            }
            return new SimpleStringProperty(dateString);
        });

        this.tbl_information.getColumns().addAll(numberCol, dateCol, nameCol, repCol, addressCol, concernCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<CompanyProfileDocumentModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((CompanyProfileDocumentModel company) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (company.getId().toString().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getType().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getDescription().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getLocation().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getCreatedBy().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getCreatedDate().toLowerCase().contains(filterString)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<CompanyProfileDocumentModel> sortedData = new SortedList<>(filteredResult);

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
        ArrayList<CompanyProfileDocumentModel> inquiries = CompanyProfileDocumentModel.getAllRecords(this.parentModel.getId());
        this.tableData.addAll(inquiries);
    }

}
