package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerBicycleEntity;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.exceptions.BicycleNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Bicycle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BicycleService extends BaseService {
    private final Dao<CustomerBicycleEntity, Integer> bicycleDao;
    private final Dao<CustomerEntity, Integer> customerDao;

    public BicycleService(NolhtacedSession session) {
        super(session);
        this.bicycleDao = new DaoImpl<>(CustomerBicycleEntity.class);
        this.customerDao = new DaoImpl<>(CustomerEntity.class);
    }

    public void create(Bicycle bicycle) throws UserNotFoundException {
        Optional<CustomerEntity> customer = customerDao.get(bicycle.getOwnerId());

        if (customer.isEmpty()) {
            throw new UserNotFoundException();
        }

        CustomerBicycleEntity customerBicycle = mapper.map(bicycle, CustomerBicycleEntity.class);
        bicycleDao.save(customerBicycle);
    }

    public Bicycle get(Integer id) throws BicycleNotFoundException {
        Optional<CustomerBicycleEntity> bicycleEntity = bicycleDao.get(id);

        if (bicycleEntity.isEmpty()) {
            throw new BicycleNotFoundException();
        }

        return mapper.map(bicycleEntity, Bicycle.class);
    }

    public List<Bicycle> getAll() {
        return bicycleDao.getAll().stream().map(
                bicycle -> mapper.map(bicycle, Bicycle.class)
        ).collect(Collectors.toList());
    }

    public void update(Bicycle bicycle) throws BicycleNotFoundException, UserNotFoundException {
        Optional<CustomerBicycleEntity> bicycleFromDao = bicycleDao.get(bicycle.getId());

        if (bicycleFromDao.isEmpty()) {
            throw new BicycleNotFoundException();
        }

        Optional<CustomerEntity> customer = customerDao.get(bicycle.getOwnerId());

        if (customer.isEmpty()) {
            throw new UserNotFoundException();
        }

        CustomerBicycleEntity bicycleEntity = mapper.map(bicycle, CustomerBicycleEntity.class);
        bicycleDao.update(bicycleEntity);
    }

    public void delete(Integer id) throws BicycleNotFoundException, ObjectStillReferencedException {
        Optional<CustomerBicycleEntity> bicycleEntity = bicycleDao.get(id);

        if (bicycleEntity.isEmpty()) {
            throw new BicycleNotFoundException();
        }

        try {
            bicycleDao.delete(bicycleEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
