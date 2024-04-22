package org.nolhtaced.core.mappers;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.AppointmentEntity;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.entities.UserEntity;
import org.nolhtaced.core.enumerators.AppointmentStateEnum;
import org.nolhtaced.core.enumerators.AppointmentTypeEnum;
import org.nolhtaced.core.models.Appointment;

import java.util.HashMap;

public class AppointmentMapper {
    private static final Dao<UserEntity, Integer> userDao = new DaoImpl<>(UserEntity.class);
    private static final Dao<CustomerEntity, Integer> customerDao = new DaoImpl<>(CustomerEntity.class);
    private static final HashMap<String, AppointmentTypeEnum> REVERSE_APPOINTMENT_TYPES = new HashMap<>();
    private static final HashMap<String, AppointmentStateEnum> REVERSE_APPOINTMENT_STATE = new HashMap<>();

    static {
        REVERSE_APPOINTMENT_TYPES.put(AppointmentTypeEnum.REPAIR_REQUEST.value, AppointmentTypeEnum.REPAIR_REQUEST);
        REVERSE_APPOINTMENT_TYPES.put(AppointmentTypeEnum.REPAIR_PICKUP.value, AppointmentTypeEnum.REPAIR_PICKUP);
        REVERSE_APPOINTMENT_TYPES.put(AppointmentTypeEnum.CONTACT_REQUEST.value, AppointmentTypeEnum.CONTACT_REQUEST);

        REVERSE_APPOINTMENT_STATE.put(AppointmentStateEnum.PENDING.value, AppointmentStateEnum.PENDING);
        REVERSE_APPOINTMENT_STATE.put(AppointmentStateEnum.HANDLING.value, AppointmentStateEnum.HANDLING);
        REVERSE_APPOINTMENT_STATE.put(AppointmentStateEnum.HANDLED.value, AppointmentStateEnum.HANDLED);
        REVERSE_APPOINTMENT_STATE.put(AppointmentStateEnum.CLOSED.value, AppointmentStateEnum.CLOSED);
    }

    public static Converter<AppointmentEntity, Appointment> entityToDomain = new Converter<>() {
        @Override
        public Appointment convert(MappingContext<AppointmentEntity, Appointment> mappingContext) {
            AppointmentEntity source = mappingContext.getSource();
            Appointment destination = new Appointment();

            destination.setId(source.getId());
            destination.setCreatedAt(source.getCreatedAt());
            destination.setType(REVERSE_APPOINTMENT_TYPES.get(source.getType().trim()));
            destination.setCustomerId(source.getCustomer().getId());
            destination.setRequesterId(source.getRequester().getId());
            destination.setCustomerNotes(source.getCustomerNotes());
            destination.setEmployeeNotes(source.getEmployeeNotes());
            destination.setState(REVERSE_APPOINTMENT_STATE.get(source.getState().trim()));

            return destination;
        }
    };

    public static Converter<Appointment, AppointmentEntity> domainToEntity = new Converter<>() {
        @Override
        public AppointmentEntity convert(MappingContext<Appointment, AppointmentEntity> mappingContext) {
            Appointment source = mappingContext.getSource();
            AppointmentEntity destination = new AppointmentEntity();

            CustomerEntity customerEntity = customerDao.get(source.getCustomerId()).get();
            UserEntity requesterUserEntity = userDao.get(source.getRequesterId()).get();


            destination.setId(source.getId());
            destination.setCreatedAt(source.getCreatedAt());
            destination.setType(source.getType().value);
            destination.setCustomer(customerEntity);
            destination.setRequester(requesterUserEntity);
            destination.setCustomerNotes(source.getCustomerNotes());
            destination.setEmployeeNotes(source.getEmployeeNotes());
            destination.setState(source.getState().value);

            return destination;
        }
    };
}
