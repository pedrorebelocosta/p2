package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.AppointmentEntity;
import org.nolhtaced.core.exceptions.AppointmentNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.models.Appointment;
import org.nolhtaced.core.types.NolhtacedSession;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService extends BaseService {
    private final Dao<AppointmentEntity, Integer> appointmentDao;

    public AppointmentService(NolhtacedSession session) {
        super(session);
        appointmentDao = new DaoImpl<>(AppointmentEntity.class);
    }

    public void create(Appointment appointment) {
        AppointmentEntity appointmentEntity = mapper.map(appointment, AppointmentEntity.class);
        appointmentDao.save(appointmentEntity);
    }

    public Appointment get(Integer id) throws AppointmentNotFoundException {
        Optional<AppointmentEntity> appointmentEntity = appointmentDao.get(id);

        if (appointmentEntity.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        return this.mapper.map(appointmentEntity.get(), Appointment.class);
    }

    public List<Appointment> getAll() {
        return appointmentDao.getAll().stream().map(
                appointmentEntity -> mapper.map(appointmentEntity, Appointment.class)
        ).collect(Collectors.toList());
    }

    public void update(Appointment appointment) throws AppointmentNotFoundException {
        Optional<AppointmentEntity> appointmentFromDao = appointmentDao.get(appointment.getId());

        if (appointmentFromDao.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        AppointmentEntity appointmentEntity = mapper.map(appointment, AppointmentEntity.class);
        appointmentDao.update(appointmentEntity);
    }

    public void delete(Integer id) throws AppointmentNotFoundException, ObjectStillReferencedException {
        Optional<AppointmentEntity> appointmentEntity = appointmentDao.get(id);

        if (appointmentEntity.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        try {
            appointmentDao.delete(appointmentEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
