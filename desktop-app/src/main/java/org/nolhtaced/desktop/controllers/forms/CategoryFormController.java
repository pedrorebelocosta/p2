package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.nolhtaced.core.exceptions.CategoryNotFoundException;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.models.Category;
import org.nolhtaced.core.services.CategoryService;
import org.nolhtaced.desktop.UserSession;

public class CategoryFormController extends FormController<Category> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField titleField;

    private final CategoryService categoryService;

    public CategoryFormController() {
        categoryService = new CategoryService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        attachFieldValidators();
    }

    @Override
    protected void onDataReceived(Category data) {
        nameField.setText(data.getName());
        titleField.setText(data.getTitle());
    }

    @Override
    protected Category getFormData() {
        return new Category(
                nameField.getText(),
                titleField.getText()
        );
    }

    public void onClickSave() throws CategoryNotFoundException, ProductNotFoundException {
        if (!validator.validate()) return;

        Category formCategory = getFormData();

        if (!isEditing) {
            categoryService.create(formCategory);
        } else {
            formCategory.setId(data.getId());
            categoryService.update(formCategory);
        }

        requestFormClose(false);
        successCallback.call(formCategory);
    }

    public void onClickCancel() {
        requestFormClose(true);
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
    }
}
