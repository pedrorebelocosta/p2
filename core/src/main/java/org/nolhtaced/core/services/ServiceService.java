package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.ServiceEntity;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.ServiceNotFoundException;
import org.nolhtaced.core.models.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceService extends BaseService {
    private final Dao<ServiceEntity, Integer> serviceDao;

    public ServiceService(NolhtacedSession session) {
        super(session);
        this.serviceDao = new DaoImpl<>(ServiceEntity.class);
    }

    public void create(Service service) {
        ServiceEntity serviceEntity = this.mapper.map(service, ServiceEntity.class);
        serviceDao.save(serviceEntity);
    }

    public Service get(Integer id) throws ServiceNotFoundException {
        Optional<ServiceEntity> serviceEntity = serviceDao.get(id);

        if (serviceEntity.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        return mapper.map(serviceEntity.get(), Service.class);
    }

    public List<Service> getAll() {
        return serviceDao.getAll().stream().map(
                service -> mapper.map(service, Service.class)
        ).collect(Collectors.toList());
    }

    public void update(Service service) throws ServiceNotFoundException {
        Optional<ServiceEntity> serviceFromDao = serviceDao.get(service.getId());

        if (serviceFromDao.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        ServiceEntity serviceEntity = this.mapper.map(service, ServiceEntity.class);
        serviceDao.update(serviceEntity);
    }

    public void delete(Integer id) throws ServiceNotFoundException, ObjectStillReferencedException {
        Optional<ServiceEntity> serviceEntity = serviceDao.get(id);

        if (serviceEntity.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        try {
            serviceDao.delete(serviceEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
