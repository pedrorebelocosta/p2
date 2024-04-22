package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Employee;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.EmployeeService;
import org.nolhtaced.desktop.UserSession;

import java.time.LocalDate;

public class EmployeeFormController extends FormController<Employee> {
    @FXML
    private ComboBox<UserRoleEnum> roleField;
    @FXML
    private DatePicker hireDateField;
    @FXML
    private DatePicker terminationDateField;
    @FXML
    public UserFormController userFormController;
    private EmployeeService employeeService;

    public EmployeeFormController() {
        this.employeeService = new EmployeeService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        userFormController.hideButtons();
        populateRoles();
        attachFieldValidators();

        if (userFormController.activeField.isSelected()) {
            terminationDateField.setDisable(true);
        }

        userFormController.activeField.selectedProperty().addListener((observable, oldValue, isActive) -> {
            terminationDateField.setDisable(isActive);

            if (isActive) {
                terminationDateField.setValue(null);
            }
        });
    }

    public void onClickSave() throws UserNotFoundException {
        boolean valid = userFormController.validator.validate() & validator.validate();

        if (!valid) return;

        Employee formEmployee = getFormData();

        if (!isEditing) {
            employeeService.create(formEmployee);
        } else {
            formEmployee.setId(data.getId());
            employeeService.update(formEmployee);
        }

        requestFormClose(false);
        successCallback.call(formEmployee);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    private void attachFieldValidators() {
        validator.createCheck()
            .dependsOn("hireDate", hireDateField.valueProperty())
            .dependsOn("terminationDate", terminationDateField.valueProperty())
                    .withMethod(context -> {
                        LocalDate hireDate = context.get("hireDate");
                        LocalDate terminationDate = context.get("terminationDate");

                        if (terminationDate == null) {
                            return;
                        }

                        if (hireDate.isAfter(terminationDate)) {
                            context.error("The hire date must be a value before the termination date");
                        }

                        if (terminationDate.isBefore(hireDate)) {
                            context.error("The termination date must be a value after the hire date");
                        }

                    }).decorates(hireDateField).decorates(terminationDateField);

        validator.createCheck()
            .dependsOn("role", roleField.valueProperty())
            .withMethod(context -> {
                UserRoleEnum role = context.get("role");

                if (role == null) {
                    context.error("A role must be selected");
                }
            })
            .decorates(roleField);
    }

    private void populateRoles() {
        roleField.getItems().add(UserRoleEnum.MANAGER);
        roleField.getItems().add(UserRoleEnum.MECHANIC);
        roleField.getItems().add(UserRoleEnum.SALES_REPRESENTATIVE);
    }

    @Override
    public void onDataReceived(Employee data) {
        userFormController.setInitialData(data);
        roleField.setValue(data.getRole());
        hireDateField.setValue(data.getHireDate());
        terminationDateField.setValue(data.getTerminationDate());
    }

    @Override
    public Employee getFormData() {
        User userData = userFormController.getFormData();

        return new Employee(
                userData.getId(),
                userData.getUsername(),
                userData.getPassword(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getDateOfBirth(),
                userData.getActive(),
                roleField.getValue(),
                hireDateField.getValue(),
                terminationDateField.getValue()
        );
    }
}
