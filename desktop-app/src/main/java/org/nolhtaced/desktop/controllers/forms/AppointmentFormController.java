package org.nolhtaced.desktop.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import org.nolhtaced.core.enumerators.AppointmentStateEnum;
import org.nolhtaced.core.exceptions.AppointmentNotFoundException;
import org.nolhtaced.core.models.Appointment;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.AppointmentService;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.helpers.ComboBoxHelper;

import java.time.*;
import java.util.Objects;

import org.nolhtaced.desktop.helpers.TimeHelper;

public class AppointmentFormController extends FormController<Appointment> {
    @FXML
    public ComboBox<Customer> customerBox;
    @FXML
    public ComboBox<AppointmentStateEnum> stateBox;
    @FXML
    public ComboBox<LocalTime> timeBox;
    @FXML
    public DatePicker dateBox;
    @FXML
    public TextArea customerNotesArea;
    @FXML
    public TextArea employeeNotesArea;

    private AppointmentService appointmentService;
    private CustomerService customerService;

    public AppointmentFormController() {
        appointmentService = new AppointmentService(UserSession.getSession());
        customerService = new CustomerService(UserSession.getSession());
    }

    @FXML
    public void initialize() {
        customerBox.setButtonCell(ComboBoxHelper.buildButtonCell(this::formatCustomer));
        customerBox.setCellFactory(ComboBoxHelper.buildCellFactory(this::formatCustomer));
        customerBox.getItems().addAll(customerService.getAll());
        stateBox.getItems().addAll(AppointmentStateEnum.values());
        timeBox.getItems().addAll(TimeHelper.TIME_INTERVALS);
        attachFieldValidators();
    }

    private String formatCustomer(Customer customer) {
        return String.format("%s %s", customer.getFirstName(), customer.getLastName());
    }

    public void onClickCancel() {
        requestFormClose(true);
    }


    private void attachFieldValidators() {
        validator.createCheck()
            .dependsOn("customer", customerBox.valueProperty())
            .withMethod(context -> {
                Customer customer = context.get("customer");

                if (customer == null) {
                    context.error("A customer must be selected");
                }
            }).decorates(customerBox);

        validator.createCheck()
            .dependsOn("state", stateBox.valueProperty())
            .withMethod(context -> {
                AppointmentStateEnum state = context.get("state");

                if (state == null) {
                    context.error("A state must be selected");
                }
            }).decorates(stateBox);

        validator.createCheck()
                .dependsOn("time", timeBox.valueProperty())
                .withMethod(context -> {
                    LocalTime time = context.get("time");

                    if (time == null) {
                        context.error("A time must be selected");
                    }
                }).decorates(timeBox);

        validator.createCheck()
            .dependsOn("date", dateBox.valueProperty())
            .withMethod(context -> {
                LocalDate date = context.get("date");

                if (date == null) {
                    context.error("A date must be selected");
                }
            }).decorates(dateBox);
    }

    public void onClickSave() {
        Appointment appointment = getFormData();

        if (data.getId() != null) {
            try {
                appointment.setId(data.getId());
                appointmentService.update(appointment);
            } catch (AppointmentNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            appointmentService.create(getFormData());
        }

        requestFormClose(false);
        successCallback.call(appointment);
    }

    @Override
    protected void onDataReceived(Appointment data) {
        if (data.getId() == null) return;

        LocalDate date = LocalDate.ofInstant(data.getScheduleDate(), ZoneId.of("UTC"));
        LocalTime time = LocalTime.ofInstant(data.getScheduleDate(), ZoneId.of("UTC"));

        Customer customer1 = customerBox.getItems().stream().filter(customer -> Objects.equals(customer.getId(), data.getCustomerId())).findFirst().get();
        customerBox.setValue(customer1);
        stateBox.setValue(data.getState());
        timeBox.setValue(time);
        dateBox.setValue(date);
        customerNotesArea.setText(data.getCustomerNotes());
        employeeNotesArea.setText(data.getEmployeeNotes());
    }

    @Override
    protected Appointment getFormData() {
        Instant scheduleDate = LocalDateTime.of(dateBox.getValue(), timeBox.getValue()).toInstant(ZoneOffset.UTC);
        LocalDate creationDate;
        Integer requesterId;

        creationDate = data.getId() != null ? data.getCreatedAt() : LocalDate.now();
        requesterId = data.getId() != null ? data.getRequesterId() : UserSession.getSession().getCurrentUser().get().getId();

        return new Appointment(
                creationDate,
                scheduleDate,
                data.getType(),
                customerBox.getValue().getId(),
                requesterId,
                customerNotesArea.getText(),
                employeeNotesArea.getText(),
                stateBox.getValue()
        );
    }
}
