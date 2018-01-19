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
    private TableView<CompanyProfileRow> tbl_information;

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
    private TextField txt_preferred_course;

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

    @FXML
    private JFXButton btn_clear;

    public CompanyProfileRecords() {
        this.tableData = FXCollections.observableArrayList();
        this.loggedUser = "JHON MELVIN PERELLO";
        this.loggedUserType = "ADMINISTRATOR";
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<CompanyProfileRow> tableData;

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
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(300.0);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn prefCourseCol = new TableColumn("Preferred Course");
        prefCourseCol.setPrefWidth(150.0);
        prefCourseCol.setCellValueFactory(new PropertyValueFactory<>("preferredCourse"));

        TableColumn addressCol = new TableColumn("Address");
        addressCol.setPrefWidth(200.0);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn contactPersonCol = new TableColumn("Contact Person");
        contactPersonCol.setPrefWidth(150.0);
        contactPersonCol.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));

        TableColumn contactNumberCol = new TableColumn("Contact Number");
        contactNumberCol.setPrefWidth(150.0);
        contactNumberCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        this.tbl_information.getColumns().addAll(nameCol, prefCourseCol, addressCol, contactPersonCol, contactNumberCol);

        // 01. wrap the observeable list inside the filter list.
        FilteredList<CompanyProfileRow> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((CompanyProfileRow student) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (student.getName().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (student.getPreferredCourse().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (student.getAddress().toLowerCase().contains(filterString)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<CompanyProfileRow> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_information.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_information.setItems(sortedData);

        //        this.tbl_information.setItems(this.tableData);
    }

    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<CompanyProfileModel> companies = CompanyProfileModel.getAllRecords();
        companies.forEach(company -> {
            CompanyProfileRow cpRow = new CompanyProfileRow();
            cpRow.setId(company.getId());
            //
            cpRow.setName(company.getName());
            cpRow.setPreferredCourse(company.getPreferredCourse());
            cpRow.setAddress(company.getAddress());
            cpRow.setContactPerson(company.getContactPerson());
            cpRow.setContactNumber(company.getContactNumber());
            //
            try {
                String dateString = Context.app().getStandardDateFormat().format(company.getCreatedDate());
                cpRow.setCreadtedDate(dateString);
                cpRow.setCreatedBy(company.getCreatedBy());
            } catch (Exception e) {
                //
            }

            try {
                String dateString = Context.app().getStandardDateFormat().format(company.getLastModifiedDate());
                cpRow.setModifiedDate(dateString);
                cpRow.setModifiedBy(company.getLastModifiedBy());
            } catch (Exception e) {
                // ignore
            }
            //
            // add to row
            this.tableData.add(cpRow);
        });
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

    /**
     * Representing the view of company profile model.
     */
    public static class CompanyProfileRow {

        private final SimpleIntegerProperty id;
        //
        private final SimpleStringProperty name;
        private final SimpleStringProperty preferredCourse;
        private final SimpleStringProperty address;
        private final SimpleStringProperty contactPerson;
        private final SimpleStringProperty contactNumber;
        //
        private final SimpleStringProperty createdBy;
        private final SimpleStringProperty creadtedDate;
        private final SimpleStringProperty modifiedBy;
        private final SimpleStringProperty modifiedDate;

        public CompanyProfileRow() {
            this.id = new SimpleIntegerProperty(0);
            //
            this.name = new SimpleStringProperty("");
            this.preferredCourse = new SimpleStringProperty("");
            this.address = new SimpleStringProperty("");
            this.contactPerson = new SimpleStringProperty("");
            this.contactNumber = new SimpleStringProperty("");
            //
            this.createdBy = new SimpleStringProperty("");
            this.creadtedDate = new SimpleStringProperty("");
            this.modifiedBy = new SimpleStringProperty("");
            this.modifiedDate = new SimpleStringProperty("");
        }

        //----------------------------------------------------------------------
        // Getters
        //----------------------------------------------------------------------
        public Integer getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getPreferredCourse() {
            return preferredCourse.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getContactPerson() {
            return contactPerson.get();
        }

        public String getContactNumber() {
            return contactNumber.get();
        }

        public String getCreatedBy() {
            return createdBy.get();
        }

        public String getCreadtedDate() {
            return creadtedDate.get();
        }

        public String getModifiedBy() {
            return modifiedBy.get();
        }

        public String getModifiedDate() {
            return modifiedDate.get();
        }

        //----------------------------------------------------------------------
        // Setters
        //----------------------------------------------------------------------
        public void setId(Integer id) {
            if (id == null) {
                this.id.set(0);
                return;
            }
            this.id.set(id);
        }

        public void setName(String name) {
            if (name == null) {
                this.name.set("");
                return;
            }
            this.name.set(name);
        }

        public void setPreferredCourse(String preferredCourse) {
            if (preferredCourse == null) {
                this.preferredCourse.set("");
                return;
            }
            this.preferredCourse.set(preferredCourse);
        }

        public void setAddress(String address) {
            if (address == null) {
                this.address.set("");
                return;
            }
            this.address.set(address);
        }

        public void setContactPerson(String contactPerson) {
            if (contactPerson == null) {
                this.contactPerson.set("");
                return;
            }
            this.contactPerson.set(contactPerson);
        }

        public void setContactNumber(String contactNumber) {
            if (contactNumber == null) {
                this.contactNumber.set("");
                return;
            }
            this.contactNumber.set(contactNumber);
        }

        public void setCreatedBy(String createdBy) {
            if (createdBy == null) {
                this.createdBy.set("");
                return;
            }
            this.createdBy.set(createdBy);
        }

        public void setModifiedBy(String modifiedBy) {
            if (modifiedBy == null) {
                this.modifiedBy.set("");
                return;
            }
            this.modifiedBy.set(modifiedBy);
        }

        public void setCreadtedDate(String creadtedDate) {
            if (creadtedDate == null) {
                this.creadtedDate.set("");
                return;
            }
            this.creadtedDate.set(creadtedDate);
        }

        public void setModifiedDate(String modifiedDate) {
            if (modifiedDate == null) {
                this.modifiedDate.set("");
                return;
            }
            this.modifiedDate.set(modifiedDate);
        }

    }

}
