package org.nolhtaced.desktop.controllers.forms.users;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.desktop.controllers.forms.BaseFormController;

public class CustomerFormController extends BaseFormController<Customer> {
    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private UserFormController userFormController;

    public CustomerFormController() {
        userFormController = new UserFormController();
    }

    @FXML
    public void initialize() {
        userFormController.hideButtons();
    }

    @Override
    public void onDataReceived(Customer data) {
        userFormController.onDataReceived(data);
        // TODO IMPLEMENT LOGIC FOR RECEIVING DATA, INTERPRET THIS AS EDIT MODE
    }

    @Override
    public EventHandler<WindowEvent> onCloseRequest() {
        return windowEvent -> {};
    }
}
