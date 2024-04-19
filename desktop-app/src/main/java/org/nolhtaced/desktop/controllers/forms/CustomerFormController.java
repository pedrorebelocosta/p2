package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.desktop.UserSession;

import java.util.ArrayList;

public class CustomerFormController extends FormController<Customer> {
    @FXML
    private TextField addressField;
    @FXML
    private TextField vatNoField;
    @FXML
    private TextField discountRateField;
    @FXML
    public UserFormController userFormController;
    private CustomerService customerService;

    public CustomerFormController() {
        customerService = new CustomerService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        userFormController.hideButtons();
        attachFieldValidators();
    }

    public void onClickSave() throws UserNotFoundException {
        boolean valid = userFormController.validator.validate() & validator.validate();

        if (!valid) return;

        Customer formCustomer = getFormData();

        if (!isEditing) {
            customerService.create(formCustomer);
        } else {
            formCustomer.setId(data.getId());
            customerService.update(formCustomer);
        }

        requestFormClose(false);
        successCallback.call(formCustomer);
    }

    private void attachFieldValidators() {
        validator.createCheck()
                .dependsOn("address", addressField.textProperty())
                .withMethod(context -> {
                    String address = context.get("address");
                    if (address.isEmpty()) {
                        context.error("An address must be supplied");
                    }
                })
                .decorates(addressField);

        validator.createCheck()
            .dependsOn("vatNo", vatNoField.textProperty())
            .withMethod(context -> {
                String vatNo = context.get("vatNo");
                if (vatNo.isEmpty()) {
                    context.error("A VAT number must be supplied");
                }
            })
            .decorates(vatNoField);

        validator.createCheck()
                .dependsOn("discountRate", discountRateField.textProperty())
                .withMethod(context -> {
                    String discountRate = context.get("discountRate");
                    if (discountRate.isEmpty()) {
                        context.error("A discount rate must be supplied");
                    }
                })
                .decorates(discountRateField);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    @Override
    public void onDataReceived(Customer data) {
        userFormController.setInitialData(data);
        addressField.setText(data.getAddress());
        vatNoField.setText(data.getVatNo().toString());
        discountRateField.setText(data.getDiscountRate().toString());
    }

    @Override
    protected Customer getFormData() {
        User userData = userFormController.getFormData();

        return new Customer(
                userData.getUsername(),
                userData.getPassword(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getDateOfBirth(),
                userData.getActive(),
                Float.parseFloat(discountRateField.getText()),
                addressField.getText(),
                Integer.parseInt(vatNoField.getText()),
                // TODO these aren't really ok are they lol
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
