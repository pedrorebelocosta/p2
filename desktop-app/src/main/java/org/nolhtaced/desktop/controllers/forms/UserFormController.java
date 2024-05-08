package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.UserService;
import org.nolhtaced.core.utilities.NolhtacedRegex;
import org.nolhtaced.desktop.UserSession;

import java.time.LocalDate;

public class UserFormController extends FormController<User> {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField firstNameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField lastNameField;
    @FXML
    public CheckBox activeField;
    @FXML
    public DatePicker dateOfBirthField;
    @FXML
    public Button cancelBtn;
    @FXML
    public Button saveBtn;

    private final UserService userService;
    public UserRoleEnum role;

    public UserFormController() {
        userService = new UserService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        activeField.setSelected(true);
        role = UserRoleEnum.ADMINISTRATOR;
        attachFieldValidators();
    }

    @Override
    protected void onDataReceived(User data) {
        usernameField.setText(data.getUsername());
        usernameField.setDisable(true);
        // TODO Add another form/modal to change password?
        passwordField.setText(data.getPassword());
        passwordField.setDisable(true);
        firstNameField.setText(data.getFirstName());
        lastNameField.setText(data.getLastName());
        dateOfBirthField.setValue(data.getDateOfBirth());
        activeField.setSelected(data.getActive());
        role = data.getRole();
    }

    public void onClickSave() throws UserNotFoundException  {
        if (!validator.validate()) return;

        User formUser = getFormData();

        if (!isEditing) {
            userService.create(formUser);
        } else {
            formUser.setId(data.getId());
            userService.update(formUser);
        }

        requestFormClose(false);
        successCallback.call(formUser);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    @Override
    protected User getFormData() {
        return new User(
                usernameField.getText(),
                passwordField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                dateOfBirthField.getValue(),
                activeField.isSelected(),
                role
        );
    }

    public void hideButtons() {
        cancelBtn.setVisible(false);
        saveBtn.setVisible(false);
    }

    private void attachFieldValidators() {
        validator.createCheck()
            .dependsOn("username", usernameField.textProperty())
            .withMethod(context -> {
                String username = context.get("username");

                if (username.isEmpty()) {
                    context.error("A username must be supplied");
                }
            }).decorates(usernameField);

        validator.createCheck()
            .dependsOn("password", passwordField.textProperty())
            .withMethod(context -> {
                String rawPassword = context.get("password");

                if (!isEditing && !rawPassword.matches(NolhtacedRegex.PASSWORD_REQUIREMENTS_PATTERN)) {
                    context.error("Your password must contain a minimum of 8 characters, one letter and one special character!");
                }
            })
            .decorates(passwordField);

        validator.createCheck()
            .dependsOn("firstName", firstNameField.textProperty())
            .withMethod(context -> {
                String firstName = context.get("firstName");
                if (firstName.isEmpty()) {
                    context.error("A first name must be supplied");
                }
            })
            .decorates(firstNameField);

        validator.createCheck()
            .dependsOn("lastName", lastNameField.textProperty())
            .withMethod(context -> {
                String lastName = context.get("lastName");
                if (lastName.isEmpty()) {
                    context.error("A last name must be supplied");
                }
            })
            .decorates(lastNameField);

        validator.createCheck()
            .dependsOn("dateOfBirth", dateOfBirthField.valueProperty())
            .withMethod(context -> {
                LocalDate dateOfBirth = context.get("dateOfBirth");
                if (dateOfBirth == null) {
                    context.error("A date of birth must be supplied");
                }
            })
            .decorates(dateOfBirthField);

        validator.createCheck()
            .dependsOn("username", usernameField.textProperty())
            .withMethod(context -> {
                String username = context.get("username");

                if (!username.matches(NolhtacedRegex.ENTITY_NAME_PATTERN)) {
                    context.error("Please use only alphanumerical characters with no spaces!");
                }
            })
            .decorates(usernameField);
    }
}
