package org.nolhtaced.desktop.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.nolhtaced.core.enumerators.AppointmentStateEnum;
import org.nolhtaced.core.enumerators.AppointmentTypeEnum;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Appointment;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.AppointmentService;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.enumerators.EAppForm;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;
import org.nolhtaced.desktop.utilities.UIManager;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AppointmentsController {
    @FXML
    public TableView<Appointment> appointmentsTable;
    @FXML
    public TableColumn<Appointment, Integer> customerCol;
    @FXML
    public TableColumn<Appointment, Instant> timeCol;
    @FXML
    public TableColumn<Appointment, AppointmentTypeEnum> typeCol;
    @FXML
    public TableColumn<Appointment, AppointmentStateEnum> stateCol;

    @FXML
    public Button editBtn;

    private final AppointmentService appointmentService;
    private final CustomerService customerService;

    public AppointmentsController() {
        this.appointmentService = new AppointmentService(UserSession.getSession());
        this.customerService = new CustomerService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        editBtn.disableProperty().bind(appointmentsTable.getSelectionModel().selectedItemProperty().isNull());

        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        customerCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Appointment, Integer> call(TableColumn<Appointment, Integer> appointmentStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer customerId, boolean b) {
                        super.updateItem(customerId, b);

                        if (customerId == null || b) {
                            setGraphic(null);
                            setText("");
                            return;
                        }

                        try {
                            Customer customer = customerService.get(customerId);
                            setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
            }
        });

        timeCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Appointment, Instant> call(TableColumn<Appointment, Instant> appointmentInstantTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Instant instant, boolean b) {
                        super.updateItem(instant, b);

                        if (instant == null || b) {
                            setGraphic(null);
                            setText("");
                            return;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withZone(ZoneId.of("UTC"));
                        setText(formatter.format(instant));
                    }
                };
            }
        });

        populateTable();
    }

    public void onClickEdit() throws UIManagerNotInitializedException {
        Appointment item = appointmentsTable.getSelectionModel().getSelectedItem();

        try {
            UIManager.openForm(EAppForm.APPOINTMENT_FORM, item, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Void populateTable() {
        appointmentsTable.getItems().setAll(appointmentService.getAll());
        return null;
    }

    public void onClickRepairDropoff() throws UIManagerNotInitializedException {
        Appointment a = new Appointment();
        a.setType(AppointmentTypeEnum.REPAIR_REQUEST);

        try {
            UIManager.openForm(EAppForm.APPOINTMENT_FORM, a, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickRepairPickup() throws UIManagerNotInitializedException {
        Appointment a = new Appointment();
        a.setType(AppointmentTypeEnum.REPAIR_PICKUP);

        try {
            UIManager.openForm(EAppForm.APPOINTMENT_FORM, a, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickContactRequest() throws UIManagerNotInitializedException  {
        Appointment a = new Appointment();
        a.setType(AppointmentTypeEnum.CONTACT_REQUEST);

        try {
            UIManager.openForm(EAppForm.APPOINTMENT_FORM, a, unused -> populateTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
