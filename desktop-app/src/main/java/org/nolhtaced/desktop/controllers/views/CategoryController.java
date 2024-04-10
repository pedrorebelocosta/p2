package org.nolhtaced.desktop.controllers.views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.exceptions.CategoryNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.models.Category;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.services.CategoryService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.util.Optional;

public class CategoryController {
    @FXML
    public TableView<Category> tableView;
    @FXML
    public TableColumn<Category, Integer> idCol;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> titleCol;

    @FXML
    public Button addBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;

    private CategoryService categoryService;

    public CategoryController() {
        categoryService = new CategoryService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        editBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        deleteBtn.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));

        populateTable();
    }

    public void onClickAdd() {
        try {
            UIManager.openForm("/forms/categories/category-form.fxml", unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickDelete() {
        Category category = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(String.format("Deleting category '%s'", category.getTitle()));
        alert.setContentText("Are you want to delete this category?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            try {
                categoryService.delete(category.getId());
                // refresh table after deletion
                populateTable();
            } catch (CategoryNotFoundException | ObjectStillReferencedException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Unable to delete category!");
                errorAlert.setContentText("It was not possible to delete this category!");
                errorAlert.show();
            }
        }
    }

    public void onClickEdit() {
        Category category = tableView.getSelectionModel().getSelectedItem();

        try {
            UIManager.openForm("/forms/categories/category-form.fxml", category, unused -> this.populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Void populateTable() {
        tableView.getItems().setAll(categoryService.getAll());
        return null;
    }
}
