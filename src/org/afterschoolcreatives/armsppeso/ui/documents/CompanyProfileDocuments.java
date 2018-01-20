/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.ui.documents;

import com.jfoenix.controls.JFXButton;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileDocumentModel;
import org.afterschoolcreatives.armsppeso.models.CompanyProfileModel;
import org.afterschoolcreatives.armsppeso.models.InquiryModel;
import org.afterschoolcreatives.armsppeso.ui.CompanyProfileRecords;
import org.afterschoolcreatives.armsppeso.ui.MainMenu;
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
    private final ObservableList<InquiryModel> tableData;
    
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
    }
    
}
