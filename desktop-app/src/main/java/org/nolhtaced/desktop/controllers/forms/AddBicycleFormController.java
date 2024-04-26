package org.nolhtaced.desktop.controllers.forms;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.enumerators.BicycleTypeEnum;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Bicycle;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.BicycleService;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.desktop.UserSession;

import java.util.ArrayList;

public class AddBicycleFormController extends FormController<Customer> {
    @FXML
    public TableView<Bicycle> bicycleTableView;
    @FXML
    public TableColumn<Bicycle, String> nameCol;
    @FXML
    public TableColumn<Bicycle, String> modelCol;
    @FXML
    public TableColumn<Bicycle, String> brandCol;
    @FXML
    public TableColumn<Bicycle, BicycleTypeEnum> typeCol;
    @FXML
    public TableColumn<Bicycle, Void> deleteRowCol;

    @FXML
    public TextField nameField;
    @FXML
    public TextField brandField;
    @FXML
    public TextField modelField;
    @FXML
    public ComboBox<BicycleTypeEnum> bicycleTypeBox;

    @FXML
    public Button addBicycleBtn;

    private final CustomerService customerService;
    private final BicycleService bicycleService;

    public AddBicycleFormController() {
        customerService = new CustomerService(UserSession.getSession());
        bicycleService = new BicycleService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        bicycleTypeBox.getItems().addAll(BicycleTypeEnum.values());

        addBicycleBtn.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(modelField.textProperty().isEmpty())
                        .or(brandField.textProperty().isEmpty())
                        .or(bicycleTypeBox.valueProperty().isNull())
        );
    }

    public void onClickSave() throws UserNotFoundException {
        Customer formCustomer = getFormData();
        customerService.update(formCustomer);
        requestFormClose(false);
        successCallback.call(formCustomer);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    public void onClickAddBicycle() {
        Bicycle b = new Bicycle(
                data.getId(),
                nameField.getText(),
                modelField.getText(),
                brandField.getText(),
                bicycleTypeBox.getValue(),
                new ArrayList<>()
        );

        bicycleTableView.getItems().add(b);
    }

    @Override
    protected void onDataReceived(Customer data) {
        bicycleTableView.getItems().addAll(data.getBicycles());
    }

    @Override
    protected Customer getFormData() {
        return new Customer(
                data.getId(),
                data.getUsername(),
                data.getPassword(),
                data.getFirstName(),
                data.getLastName(),
                data.getDateOfBirth(),
                data.getActive(),
                data.getDiscountRate(),
                data.getAddress(),
                data.getVatNo(),
                bicycleTableView.getItems(),
                data.getTransactions()
        );
    }
}
