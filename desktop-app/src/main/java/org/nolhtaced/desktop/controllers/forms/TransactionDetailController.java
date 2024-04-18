package org.nolhtaced.desktop.controllers.forms;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nolhtaced.core.models.ITransactionItem;
import org.nolhtaced.core.models.Transaction;

public class TransactionDetailController extends FormController<Transaction> {
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
    public Label orderTotalLabel;

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

    }

    public void onClickClose() {
        requestFormClose(false);
    }

    @Override
    protected void onDataReceived(Transaction data) {
        sellablesTable.getItems().setAll(data.getItems());
        orderTotalLabel.setText(data.getTotalAmount().toString());
    }

    @Override
    protected Transaction getFormData() {
        return null;
    }
}
