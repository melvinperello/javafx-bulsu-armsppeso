package afterschoolcreatives.armsppeso.ui;

import afterschoolcreatives.armsppeso.Context;
import afterschoolcreatives.armsppeso.models.GraduatedStudentModel;
import afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
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

    public GraduateStudentRecords() {
        this.tableData = FXCollections.observableArrayList();
    }

    private final ObservableList<GraduateStudentTableRow> tableData;

    @Override
    protected void setup() {
        this.createTable();
        this.populateTable(); //refresh

        this.btn_main_from_student.setOnMouseClicked(click -> {
            MainMenu mm = new MainMenu();
            this.changeRoot(mm.load());
            click.consume();
        });

        this.btn_add_record.setOnMouseClicked(click -> {

        });
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

    private void populateTable() {
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
                graduationDate = Context.app().getStandardDateFormat().format(student.getGraduationDate());
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
