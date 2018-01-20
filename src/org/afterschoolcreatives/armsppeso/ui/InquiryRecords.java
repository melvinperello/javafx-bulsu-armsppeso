/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.armsppeso.ui;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.afterschoolcreatives.armsppeso.Context;
import org.afterschoolcreatives.armsppeso.models.InquiryModel;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

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
     * Contains the data of the table.
     */
    private final ObservableList<InquiryModel> tableData;

    private final String loggedUser;
    private final String loggedUserType;

    @Override
    protected void setup() {
        /**
         * Initialize combo box.
         */
        this.cmb_concern.getItems().setAll("Tie Up", "Job Fair", "Posting of Vacancy", "List of Graduates", "Others");

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
