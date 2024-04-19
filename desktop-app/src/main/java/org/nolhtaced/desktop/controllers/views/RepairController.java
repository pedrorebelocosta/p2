package org.nolhtaced.desktop.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.nolhtaced.core.enumerators.RepairStateEnum;
import org.nolhtaced.core.models.*;
import org.nolhtaced.core.services.BicycleService;
import org.nolhtaced.core.services.EmployeeService;
import org.nolhtaced.core.services.RepairService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.enumerators.EAppForm;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RepairController {
    @FXML
    public TableView<Repair> tableView;
    @FXML
    public TableColumn<Repair, Integer> idCol;
    @FXML
    public TableColumn<Repair, Integer> bicycleNameCol;
    @FXML
    public TableColumn<Repair, RepairStateEnum> stateCol;
    @FXML
    public TableColumn<Repair, Integer> employeeCol;

    @FXML
    public Button addBtn;
    @FXML
    public Button editBtn;

    private final RepairService repairService;
    private final EmployeeService employeeService;
    private final BicycleService bicycleService;

    private final List<Employee> employeeList;
    private final List<Bicycle> bicycleList;

    public RepairController() {
        repairService = new RepairService(UserSession.getSession());
        bicycleService = new BicycleService(UserSession.getSession());
        employeeService = new EmployeeService(UserSession.getSession());
        employeeList = employeeService.getAll();
        bicycleList = bicycleService.getAll();
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bicycleNameCol.setCellValueFactory(new PropertyValueFactory<>("bicycleId"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        bicycleNameCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Repair, Integer> call(TableColumn<Repair, Integer> repairIntegerTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer customerId, boolean empty) {
                        super.updateItem(customerId, empty);

                        if (customerId == null || empty) {
                            setText("");
                            return;
                        }

                        Bicycle bicycle = bicycleList.stream().filter(c -> Objects.equals(c.getId(), customerId)).findFirst().get();
                        setText(bicycle.getName());
                    }
                };
            }
        });

        employeeCol.setCellValueFactory(new PropertyValueFactory<>("assignedEmployeeId"));

        employeeCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Repair, Integer> call(TableColumn<Repair, Integer> repairIntegerTableColumn) {
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

        editBtn.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        populateTable();
    }

    private Void populateTable() {
        tableView.getItems().setAll(repairService.getAll());
        return null;
    }

    public void onClickAdd() throws UIManagerNotInitializedException {
        try {
            UIManager.openForm(EAppForm.REPAIR_FORM, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickEdit() throws UIManagerNotInitializedException {
        Repair r = tableView.getSelectionModel().getSelectedItem();
        try {
            UIManager.openForm(EAppForm.REPAIR_FORM, r, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
