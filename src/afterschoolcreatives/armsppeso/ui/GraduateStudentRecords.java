package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import afterschoolcreatives.polaris.java.util.StringTool;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import com.jfoenix.controls.JFXButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class GraduateStudentRecords extends PolarisFxController {

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

    /**
     * Construct.
     */
    public GraduateStudentRecords() {
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<GraduateStudentTableRow> tableData;

    @Override
    protected void setup() {
        this.createTable();
        this.populateTable(); //refresh

        // back to main menu
        this.btn_main_from_student.setOnMouseClicked(click -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            click.consume();
        });

        this.btn_add_record.setOnMouseClicked(click -> {
            this.validateFields();
        });
    }

    private boolean validateFields() {
        // last
        String lastName = this.getTextString(this.txt_last_name);
        if ((!StringTool.isAlpha(lastName, '-', ' ')) || (!StringTool.startsWith(lastName, ' ', '-'))) {
            this.showWarningMessage("Invalid Last Name");
            return false;
        }
        // first
        String firstName = this.getTextString(this.txt_first_name);
        if ((!StringTool.isAlpha(firstName, '-', ' ')) || (!StringTool.startsWith(firstName, ' ', '-'))) {
            this.showWarningMessage("Invalid First Name");
            return false;
        }
        // middle
        String middleName = this.getTextString(this.txt_middle_name);
        if ((!StringTool.isAlpha(middleName, '-', ' ')) || (!StringTool.startsWith(middleName, ' ', '-'))) {
            this.showWarningMessage("Invalid Middle Name");
            return false;
        }

        // ok
        return true;
    }

    private void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle("Graduated Student")
                .setHeaderText("Invalid Field")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    private void insertNew() {
        GraduatedStudentModel student = new GraduatedStudentModel();
        student.setLastName(this.getTextString(this.txt_last_name));
        student.setFirstName(this.getTextString(this.txt_first_name));
        student.setMiddleName(this.getTextString(this.txt_middle_name));
        //
        student.setCourse(this.getTextString(this.txt_course));
        try {
            SimpleDateFormat insertionFormat = new SimpleDateFormat("MM/dd/yyyy");
            student.setGraduationDate(insertionFormat.parse(this.getTextString(this.txt_graduation_date)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        student.setAge(Integer.valueOf(this.getTextString(this.txt_age)));
        student.setEmail(this.getTextString(this.txt_email));
        student.setContact(this.getTextString(this.txt_contact));
        student.setAddress(this.getTextString(this.txt_address));
        // created
        // modified
        student.insert();
        // refresh table
        this.populateTable();
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

        this.tbl_information.setItems(this.tableData);
        this.tbl_information.getColumns().addAll(fullNameCol, courseCol, graduationDate, ageCol, addressCol);
    }

    /**
     * Refreshes the contents of the table.
     */
    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<GraduatedStudentModel> students = GraduatedStudentModel.getAllRecords();
        students.forEach(student -> {
            GraduateStudentTableRow gsRow = new GraduateStudentTableRow();
            gsRow.setLastName(student.getLastName());
            gsRow.setFirstName(student.getFirstName());
            gsRow.setMiddleName(student.getMiddleName());
            //
            gsRow.setCourse(student.getCourse());
            String graduationDate = "";
            if (student.getGraduationDate() != null) {
                SimpleDateFormat gradFormat = new SimpleDateFormat("MM-dd-yyyy");
                graduationDate = gradFormat.format(student.getGraduationDate());
            }
            gsRow.setGraduationDate(graduationDate);
            gsRow.setAddress(student.getAddress());
            String age = "";
            if (student.getAge() != null) {
                age = student.getAge().toString();
            }
            gsRow.setAge(age);
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

        private final SimpleStringProperty course;
        private final SimpleStringProperty graduationDate;
        private final SimpleStringProperty age;
        private final SimpleStringProperty address;
        // extended properties
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty middleName;

        /**
         * Default constructor.
         */
        public GraduateStudentTableRow() {
            this.course = new SimpleStringProperty("");
            this.graduationDate = new SimpleStringProperty("");
            this.age = new SimpleStringProperty("");
            this.address = new SimpleStringProperty("");
            //
            this.lastName = new SimpleStringProperty("");
            this.firstName = new SimpleStringProperty("");
            this.middleName = new SimpleStringProperty("");
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

        //--------------------------------------------------------------------------
        // Setter
        //--------------------------------------------------------------------------
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

    }

}
