package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.EmployeeEntity;
import org.nolhtaced.core.exceptions.EmployeeNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService extends BaseService {
    private final Dao<EmployeeEntity, Integer> employeeDao;

    public EmployeeService(NolhtacedSession session) {
        super(session);
        this.employeeDao = new DaoImpl<>(EmployeeEntity.class);
    }

    public void create(Employee employee) {
        EmployeeEntity employeeEntity = this.mapper.map(employee, EmployeeEntity.class);
        employeeDao.save(employeeEntity);
    }

    public Employee get(Integer id) throws UserNotFoundException {
        Optional<EmployeeEntity> employeeEntity = employeeDao.get(id);

        if (employeeEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        return mapper.map(employeeEntity.get(), Employee.class);
    }

    public List<Employee> getAll() {
        return employeeDao.getAll().stream().map(
                employee -> mapper.map(employee, Employee.class)
        ).collect(Collectors.toList());
    }

    public void update(Employee employee) throws UserNotFoundException {
        Optional<EmployeeEntity> employeeFromDao = employeeDao.get(employee.getId());

        if (employeeFromDao.isEmpty()) {
            throw new UserNotFoundException();
        }

        EmployeeEntity employeeEntity = this.mapper.map(employee, EmployeeEntity.class);
        employeeDao.update(employeeEntity);
    }

    public void delete(Integer id) throws UserNotFoundException, ObjectStillReferencedException {
        Optional<EmployeeEntity> employeeEntity = employeeDao.get(id);

        if (employeeEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        try {
            employeeDao.delete(employeeEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
