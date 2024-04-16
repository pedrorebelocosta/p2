package org.nolhtaced.desktop.controllers.forms;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.enumerators.TransactionStateEnum;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.exceptions.ServiceNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.*;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.services.ProductService;
import org.nolhtaced.core.services.ServiceService;
import org.nolhtaced.core.services.TransactionService;
import org.nolhtaced.desktop.UserSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesFormController extends FormController<Transaction> {
    @FXML
    public TableView<ITransactionItem> sellablesTable;
    @FXML
    public TableColumn<ITransactionItem, String> itemNameCol;
    @FXML
    public TableColumn<ITransactionItem, String> itemTitleCol;
    @FXML
    public TableColumn<ITransactionItem, Float> quantityCol;
    @FXML
    public TableColumn<ITransactionItem, Float> priceCol;
    @FXML
    public TableColumn<ITransactionItem, Float> totalCol;
    @FXML
    public TableColumn<ITransactionItem, Void> deleteRowCol;
    @FXML
    public TableColumn<ITransactionItem, SellableTypeEnum> typeCol;

    @FXML
    public ComboBox<Customer> customerField;
    @FXML
    public TextField quantityField;
    @FXML
    public Label totalCostLabel;
    @FXML
    public ComboBox<String> sellableListCombobox;
    @FXML
    public ComboBox<TransactionStateEnum> transactionStateField;
    @FXML
    public Button addItemBtn;

    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ServiceService serviceService;

    private final HashMap<String, String> productsAndServices;

    public SalesFormController() {
        transactionService = new TransactionService(UserSession.getSession());
        customerService = new CustomerService(UserSession.getSession());
        productService = new ProductService(UserSession.getSession());
        serviceService = new ServiceService(UserSession.getSession());
        productsAndServices = buildSellableList();
    }

    private HashMap<String, String> buildSellableList() {
        List<Product> productList = productService.getAllInStock();
        List<Service> serviceList = serviceService.getAll();
        HashMap<String, String> servicesAndProducts = new HashMap<>();

        for (Product p : productList) {
            servicesAndProducts.put("p-" + p.getName(), "Product - " + p.getTitle());
        }

        for (Service s : serviceList) {
            servicesAndProducts.put("s-" + s.getName(), "Service - " + s.getTitle());
        }

        return servicesAndProducts;
    }

    @FXML
    public void initialize() {
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        totalCol.setCellValueFactory(cellDataFeatures -> {
            ITransactionItem item = cellDataFeatures.getValue();

            return Bindings.createFloatBinding(() -> {
                if (item.getPrice() == null || item.getQuantity() == null) {
                    return 0F;
                }

                return item.getPrice() * item.getQuantity();
            }).asObject();
        });

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        sellableListCombobox.getItems().addAll(productsAndServices.keySet());

        sellableListCombobox.setButtonCell(new ListCell<>() {
            private final Label comboboxLabel;

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                comboboxLabel = new Label();
                comboboxLabel.setTextFill(Color.BLACK);
            }

            @Override
            protected void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);

                if (name == null || empty) {
                    setGraphic(null);
                } else {
                    comboboxLabel.setText(productsAndServices.get(name));
                    setGraphic(comboboxLabel);
                }
            }
        });

        sellableListCombobox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> categoryListView) {
                return new ListCell<>() {
                    private final Label label;

                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        label = new Label();
                    }

                    @Override
                    protected void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);

                        if (name == null || empty) {
                            setGraphic(null);
                        } else {
                            label.setText(productsAndServices.get(name));
                            setGraphic(label);
                        }
                    }
                };
            }
        });

        customerField.getItems().addAll(customerService.getAll());

        customerField.setButtonCell(new ListCell<>(){
            private final Label comboboxLabel;

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                comboboxLabel = new Label();
                comboboxLabel.setTextFill(Color.BLACK);
            }

            @Override
            protected void updateItem(Customer customer, boolean empty) {
                super.updateItem(customer, empty);

                if (customer == null || empty) {
                    setGraphic(null);
                } else {
                    comboboxLabel.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
                    setGraphic(comboboxLabel);
                }
            }
        });

        customerField.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> customerListView) {
                return new ListCell<>() {
                    private final Label label;

                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        label = new Label();
                    }

                    @Override
                    protected void updateItem(Customer customer, boolean empty) {
                        super.updateItem(customer, empty);

                        if (customer == null || empty) {
                            setGraphic(null);
                        } else {
                            label.setText(String.format("%d - %s %s", customer.getId(), customer.getFirstName(), customer.getLastName()));
                            setGraphic(label);
                        }
                    }
                };
            }
        });

        // TODO FIX THIS LOGIC (can't focus on this rn)
        addItemBtn.disableProperty().bind(
                Bindings.and(
                        sellableListCombobox.valueProperty().isNull(),
                        quantityField.textProperty().isNull().or(quantityField.textProperty().isEmpty())
                )
        );

        transactionStateField.getItems().addAll(
                TransactionStateEnum.ORDERED,
                TransactionStateEnum.AWAITING_PAYMENT,
                TransactionStateEnum.PAYMENT_RECEIVED,
                TransactionStateEnum.CONFIRMED,
                TransactionStateEnum.PROCESSED,
                TransactionStateEnum.SHIPPED,
                TransactionStateEnum.DELIVERED,
                TransactionStateEnum.DONE,
                TransactionStateEnum.CANCELLED
        );

        deleteRowCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ITransactionItem, Void> call(TableColumn<ITransactionItem, Void> iTransactionItemVoidTableColumn) {
                return new TableCell<>() {
                    final Button btn = new Button();

                    {
                        btn.setGraphic(new FontIcon(FontAwesomeRegular.TRASH_ALT));
                    }

                    @Override
                    protected void updateItem(Void unused, boolean empty) {
                        super.updateItem(unused, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(evt -> {
                                getTableView().getItems().remove(getIndex());
                                refreshTotalLabel();
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        });

        initializeFieldValidators();
    }

    @Override
    protected void onDataReceived(Transaction data) {
        throw new UnsupportedOperationException("This form doesn't allow editing");
    }

    public void onClickSave() throws UserNotFoundException {
        if (!validator.validate()) return;

        Transaction formTransaction = getFormData();
        transactionService.create(formTransaction);
        requestFormClose(false);
        successCallback.call(formTransaction);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }

    public void onAddItem() {
        String itemName = sellableListCombobox.getValue();
        Float quantity = Float.parseFloat(quantityField.getText());
        ITransactionItem item = new TransactionItem();

        try {
            // 🔨🔨🔨🔨🔨🔨🔨🔨🔨
            if (itemName.startsWith("s-")) {
                Service s = serviceService.getByName(itemName.substring(2));
                item.setId(s.getId());
                item.setName(s.getName());
                item.setTitle(s.getTitle());
                item.setQuantity(quantity);
                item.setPrice(s.getPrice());
                item.setType(SellableTypeEnum.SERVICE);
            }

            if (itemName.startsWith("p-")) {
                // 🔨🔨🔨🔨🔨🔨🔨🔨🔨
                Product p = productService.getByName(itemName.substring(2));
                item.setId(p.getId());
                item.setName(p.getName());
                item.setTitle(p.getTitle());
                item.setQuantity(quantity);
                item.setPrice(p.getPrice());
                item.setType(SellableTypeEnum.PRODUCT);
            }

            sellablesTable.getItems().add(item);
            refreshTotalLabel();

            sellableListCombobox.setValue(null);
            quantityField.setText(null);
        } catch (ProductNotFoundException | ServiceNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private float calculateTotal() {
        float total = 0;

        for (ITransactionItem item : sellablesTable.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }

    private void refreshTotalLabel() {
        totalCostLabel.setText(String.valueOf(calculateTotal()));
    }

    @Override
    protected Transaction getFormData() {
        List<ITransactionItem> items = new ArrayList<>(sellablesTable.getItems());

        return new Transaction(
                customerField.getValue().getId(),
                UserSession.getSession().getCurrentUser().get().getId(),
                calculateTotal(),
                LocalDate.now(),
                transactionStateField.getValue(),
                items
        );
    }

    public void initializeFieldValidators() {
        validator.createCheck()
            .dependsOn("customer", customerField.valueProperty())
            .withMethod(context -> {
                Customer customer = context.get("customer");

                if (customer == null) {
                    context.error("You must select a customer");
                }
            })
            .decorates(customerField);

        // TODO IMPROVEMENT THIS SHOULD BE A DOMAIN RULE
//        validator.createCheck()
//                .dependsOn("quantity", quantityField.textProperty())
//                .dependsOn("itemName", sellableListCombobox.valueProperty())
//                .withMethod(context -> {
//                    String sellableName = context.get("itemName");
//                    Float quantity = Float.parseFloat(context.get("quantity"));
//
//                    System.out.println("sellable name " + sellableName);
//                    System.out.println("available quantity " + quantity);
//
//                    if (sellableName.startsWith("p-")) {
//                        try {
//                            Product product = productService.getByName(sellableName);
//
//                            if (quantity > product.getAvailableUnits()) {
//                                context.error(String.format("Quantity cannot exceed current product stock of %s units", product.getAvailableUnits()));
//                            }
//
//                        } catch (ProductNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                })
//                .decorates(quantityField);

        validator.createCheck()
                .dependsOn("transactionState", transactionStateField.valueProperty())
                .withMethod(context -> {
                    TransactionStateEnum state = context.get("transactionState");

                    if (state == null) {
                        context.error("You must select a transaction state");
                    }
                })
                .decorates(transactionStateField);

        validator.createCheck()
                .dependsOn("sellables", sellablesTable.itemsProperty())
                .withMethod(context -> {
                    ObservableList<ITransactionItem> items = context.get("sellables");

                    if (items == null || items.isEmpty()) {
                        context.error("1 or more items are required to save a sale");
                    }
                })
                .decorates(sellablesTable);
    }
}
