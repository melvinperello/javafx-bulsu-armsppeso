package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import afterschoolcreatives.polaris.java.util.StringTools;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import com.jfoenix.controls.JFXButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jhon Melvin
 */
public class GraduateStudentRecords extends PolarisFxController {

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_main_from_student;

    @FXML
    private TableView<GraduateStudentTableRow> tbl_information;

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

    @FXML
    private JFXButton btn_clear;

    /**
     * Construct.
     */
    public GraduateStudentRecords() {
        this.tableData = FXCollections.observableArrayList();
        this.loggedUser = Context.app().getAccountName();
        this.loggedUserType = Context.app().getAccountType();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<GraduateStudentTableRow> tableData;

    private final String loggedUser;
    private final String loggedUserType;

    /**
     * Prepare the form.
     */
    @Override
    protected void setup() {
        this.createTable();
        this.populateTable(); //refresh

        this.lbl_created.setText("");
        this.lbl_updated.setText("");

        // back to main menu
        this.btn_main_from_student.setOnMouseClicked(click -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            click.consume();
        });

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

        this.btn_delete_record.setOnMouseClicked(click -> {
            GraduateStudentTableRow gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
            if (gsRow == null) {
                this.showWarningMessage("Please select an entry to delete");
                return;
            }
            if (!this.showConfirmation("Are you sure you want to delete this entry ?")) {
                return; // cancel
            }
            boolean res = GraduatedStudentModel.delete(gsRow.getId());
            if (res) {
                this.showInformationMessage("Entry Was Delete");
                this.clearFields();
                this.populateTable();
            } else {
                this.showWarningMessage("Failed to Delete Record.");
            }
            click.consume();
        });

        this.btn_update_record.setOnMouseClicked(click -> {
            GraduateStudentTableRow gsRow = this.tbl_information.getSelectionModel().getSelectedItem();
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

        this.btn_clear.setOnMouseClicked(click -> {
            this.clearFields();
            click.consume();
        });

        this.tbl_information.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends GraduateStudentTableRow> observable, GraduateStudentTableRow oldValue, GraduateStudentTableRow newValue) -> {
            GraduateStudentTableRow selected = newValue;
            if (selected == null) {
                this.clearFields();
                return;
            }
            // selection
            this.txt_last_name.setText(selected.getLastName());
            this.txt_first_name.setText(selected.getFirstName());
            this.txt_middle_name.setText(selected.getMiddleName());
            this.txt_course.setText(selected.getCourse());
            this.txt_age.setText(selected.getAge());
            this.txt_address.setText(selected.getAddress());
            this.txt_graduation_date.setText(selected.getGraduationDate());
            // contact
            this.txt_email.setText(selected.getEmail());
            this.txt_contact.setText(selected.getContact());
            //
            this.lbl_created.setText(selected.getCreadtedDate() + " " + selected.getCreatedBy());
            this.lbl_updated.setText(selected.getModifiedDate() + " " + selected.getModifiedBy());
        });
    }

