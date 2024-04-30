package org.nolhtaced.core.mappers;

import org.nolhtaced.core.entities.EmployeeEntity;
import org.nolhtaced.core.entities.UserEntity;
import org.nolhtaced.core.models.Employee;
import org.nolhtaced.core.models.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.providers.ModelMapperProvider;

public class EmployeeMapper {
    private static final ModelMapper mapper = ModelMapperProvider.getModelMapper();

    public static Converter<EmployeeEntity, Employee> entityToDomain = new Converter<EmployeeEntity, Employee>() {
        @Override
        public Employee convert(MappingContext<EmployeeEntity, Employee> mappingContext) {
            EmployeeEntity employeeEntity = mappingContext.getSource();
            Employee employee = new Employee();

            User user = mapper.map(employeeEntity.getUser(), User.class);

            employee.setId(user.getId());
            employee.setUsername(user.getUsername());
            employee.setPassword(user.getPassword());
            employee.setFirstName(user.getFirstName());
            employee.setLastName(user.getLastName());
            employee.setDateOfBirth(user.getDateOfBirth());
            employee.setActive(user.getActive());
            employee.setRole(user.getRole());

            employee.setHireDate(employeeEntity.getHireDate());
            employee.setTerminationDate(employeeEntity.getTerminationDate());

            return employee;
        }
    };

    public static Converter<Employee, EmployeeEntity> domainToEntity = new Converter<Employee, EmployeeEntity>() {
        @Override
        public EmployeeEntity convert(MappingContext<Employee, EmployeeEntity> mappingContext) {
            Employee employee = mappingContext.getSource();
            EmployeeEntity employeeEntity = new EmployeeEntity();

            UserEntity userEntity = mapper.map(employee, UserEntity.class);
            userEntity.setRole(employee.getRole().value);

            employeeEntity.setId(employee.getId());
            employeeEntity.setUser(userEntity);
            employeeEntity.setHireDate(employee.getHireDate());
            employeeEntity.setTerminationDate(employee.getTerminationDate());

            return employeeEntity;
        }
    };
}
