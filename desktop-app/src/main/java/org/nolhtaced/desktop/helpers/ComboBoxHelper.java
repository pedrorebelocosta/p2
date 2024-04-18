package org.nolhtaced.desktop.helpers;

import com.sun.javafx.binding.StringFormatter;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.lang.reflect.Field;

public class ComboBoxHelper {
    public static <T> ListCell<T> buildButtonCell(Callback<T, String> formatFunction) {
        return new ListCell<>() {
            private final Label comboboxLabel;

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                comboboxLabel = new Label();
                comboboxLabel.setTextFill(Color.BLACK);
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    String label = formatFunction.call(item);
                    comboboxLabel.setText(label);
                    setGraphic(comboboxLabel);
                }
            }
        };
    }

    public static <T> Callback<ListView<T>, ListCell<T>> buildCellFactory(Callback<T, String> formatFunction) {
        return new Callback<>() {
            @Override
            public ListCell<T> call(ListView<T> tListView) {
                return new ListCell<>() {
                    private final Label label;

                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        label = new Label();
                    }

                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            label.setText(formatFunction.call(item));
                            setGraphic(label);
                        }
                    }
                };
            }
        };
    }
}