    /**
     * Display a warning message with this stage as owner.
     *
     * @param message
     */
    private void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle("Graduated Student")
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
                .setTitle("Graduated Student")
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
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("Graduated Student")
                .setHeaderText("Confirmation")
                .setContentText(message)
                .setOwner(this.getStage())
                .setButtons(yesButton, cancelButton)
                .showAndWait();
        return res.get().getButtonData().equals(ButtonData.YES);
    }

    //--------------------------------------------------------------------------
    // Field set that contains validated values
    //--------------------------------------------------------------------------
    private String frm_lastname;
    private String frm_firstname;
    private String frm_middlename;
    private String frm_course;
    private Date frm_graduation_date;
    private Integer frm_age;
    private String frm_email;
    private String frm_contact;
    private String frm_address;

    /**
     * Validates the fields and sets the value to global field set values.
     *
     * @return
     */
    private boolean validateFields() {
        // last
        String lastName = this.getTextString(this.txt_last_name);
        if ((!StringTools.isAlpha(lastName, '-', ' ')) || (StringTools.startsWith(lastName, ' ', '-'))) {
            this.showWarningMessage("Invalid Last Name.");
            return false;
        }
        // first
        String firstName = this.getTextString(this.txt_first_name);
        if ((!StringTools.isAlpha(firstName, '-', ' ')) || (StringTools.startsWith(firstName, ' ', '-'))) {
            this.showWarningMessage("Invalid First Name.");
            return false;
        }
        // middle (OPTIONAL)
        String middleName = this.getTextString(this.txt_middle_name);
        if (!middleName.isEmpty()) {
            if ((!StringTools.isAlpha(middleName, '-', ' ')) || (StringTools.startsWith(middleName, ' ', '-'))) {
                this.showWarningMessage("Invalid Middle Name.");
                return false;
            }
        }

        String course = this.getTextString(this.txt_course);
        if (course.isEmpty()) {
            this.showWarningMessage("Please Enter a Course.");
            return false;
        }

        String graduation_date = this.getTextString(this.txt_graduation_date);
        SimpleDateFormat insertionFormat = new SimpleDateFormat("MM/dd/yyyy");
        insertionFormat.setLenient(false); // para hndi nagooverflow yung date
        Date gradDate;
        try {
            gradDate = insertionFormat.parse(graduation_date);
        } catch (ParseException e) {
            this.showWarningMessage("Invalid Graduation Date.");
            return false;
        }

        // Optional
        String age = this.getTextString(this.txt_age);
        Integer ageInt = null;
        if (!age.isEmpty()) {
            try {
                ageInt = Integer.valueOf(age);
                if (ageInt <= 0) {
                    throw new NumberFormatException("Invalid Age.");
                }
            } catch (NumberFormatException e) {
                this.showWarningMessage("Invalid Age.");
                return false;
            }
        }

        // Optional
        String email = this.txt_email.getText().trim();
        if (!email.isEmpty()) {
            if (!StringTools.isEmail(email)) {
                this.showWarningMessage("Invalid E-Mail.");
                return false;
            }
        }

        // Optional
        String contact = this.getTextString(this.txt_contact);
//        if (contact.isEmpty()) {
//            this.showWarningMessage("Please Enter a Contact Number.");
//            return false;
//        }

        // Optional
        String address = this.getTextString(this.txt_address);
//        if (address.isEmpty()) {
//            this.showWarningMessage("Please Enter an Address.");
//            return false;
//        }

        //----------------------------------------------------------------------
        this.frm_lastname = lastName;
        this.frm_firstname = firstName;
        this.frm_middlename = middleName;
        this.frm_course = course;
        this.frm_graduation_date = gradDate;
        this.frm_age = ageInt;
        this.frm_email = email;
        this.frm_contact = contact;
        this.frm_address = address;
        // ok
        return true;
    }

    /**
     * clears the field related to the form.
     */
    private void clearFields() {
        this.txt_last_name.setText("");
        this.txt_middle_name.setText("");
        this.txt_first_name.setText("");
        //
        this.txt_course.setText("");
        this.txt_graduation_date.setText("");
        this.txt_age.setText("");
        this.txt_email.setText("");
        this.txt_contact.setText("");
        this.txt_address.setText("");
        //
        this.lbl_created.setText("");
        this.lbl_updated.setText("");
    }

    /**
     * Inserts a new record on the database when validation is successful.
     */
    private boolean insertRecord() {
        GraduatedStudentModel student = new GraduatedStudentModel();
        student.setLastName(this.frm_lastname);
        student.setFirstName(this.frm_firstname);
        student.setMiddleName(this.frm_middlename);
        //
        student.setCourse(this.frm_course);
        student.setGraduationDate(this.frm_graduation_date);
        student.setAge(this.frm_age);
        student.setEmail(this.frm_email);
        student.setContact(this.frm_contact);
        student.setAddress(this.frm_address);
        // created
        student.setCreatedBy(this.loggedUser);
        student.setCreatedDate(new Date());
        // modified
        student.setLastModifiedBy(this.loggedUser);
        student.setLastModifiedDate(new Date());
        return student.insert();
    }

    private boolean updateRecord(GraduateStudentTableRow gsRow) {
        GraduatedStudentModel student = new GraduatedStudentModel();
        student.setId(gsRow.getId());
        student.setLastName(this.frm_lastname);
        student.setFirstName(this.frm_firstname);
        student.setMiddleName(this.frm_middlename);
        //
        student.setCourse(this.frm_course);
        student.setGraduationDate(this.frm_graduation_date);
        student.setAge(this.frm_age);
        student.setEmail(this.frm_email);
        student.setContact(this.frm_contact);
        student.setAddress(this.frm_address);
        // created
        try {
            student.setCreatedDate(Context.app().getStandardDateFormat().parse(gsRow.getCreadtedDate()));
            student.setCreatedBy(gsRow.getCreatedBy());
        } catch (Exception e) {
            // ignore
        }

        // modified
        student.setLastModifiedBy(this.loggedUser);
        student.setLastModifiedDate(new Date());
        return student.update();
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
     * Create the table including predicate.
     */
    private void createTable() {
        TableColumn fullNameCol = new TableColumn("Full Name");
        fullNameCol.setPrefWidth(250.0);
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn courseCol = new TableColumn("Course");
        courseCol.setPrefWidth(150.0);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn graduationDate = new TableColumn("Graduated");
        graduationDate.setPrefWidth(150.0);
        graduationDate.setCellValueFactory(new PropertyValueFactory<>("graduationDate"));

        TableColumn ageCol = new TableColumn("Age");
        ageCol.setPrefWidth(100.0);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn addressCol = new TableColumn("Address");
        addressCol.setPrefWidth(300.0);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        this.tbl_information.getColumns().addAll(fullNameCol, courseCol, graduationDate, ageCol, addressCol);

        // 01. wrap the observeable list inside the filter list.
        FilteredList<GraduateStudentTableRow> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((GraduateStudentTableRow student) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (student.getFullName().toLowerCase().contains(filterString)) {
                    return true;
                }
                // if exact age
                if (student.getAge().equals(filterString)) {
                    return true;
                }
                // if exact course
                if (student.getCourse().toLowerCase().equals(filterString)) {
                    return true;
                }
                // if contains graduate
                if (student.getGraduationDate().contains(filterString)) {
                    return true;
                }
                // if contains graduate
                if (student.getAddress().toLowerCase().contains(filterString)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<GraduateStudentTableRow> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_information.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_information.setItems(sortedData);

//        this.tbl_information.setItems(this.tableData);
    }

    /**
     * Refreshes the contents of the table.
     */
    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<GraduatedStudentModel> students = GraduatedStudentModel.getAllRecords();
        students.forEach(student -> {
            GraduateStudentTableRow gsRow = new GraduateStudentTableRow();
            gsRow.setId(student.getId());
            gsRow.setLastName(student.getLastName());
            gsRow.setFirstName(student.getFirstName());
            gsRow.setMiddleName(student.getMiddleName());
            //
            gsRow.setCourse(student.getCourse());
            String graduationDate = "";
            if (student.getGraduationDate() != null) {
                SimpleDateFormat gradFormat = new SimpleDateFormat("MM/dd/yyyy");
                graduationDate = gradFormat.format(student.getGraduationDate());
            }
            gsRow.setGraduationDate(graduationDate);
            gsRow.setAddress(student.getAddress());
            String age = "";
            if (student.getAge() != null) {
                age = student.getAge().toString();
            }
            gsRow.setAge(age);
            //
            gsRow.setEmail(student.getEmail());
            gsRow.setContact(student.getContact());

            try {
                String dateString = Context.app().getStandardDateFormat().format(student.getCreatedDate());
                gsRow.setCreadtedDate(dateString);
                gsRow.setCreatedBy(student.getCreatedBy());
            } catch (Exception e) {
                //
            }

            try {
                String dateString = Context.app().getStandardDateFormat().format(student.getLastModifiedDate());
                gsRow.setModifiedDate(dateString);
                gsRow.setModifiedBy(student.getLastModifiedBy());
            } catch (Exception e) {
                // ignore
            }
            //
            // add to row
            this.tableData.add(gsRow);
        });
    }

    //--------------------------------------------------------------------------
    // Table Data Row.
    //--------------------------------------------------------------------------
    /**
     * Object Class Representing the Data of the Table in displaying the
     * Graduated Students.
     */
    public static class GraduateStudentTableRow {

        private final SimpleIntegerProperty id;

        private final SimpleStringProperty course;
        private final SimpleStringProperty graduationDate;
        private final SimpleStringProperty age;
        private final SimpleStringProperty address;
        // extended properties
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty middleName;
        private final SimpleStringProperty contact;
        private final SimpleStringProperty email;
        private final SimpleStringProperty createdBy;
        private final SimpleStringProperty creadtedDate;
        private final SimpleStringProperty modifiedBy;
        private final SimpleStringProperty modifiedDate;

        /**
         * Default constructor.
         */
        public GraduateStudentTableRow() {
            this.id = new SimpleIntegerProperty(0);

            this.course = new SimpleStringProperty("");
            this.graduationDate = new SimpleStringProperty("");
            this.age = new SimpleStringProperty("");
            this.address = new SimpleStringProperty("");
            //
            this.lastName = new SimpleStringProperty("");
            this.firstName = new SimpleStringProperty("");
            this.middleName = new SimpleStringProperty("");
            //
            this.contact = new SimpleStringProperty("");
            this.email = new SimpleStringProperty("");
            this.createdBy = new SimpleStringProperty("");
            this.modifiedBy = new SimpleStringProperty("");
            this.creadtedDate = new SimpleStringProperty("");
            this.modifiedDate = new SimpleStringProperty("");
        }

        //--------------------------------------------------------------------------
        // Getter
        //--------------------------------------------------------------------------
        /**
         * Get the full name of this object.
         *
         * @return
         */
        public String getFullName() {
            return this.lastName.get() + ", " + this.firstName.get() + " " + this.middleName.get();
        }

        public String getCourse() {
            return course.get();
        }

        public String getGraduationDate() {
            return graduationDate.get();
        }

        public String getAge() {
            return age.get();
        }

        public String getAddress() {
            return address.get();
        }

        public Integer getId() {
            return id.get();
        }

        public String getLastName() {
            return lastName.get();
        }

        public String getFirstName() {
            return firstName.get();
        }

        public String getMiddleName() {
            return middleName.get();
        }
        //

        public String getContact() {
            return contact.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getCreatedBy() {
            return createdBy.get();
        }

        public String getModifiedBy() {
            return modifiedBy.get();
        }

        public String getCreadtedDate() {
            return creadtedDate.get();
        }

        public String getModifiedDate() {
            return modifiedDate.get();
        }

        //--------------------------------------------------------------------------
        // Setter
        //--------------------------------------------------------------------------
        public void setId(Integer id) {
            if (id == null) {
                this.id.set(0);
                return;
            }
            this.id.set(id);
        }

        public void setCourse(String course) {
            if (course == null) {
                this.address.set("");
                return;
            }
            this.course.set(course);
        }

        public void setGraduationDate(String graduationDate) {
            if (graduationDate == null) {
                this.graduationDate.set("");
                return;
            }
            this.graduationDate.set(graduationDate);
        }

        public void setAge(String age) {
            if (age == null) {
                this.age.set("");
                return;
            }
            this.age.set(age);
        }

        public void setAddress(String address) {
            if (address == null) {
                this.address.set("");
                return;
            }
            this.address.set(address);
        }

        public void setLastName(String lastName) {
            if (lastName == null) {
                this.lastName.set("");
                return;
            }
            this.lastName.set(lastName);
        }

        public void setFirstName(String firstName) {
            if (firstName == null) {
                this.firstName.set("");
                return;
            }
            this.firstName.set(firstName);
        }

        public void setMiddleName(String middleName) {
            if (middleName == null) {
                this.middleName.set("");
                return;
            }
            this.middleName.set(middleName);
        }

        //
        public void setContact(String contact) {
            if (contact == null) {
                this.contact.set("");
                return;
            }
            this.contact.set(contact);
        }

        public void setEmail(String email) {
            if (email == null) {
                this.email.set("");
                return;
            }
            this.email.set(email);
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
