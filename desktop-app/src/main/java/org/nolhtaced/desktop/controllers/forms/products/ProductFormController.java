package org.nolhtaced.desktop.controllers.forms.products;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.nolhtaced.core.exceptions.CategoryNotFoundException;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.models.Category;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.services.CategoryService;
import org.nolhtaced.core.services.ProductService;
import org.nolhtaced.core.utilities.ImageUtil;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.controllers.forms.BaseFormController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductFormController extends BaseFormController<Product> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField priceField;
    @FXML
    public ComboBox<Category> categoryField;
    @FXML
    public TextField availableUnitsField;
    @FXML
    public ImageView imageViewField;

    private File image;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final List<Category> categories;

    public ProductFormController() {
        productService = new ProductService(UserSession.getSession());
        categoryService = new CategoryService(UserSession.getSession());
        categories = categoryService.getAll();
    }

    @FXML
    public void initialize() {
        populateCategories();
        attachFieldValidators();
    }

    public void onClickSave() throws CategoryNotFoundException, ProductNotFoundException {
        if (!validator.validate()) return;

        try {
            Product formProduct = getFormData();
            String imagePath = ImageUtil.saveImage(Files.readAllBytes(image.toPath()));
            formProduct.setImagePath(imagePath);

            if (!isEditing) {
                productService.create(formProduct);
            } else {
                formProduct.setId(data.getId());
                productService.update(formProduct);
            }

            requestFormClose(false);
            successCallback.call(formProduct);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDataReceived(Product data) {
        try {
            nameField.setText(data.getName());
            titleField.setText(data.getTitle());
            priceField.setText(data.getPrice().toString());
            Optional<Category> category = categoryField.getItems().stream().filter(
                    item -> Objects.equals(item.getId(), data.getCategoryId())
            ).findFirst();
            categoryField.setValue(category.get());
            availableUnitsField.setText(data.getAvailableUnits().toString());
            image = ImageUtil.getImageFile(data.getImagePath());
            imageViewField.setImage(new Image(image.toURI().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    public void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            image = file;
            imageViewField.setImage(new Image(image.toURI().toString()));
        }
    }

    @Override
    protected Product getFormData() {
        return new Product(
                nameField.getText(),
                titleField.getText(),
                Float.parseFloat(priceField.getText()),
                categoryField.getValue().getId(),
                Integer.parseInt(availableUnitsField.getText())
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

        validator.createCheck()
            .dependsOn("category", categoryField.valueProperty())
            .withMethod(context -> {
                Category category = context.get("category");

                if (category == null) {
                    context.error("A category must be supplied");
                }
            })
            .decorates(categoryField);

        validator.createCheck()
            .dependsOn("availableUnits", availableUnitsField.textProperty())
            .withMethod(context -> {
                String availableUnits = context.get("availableUnits");

                if (availableUnits.isEmpty()) {
                    context.error("Available units must be supplied");
                }
            })
            .decorates(availableUnitsField);

        // CHECK HOW TO MAKE THE IMAGE MANDATORY
        validator.createCheck()
            .dependsOn("image", imageViewField.imageProperty())
            .withMethod(context -> {
                Image image = context.get("image");

                if (image == null) {
                    context.error("An image must be supplied");
                }
            }).decorates(imageViewField);
    }

    private void populateCategories() {
        categoryField.setButtonCell(new ListCell<>(){
            private final Label comboboxLabel;

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                comboboxLabel = new Label();
                comboboxLabel.setTextFill(Color.BLACK);
            }

            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);

                if (category == null || empty) {
                    setGraphic(null);
                } else {
                    comboboxLabel.setText(category.getTitle());
                    setGraphic(comboboxLabel);
                }
            }
        });

        categoryField.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Category> call(ListView<Category> categoryListView) {
                return new ListCell<>() {
                    private final Label label;

                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        label = new Label();
                    }

                    @Override
                    protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            label.setText(item.getTitle());
                            setGraphic(label);
                        }
                    }
                };
            }
        });

        categoryField.getItems().addAll(categories);
    }
}
