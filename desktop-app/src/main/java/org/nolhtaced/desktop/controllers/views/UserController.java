package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.SessionNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.models.Employee;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.services.EmployeeService;
import org.nolhtaced.core.services.UserService;
import org.nolhtaced.desktop.utilities.UIManager;
import org.nolhtaced.desktop.UserSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class UserController {
    @FXML
    public TableView<User> tableView;
    @FXML
    public TableColumn<User, Integer> idCol;
    @FXML
    public TableColumn<User, String> usernameCol;
    @FXML
    public TableColumn<User, String> firstNameCol;
    @FXML
    public TableColumn<User, String> lastNameCol;
    @FXML
    public TableColumn<User, LocalDate> dateOfBirthCol;
    @FXML
    public TableColumn<User, UserRoleEnum> roleCol;
    @FXML
    public TableColumn<User, Boolean> activeCol;
    @FXML
    public MenuButton addBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;

    private final UserService userService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public UserController() {
        this.userService = new UserService(UserSession.getSession());
        this.employeeService = new EmployeeService(UserSession.getSession());
        this.customerService = new CustomerService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        editBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        deleteBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));

        populateTable();
        populateMenuOptions();
    }

    public void onClickAdd(UserRoleEnum type) {
        if (type == UserRoleEnum.ADMINISTRATOR) {
            try {
                UIManager.<User>openForm("/forms/user-form.fxml", unused -> this.populateTable());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (type == UserRoleEnum.CUSTOMER) {
            try {
                UIManager.openForm("/forms/customer-form.fxml", unused -> this.populateTable());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                UIManager.<Employee>openForm("/forms/employee-form.fxml", unused -> this.populateTable());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onClickEdit() {
        User user = tableView.getSelectionModel().getSelectedItem();

        if (user.getRole() == UserRoleEnum.ADMINISTRATOR) {
            try {
                UIManager.openForm("/forms/user-form.fxml", user, unused -> this.populateTable());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else if (user.getRole() == UserRoleEnum.CUSTOMER) {
            try {
                Customer customer = customerService.get(user.getId());
                UIManager.openForm("/forms/customer-form.fxml", customer, unused -> this.populateTable());
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            try {
                Employee employee = employeeService.get(user.getId());
                UIManager.openForm("/forms/employee-form.fxml", employee, unused -> this.populateTable());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onClickDelete() {
        User user = this.tableView.getSelectionModel().getSelectedItem();

        if (user.getId().equals(UserSession.getSession().getCurrentUser().get().getId())) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setTitle("Unable to delete user!");
            errorAlert.setContentText("You can't delete yourself...");
            errorAlert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(String.format("Deleting user '%s'", user.getUsername()));
        alert.setContentText("Are you want to delete this user?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            try {
                if (user.getRole() == UserRoleEnum.ADMINISTRATOR) {
                    userService.delete(user.getId());
                } else if (user.getRole() == UserRoleEnum.CUSTOMER) {
                    customerService.delete(user.getId());
                } else {
                    employeeService.delete(user.getId());
                }

                // refresh table after deletion
                populateTable();
            } catch (UserNotFoundException | ObjectStillReferencedException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Unable to delete user!");
                errorAlert.setContentText("It was not possible to delete this user!");
                errorAlert.show();
            }
        }
    }

    private Void populateTable() {
        try {
            tableView.getItems().setAll(userService.getAll());
        } catch (SessionNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void populateMenuOptions() {
        UserRoleEnum role = UserSession.getSession().getCurrentUser().get().getRole();

        if (role == UserRoleEnum.ADMINISTRATOR) {
            MenuItem administratorItem = new MenuItem("Administrator");
            administratorItem.setOnAction((e) -> onClickAdd(UserRoleEnum.ADMINISTRATOR));
            addBtn.getItems().add(administratorItem);
        }

        if (role == UserRoleEnum.ADMINISTRATOR || role == UserRoleEnum.MANAGER) {
            MenuItem employeeItem = new MenuItem("Employee");
            employeeItem.setOnAction((e) -> onClickAdd(UserRoleEnum.MANAGER));
            addBtn.getItems().add(employeeItem);
        }

        MenuItem customerItem = new MenuItem("Customer");
        customerItem.setOnAction((e) -> onClickAdd(UserRoleEnum.CUSTOMER));
        addBtn.getItems().add(customerItem);
    }
}
