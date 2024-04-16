package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.ServiceNotFoundException;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.models.Service;
import org.nolhtaced.core.services.ServiceService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.util.Optional;

public class ServiceController {
    @FXML
    public TableView<Service> tableView;
    @FXML
    public TableColumn<Product, Integer> idCol;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> titleCol;
    @FXML
    public TableColumn<Product, Float> priceCol;

    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;

    private ServiceService serviceService;

    public ServiceController() {
        this.serviceService = new ServiceService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        editBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        deleteBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));

        populateTable();
    }

    public void onClickDelete() {
        Service service = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(String.format("Deleting service '%s'", service.getTitle()));
        alert.setContentText("Are you want to delete this service?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            try {
                serviceService.delete(service.getId());
                // refresh table after deletion
                populateTable();
            } catch (ServiceNotFoundException | ObjectStillReferencedException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Unable to delete service!");
                errorAlert.setContentText("It was not possible to delete this service!");
                errorAlert.show();
            }
        }
    }

    public void onClickEdit() {
        Service service = tableView.getSelectionModel().getSelectedItem();

        try {
            UIManager.openForm("/forms/service-form.fxml", service, unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickAdd() {
        try {
            UIManager.openForm("/forms/service-form.fxml", unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Void populateTable() {
        tableView.getItems().setAll(serviceService.getAll());
        return null;
    }
}
