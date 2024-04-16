package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.services.ProductService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.util.Optional;

public class ProductController {
    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product, Integer> idCol;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> titleCol;
    @FXML
    public TableColumn<Product, Float> priceCol;
    @FXML
    public TableColumn<Product, Integer> availableUnitsCol;
    @FXML
    public Button addBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;

    private ProductService productService;

    public ProductController() {
        this.productService = new ProductService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableUnitsCol.setCellValueFactory(new PropertyValueFactory<>("availableUnits"));
        editBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        deleteBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));

        populateTable();
    }

    public void onClickAdd() {
        try {
            UIManager.openForm("/forms/product-form.fxml", unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickDelete() {
        Product product = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(String.format("Deleting product '%s'", product.getTitle()));
        alert.setContentText("Are you want to delete this product?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            try {
                productService.delete(product.getId());
                // refresh table after deletion
                populateTable();
            } catch (ProductNotFoundException | ObjectStillReferencedException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Unable to delete product!");
                errorAlert.setContentText("It was not possible to delete this product!");
                errorAlert.show();
            }
        }
    }

    public void onClickEdit() {
        Product product = tableView.getSelectionModel().getSelectedItem();

        try {
            UIManager.openForm("/forms/product-form.fxml", product, unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Void populateTable() {
        tableView.getItems().setAll(productService.getAll());
        return null;
    }
}
