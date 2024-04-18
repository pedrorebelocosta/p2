package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.exceptions.SessionNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.services.UserService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.time.LocalDate;

public class CustomerController {
    @FXML
    public TableView<Customer> tableView;
    @FXML
    public TableColumn<Customer, Integer> idCol;
    @FXML
    public TableColumn<Customer, String> usernameCol;
    @FXML
    public TableColumn<Customer, String> firstNameCol;
    @FXML
    public TableColumn<Customer, String> lastNameCol;
    @FXML
    public TableColumn<Customer, LocalDate> dateOfBirthCol;
    @FXML
    public TableColumn<Customer, Boolean> activeCol;
    @FXML
    public Button newCustomerBtn;
    @FXML
    public Button editCustomerBtn;

    private final CustomerService customerService;

    public CustomerController() {
        customerService = new CustomerService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));

        editCustomerBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        populateTable();
    }

    public void onClickAdd() {
        try {
            UIManager.openForm("/forms/customer-form.fxml", unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickEdit() {
        Customer tableCustomer = tableView.getSelectionModel().getSelectedItem();

        try {
            Customer customer = customerService.get(tableCustomer.getId());
            UIManager.openForm("/forms/customer-form.fxml", customer, unused -> this.populateTable());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Void populateTable() {
        tableView.getItems().setAll(customerService.getAll());
        return null;
    }
}
