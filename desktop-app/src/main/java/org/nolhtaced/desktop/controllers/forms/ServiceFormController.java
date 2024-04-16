package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.exceptions.ServiceNotFoundException;
import org.nolhtaced.core.models.Service;
import org.nolhtaced.core.services.ServiceService;
import org.nolhtaced.desktop.UserSession;

public class ServiceFormController extends FormController<Service> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField priceField;

    private final ServiceService serviceService;

    public ServiceFormController() {
        serviceService = new ServiceService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        attachFieldValidators();
    }

    public void onClickSave() throws ServiceNotFoundException, ProductNotFoundException {
        if (!validator.validate()) return;

        Service formService = getFormData();

        if (!isEditing) {
            serviceService.create(formService);
        } else {
            formService.setId(data.getId());
            serviceService.update(formService);
        }

        requestFormClose(false);
        successCallback.call(formService);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    @Override
    protected void onDataReceived(Service data) {
        nameField.setText(data.getName());
        titleField.setText(data.getTitle());
        priceField.setText(data.getPrice().toString());
    }

    @Override
    protected Service getFormData() {
        return new Service(
            nameField.getText(),
            titleField.getText(),
            Float.parseFloat(priceField.getText())
        );
    }

    private void attachFieldValidators() {
        validator.createCheck()
            .dependsOn("name", nameField.textProperty())
            .withMethod(context -> {
                String name = context.get("name");

                if (name.isEmpty()) {
                    context.error("A name must be supplied");
                }
            }).decorates(nameField);

        validator.createCheck()
            .dependsOn("title", titleField.textProperty())
            .withMethod(context -> {
                String title = context.get("title");

                if (title.isEmpty()) {
                    context.error("A title must be supplied");
                }
            }).decorates(titleField);

        validator.createCheck()
            .dependsOn("price", priceField.textProperty())
            .withMethod(context -> {
                String price = context.get("price");

                if (price.isEmpty()) {
                    context.error("A price must be supplied");
                }
            })
            .decorates(priceField);
    }
}
