package org.nolhtaced.desktop.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public abstract class TableViewController<T> {
    @FXML
    public TableView<T> tableView;

    protected abstract Void populateTable();
    protected abstract void onClickAdd();
    protected abstract void onClickEdit();
    protected abstract void onClickDelete();
}
