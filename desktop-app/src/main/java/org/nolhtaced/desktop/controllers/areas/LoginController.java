package org.nolhtaced.desktop.controllers.areas;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.services.UserService;
import org.nolhtaced.core.utilities.NolhtacedRegex;
import org.nolhtaced.desktop.utilities.UIManager;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.enumerators.EAppArea;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;
import net.synedra.validatorfx.Validator;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Button loginButton;

    private UserService userService;
    private Validator validator;

    public LoginController() {
        this.userService = new UserService(UserSession.getSession());
        this.validator = new Validator();
    }

    @FXML
    public void initialize() {
        validator.createCheck()
                .dependsOn("username", username.textProperty())
                .withMethod(context -> {
                    String username = context.get("username");

                    if (!username.matches(NolhtacedRegex.ENTITY_NAME_PATTERN)) {
                        context.error("Please use only alphanumerical characters with no spaces!");
                    }
                })
                .decorates(username);

        validator.createCheck()
                .dependsOn("username", username.textProperty())
                .dependsOn("password", password.textProperty())
                .withMethod(context -> {
                    String username = context.get("username");
                    String password = context.get("password");

                    try {
                        if (userService.verifyPassword(username, password)) {
                            UserSession.getSession().setCurrentUser(userService.getByUsername(username));
                            UIManager.setScreen(EAppArea.APPLICATION);
                        } else {
                            context.error("Your credentials are incorrect, please verify your username and password!");
                        }
                    } catch (UserNotFoundException e) {
                        context.error("This username doesn't exist, verify your username.");
                    } catch (UIManagerNotInitializedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .decorates(username)
                .decorates(password);

        loginButton.disableProperty().bind(Bindings.or(username.textProperty().isEmpty(), password.textProperty().isEmpty()));
    }

    @FXML
    protected void onLoginClick() {
        validator.validate();
    }
}
