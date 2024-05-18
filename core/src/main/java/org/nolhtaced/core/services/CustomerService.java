package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.types.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.utilities.PasswordUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService extends BaseService {
    private final UserService userService;
    private final Dao<CustomerEntity, Integer> customerDao;

    public CustomerService(NolhtacedSession session) {
        super(session);
        this.userService = new UserService(session);
        this.customerDao = new DaoImpl<>(CustomerEntity.class);
    }

    public void create(Customer customer) {
        CustomerEntity customerEntity = this.mapper.map(customer, CustomerEntity.class);
        String hashedPassword = PasswordUtil.generateHash(customer.getPassword());
        customerEntity.getUser().setPassword(hashedPassword);
        customerDao.save(customerEntity);
    }

    public Customer get(Integer id) throws UserNotFoundException {
        Optional<CustomerEntity> customerEntity = customerDao.get(id);

        if (customerEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        return this.mapper.map(customerEntity.get(), Customer.class);
    }

    public Customer getByUsername(String userName) throws UserNotFoundException {
        User user = userService.getByUsername(userName);
        Optional<CustomerEntity> customerEntity = customerDao.get(user.getId());

        if (customerEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        return this.mapper.map(customerEntity.get(), Customer.class);
    }

    public List<Customer> getAll() {
        return customerDao.getAll().stream().map(
                customer -> this.mapper.map(customer, Customer.class)
        ).collect(Collectors.toList());
    }

    public void update(Customer customer) throws UserNotFoundException {
        Optional<CustomerEntity> customerFromDao = customerDao.get(customer.getId());

        if (customerFromDao.isEmpty()) {
            throw new UserNotFoundException();
        }

        CustomerEntity customerEntity = this.mapper.map(customer, CustomerEntity.class);
        customerDao.update(customerEntity);
    }

    public void delete(Integer id) throws UserNotFoundException, ObjectStillReferencedException {
        Optional<CustomerEntity> customerEntity = customerDao.get(id);

        if (customerEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        try {
            customerDao.delete(customerEntity.get());
            userService.delete(id);
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
