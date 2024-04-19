package org.nolhtaced.desktop.helpers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

public class TableCellHelper {
    public static <P,R> Callback<TableColumn<P,R>, TableCell<P,R>> buildButtonCellFactory(FontIcon buttonIcon, Callback<R, Void> callback) {
        return new Callback<>() {
            @Override
            public TableCell<P, R> call(TableColumn<P, R> prTableColumn) {
                final Button btn = new Button();

                {
                    btn.setGraphic(buttonIcon);
                }

                return new TableCell<>() {
                    @Override
                    protected void updateItem(R r, boolean b) {
                        super.updateItem(r, b);

                        if (b) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(evt -> callback.call(r));
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };
    }
}
