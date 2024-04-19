package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.nolhtaced.core.enumerators.TransactionStateEnum;
import org.nolhtaced.core.exceptions.TransactionNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.models.Employee;
import org.nolhtaced.core.models.Transaction;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.services.EmployeeService;
import org.nolhtaced.core.services.TransactionService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.enumerators.EAppForm;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SalesController {
    @FXML
    public TableView<Transaction> tableView;
    @FXML
    public TableColumn<Transaction, Integer> idCol;
    @FXML
    public TableColumn<Transaction, Integer> customerCol;
    @FXML
    public TableColumn<Transaction, Integer> employeeCol;
    @FXML
    public TableColumn<Transaction, TransactionStateEnum> stateCol;
    @FXML
    public TableColumn<Transaction, Float> totalCol;

    @FXML
    public Button addBtn;
    @FXML
    public Button transitionStateBtn;
    @FXML
    public Button viewDetailBtn;

    private TransactionService transactionService;
    private CustomerService customerService;
    private EmployeeService employeeService;

    private List<Customer> customerList;
    private List<Employee> employeeList;

    public SalesController() {
        transactionService = new TransactionService(UserSession.getSession());
        customerService = new CustomerService(UserSession.getSession());
        employeeService = new EmployeeService(UserSession.getSession());
        customerList = customerService.getAll();
        employeeList = employeeService.getAll();
    }

    @FXML
    public void initialize() {
        transitionStateBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        viewDetailBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, Integer> call(TableColumn<Transaction, Integer> transactionStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer customerId, boolean empty) {
                        super.updateItem(customerId, empty);

                        if (customerId == null || empty) {
                            setText("");
                            return;
                        }

                        Customer customer = customerList.stream().filter(c -> Objects.equals(c.getId(), customerId)).findFirst().get();
                        setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
                    }
                };
            }
        });

        employeeCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        employeeCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, Integer> call(TableColumn<Transaction, Integer> transactionStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer employeeId, boolean empty) {
                        super.updateItem(employeeId, empty);

                        if (employeeId == null || empty) {
                            setText("");
                            return;
                        }

                        Employee employee = employeeList.stream().filter(c -> Objects.equals(c.getId(), employeeId)).findFirst().get();
                        setText(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
                    }
                };
            }
        });

        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        populateTable();
    }

    private Void populateTable() {
        tableView.getItems().setAll(transactionService.getAll());
        return null;
    }

    public void onClickAdd() throws UIManagerNotInitializedException {
        try {
            UIManager.openForm(EAppForm.SALES_FORM, unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickStateTransition() throws UIManagerNotInitializedException {
        Transaction t = tableView.getSelectionModel().getSelectedItem();
        try {
            UIManager.openForm(EAppForm.TRANSACTION_STATE_FORM, t.getState(), newState -> onOrderStateTransition(t, newState));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Void onOrderStateTransition(Transaction t, TransactionStateEnum newState) {
        try {
            t.setState(newState);
            transactionService.update(t);
            this.populateTable();
        } catch (TransactionNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void onClickViewDetails() throws UIManagerNotInitializedException {
        Transaction t = tableView.getSelectionModel().getSelectedItem();
        try {
            UIManager.openForm(EAppForm.TRANSACTION_DETAIL_FORM, t, unused -> null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
