package org.nolhtaced.desktop.controllers.forms.users;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import net.synedra.validatorfx.Validator;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.UserService;
import org.nolhtaced.core.utilities.NolhtacedRegex;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.controllers.forms.BaseFormController;

import java.time.LocalDate;

public class UserFormController extends BaseFormController<User> {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField lastNameField;
    @FXML
    private CheckBox activeField;
    @FXML
    private DatePicker dateOfBirthField;
    private UserRoleEnum role;
    // TODO I wonder if I can move this up to base
    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    private UserService userService;

    public Validator validator;

    public UserFormController() {
        userService = new UserService(UserSession.getSession());
        this.validator = new Validator();
    }

    @FXML
    public void initialize() {
        if (!isEditing) {
            activeField.setSelected(true);
        }

        attachFieldValidators();
        // add field validations
        // DISABLE username field on edit
        // DISABLE PASSWORD EDIT IF NOT ADMIN
    }

    @Override
    protected void onDataReceived(User data) {
        usernameField.setText(data.getUsername());
        usernameField.setDisable(true);
        // Passwords just simply can't be edited here
        // TODO Add another form/modal to change password?
        passwordField.setText(data.getPassword());
        passwordField.setDisable(true);
        firstNameField.setText(data.getFirstName());
        lastNameField.setText(data.getLastName());
        dateOfBirthField.setValue(data.getDateOfBirth());
        activeField.setSelected(data.getActive());
        // TODO role logic (once a user is created can it have it's role changed?)
        role = data.getRole();
        // THIS METHOD WILL ONLY BE CALLED WHEN EDITING A USER
    }

    @Override
    public EventHandler<WindowEvent> onCloseRequest() {
        return windowEvent -> {
            System.out.println("Form cancel request received.");
        };
    }

    public void onClickSave() {
        if (!validator.validate()) return;

        User formUser = getFormData();

        if (!isEditing) {
            userService.create(formUser);
        } else {
            formUser.setId(data.getId());
            // HACK TO OVERCOME A DESIGN FLAW ON SERVICE INTERFACES
            formUser.setPassword(null);
            updateUser(formUser);
        }

        closeForm();
    }

    private void updateUser(User formUser) {
        if (!data.equals(formUser)) {
            System.out.println("the data is equal son.");
        }
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

    public void onClickCancel() {
        // TODO add an alert or something here?
        closeForm();
    }

    // TODO check if it makes sense to move this somewhere else
    public void hideButtons() {
        cancelBtn.setVisible(false);
        saveBtn.setVisible(false);
    }

    private void attachFieldValidators() {
        // TODO check how to handle password (only on create)
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(context -> {
                    String username = context.get("username");

                    if (username.isEmpty()) {
                        context.error("A username must be supplied");
                    }
                }).decorates(usernameField);

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

//        validator.createCheck()
//                .dependsOn("role", roleField.valueProperty())
//                .withMethod(context -> {
//                    UserRoleEnum role = context.get("role");
//                    if (role == null) {
//                        context.error("A role must be selected");
//                    }
//                })
//                .decorates(roleField);

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
//    @Override
//    public EventHandler<WindowEvent> onCloseRequest(Stage stage) {
//        return windowEvent -> {
//            System.out.println("Im about to end this man's whole career");
//            windowEvent.consume();
//        };
//    }
}
