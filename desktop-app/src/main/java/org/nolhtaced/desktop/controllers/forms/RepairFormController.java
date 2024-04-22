package org.nolhtaced.desktop.controllers.forms;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.enumerators.RepairStateEnum;
import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.exceptions.BicycleNotFoundException;
import org.nolhtaced.core.exceptions.EmployeeNotFoundException;
import org.nolhtaced.core.exceptions.RepairNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.*;
import org.nolhtaced.core.services.*;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.helpers.ComboBoxHelper;

import java.util.ArrayList;
import java.util.List;

public class RepairFormController extends FormController<Repair> {
    @FXML
    public TableView<IRepairItem> repairItemsTable;
    @FXML
    public TableColumn<ITransactionItem, String> itemNameCol;
    @FXML
    public TableColumn<IRepairItem, String> itemTitleCol;
    @FXML
    public TableColumn<ITransactionItem, Float> quantityCol;
    @FXML
    public TableColumn<ITransactionItem, Void> deleteRowCol;
    @FXML
    public TableColumn<ITransactionItem, SellableTypeEnum> typeCol;
    @FXML
    public ComboBox<Customer> customerBox;
    @FXML
    public ComboBox<Bicycle> bicycleBox;
    @FXML
    public ComboBox<Employee> assignedEmployeeBox;
    @FXML
    public ComboBox<IRepairItem> productsAndServicesBox;
    @FXML
    public TextField quantityField;
    @FXML
    public Button addBtn;
    @FXML
    public TextArea notesArea;
    @FXML
    public ComboBox<RepairStateEnum> statesBox;

    private final CustomerService customerService;
    private final BicycleService bicycleService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final ServiceService serviceService;
    private final RepairService repairService;
    private final List<IRepairItem> repairItemsList;

    public RepairFormController() {
        customerService = new CustomerService(UserSession.getSession());
        bicycleService = new BicycleService(UserSession.getSession());
        employeeService = new EmployeeService(UserSession.getSession());
        productService = new ProductService(UserSession.getSession());
        serviceService = new ServiceService(UserSession.getSession());
        repairService = new RepairService(UserSession.getSession());
        repairItemsList = buildRepairItemsList();
    }

    @FXML
    public void initialize() {
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        customerBox.getItems().addAll(customerService.getAll());
        bicycleBox.disableProperty().bind(customerBox.valueProperty().isNull());

        customerBox.valueProperty().addListener((observableValue, customer, t1) -> {
            if (t1 != null) {
                bicycleBox.getItems().clear();
                bicycleBox.getItems().addAll(t1.getBicycles());
            }
        });

        assignedEmployeeBox.getItems().addAll(employeeService.getAll());
        productsAndServicesBox.getItems().addAll(repairItemsList);
        productsAndServicesBox.setButtonCell(ComboBoxHelper.buildButtonCell(IRepairItem::getTitle));
        productsAndServicesBox.setCellFactory(ComboBoxHelper.buildCellFactory(IRepairItem::getTitle));

        customerBox.setButtonCell(ComboBoxHelper.buildButtonCell(customer -> String.format("%s %s", customer.getFirstName(), customer.getLastName())));
        customerBox.setCellFactory(ComboBoxHelper.buildCellFactory(customer -> String.format("%s %s", customer.getFirstName(), customer.getLastName())));

        bicycleBox.setButtonCell(ComboBoxHelper.buildButtonCell(Bicycle::getName));
        bicycleBox.setCellFactory(ComboBoxHelper.buildCellFactory(Bicycle::getName));

        assignedEmployeeBox.setButtonCell(ComboBoxHelper.buildButtonCell(employee -> String.format("%s %s", employee.getFirstName(), employee.getLastName())));
        assignedEmployeeBox.setCellFactory(ComboBoxHelper.buildCellFactory(employee -> String.format("%s %s", employee.getFirstName(), employee.getLastName())));

        statesBox.getItems().addAll(RepairStateEnum.values());

        addBtn.disableProperty().bind(Bindings.or(productsAndServicesBox.valueProperty().isNull(), quantityField.textProperty().isEmpty()));

        initializeFieldValidators();
    }

    private List<IRepairItem> buildRepairItemsList() {
        List<Product> productList = productService.getAllInStock();
        List<Service> serviceList = serviceService.getAll();
        List<IRepairItem> servicesAndProducts = new ArrayList<>();

        for (Product p : productList) {
            servicesAndProducts.add(new RepairItem(p.getId(), p.getName(), "Product - " + p.getTitle(),0F,SellableTypeEnum.PRODUCT));
        }

        for (Service s : serviceList) {
            servicesAndProducts.add(new RepairItem(s.getId(), s.getName(),"Service - " + s.getTitle(),0F,SellableTypeEnum.SERVICE));
        }

        return servicesAndProducts;
    }

    public void onAddItem() {
        IRepairItem value = productsAndServicesBox.getValue();
        value.setQuantity(Float.parseFloat(quantityField.getText()));
        repairItemsTable.getItems().add(value);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    public void onClickSave() {
        if (!validator.validate()) return;

        Repair formRepair = getFormData();

        try {
            if (!isEditing) {
                repairService.create(formRepair);
            } else {
                formRepair.setId(data.getId());
                repairService.update(formRepair);
            }
        } catch (BicycleNotFoundException | EmployeeNotFoundException | RepairNotFoundException e) {
            throw new RuntimeException(e);
        }

        requestFormClose(false);
        successCallback.call(formRepair);
    }

    private void initializeFieldValidators() {
        validator.createCheck()
                .dependsOn("customer", customerBox.valueProperty())
                .withMethod(context -> {
                    Customer customer = context.get("customer");

                    if (customer == null) {
                        context.error("A customer must be selected");
                    }
                })
                .decorates(customerBox);

        validator.createCheck()
                .dependsOn("bicycle", bicycleBox.valueProperty())
                .withMethod(context -> {
                    Bicycle bicycle = context.get("bicycle");

                    if (bicycle == null) {
                        context.error("A bicycle must be selected");
                    }
                })
                .decorates(bicycleBox);

        validator.createCheck()
                .dependsOn("employee", assignedEmployeeBox.valueProperty())
                .withMethod(context -> {
                    Employee employee = context.get("employee");

                    if (employee == null) {
                        context.error("An employee must be selected");
                    }
                })
                .decorates(assignedEmployeeBox);

        validator.createCheck()
                .dependsOn("state", statesBox.valueProperty())
                .withMethod(context -> {
                    RepairStateEnum state = context.get("state");

                    if (state == null) {
                        context.error("A repair state must be selected");
                    }
                })
                .decorates(statesBox);
    }

    @Override
    protected void onDataReceived(Repair data) {
        try {
            Bicycle b = bicycleService.get(data.getBicycleId());
            Customer c = customerService.get(b.getOwnerId());
            Employee e = employeeService.get(data.getAssignedEmployeeId());

            customerBox.setValue(c);
            Bicycle bici = bicycleBox.getItems().stream().filter(bike -> bike.getId().equals(data.getBicycleId())).findFirst().get();
            bicycleBox.setValue(bici);
            assignedEmployeeBox.setValue(e);
            statesBox.setValue(data.getState());
            notesArea.setText(data.getNotes());

            repairItemsTable.getItems().addAll(data.getSellablesUsed());
        } catch (BicycleNotFoundException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected Repair getFormData() {
        return new Repair(
            bicycleBox.getValue().getId(),
            statesBox.getValue(),
            notesArea.getText(),
            assignedEmployeeBox.getValue().getId(),
            repairItemsTable.getItems()
        );
    }
}
