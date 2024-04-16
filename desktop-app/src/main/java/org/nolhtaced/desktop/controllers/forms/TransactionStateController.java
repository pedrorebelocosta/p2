package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.nolhtaced.core.enumerators.TransactionStateEnum;

public class TransactionStateController extends FormController<TransactionStateEnum>{
    @FXML
    public ComboBox<TransactionStateEnum> statesCombobox;

    @FXML
    public void initialize() {
        statesCombobox.getItems().addAll(
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
    }

    @Override
    protected void onDataReceived(TransactionStateEnum data) {
        statesCombobox.setValue(data);
    }

    @Override
    protected TransactionStateEnum getFormData() {
        return statesCombobox.getValue();
    }

    public void onClickSave() {
        successCallback.call(statesCombobox.getValue());
        requestFormClose(false);
    }

    public void onClickCancel() {
        requestFormClose(true);
    }
}
