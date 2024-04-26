package org.nolhtaced.core.mappers;

import org.nolhtaced.core.entities.CustomerBicycleEntity;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.entities.UserEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.models.Bicycle;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.models.Transaction;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.providers.ModelMapperProvider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerMapper {
    private static final ModelMapper mapper = ModelMapperProvider.getModelMapper();

    public static Converter<CustomerEntity, Customer> entityToDomain = new Converter<CustomerEntity, Customer>() {
        @Override
        public Customer convert(MappingContext<CustomerEntity, Customer> mappingContext) {
            CustomerEntity customerEntity = mappingContext.getSource();
            Customer customer = new Customer();

            User user = mapper.map(customerEntity.getUser(), User.class);

            customer.setId(user.getId());
            customer.setUsername(user.getUsername());
            customer.setPassword(user.getPassword());
            customer.setFirstName(user.getFirstName());
            customer.setLastName(user.getLastName());
            customer.setDateOfBirth(user.getDateOfBirth());
            customer.setActive(user.getActive());
            customer.setRole(user.getRole());
            customer.setDiscountRate(customerEntity.getDiscountRate());
            customer.setAddress(customerEntity.getAddress());
            customer.setVatNo(customerEntity.getVatNo());

            List<Bicycle> bicycles = customerEntity.getCustomerBicycles().stream().map(
                    bicycle -> mapper.map(bicycle, Bicycle.class)
            ).collect(Collectors.toList());

            List<Transaction> transactions = customerEntity.getTransactions().stream().map(
                    transaction -> mapper.map(transaction, Transaction.class)
            ).collect(Collectors.toList());

            customer.setBicycles(bicycles);
            customer.setTransactions(transactions);

            return customer;
        }
    };

    public static Converter<Customer, CustomerEntity> domainToEntity = new Converter<Customer, CustomerEntity>() {
        @Override
        public CustomerEntity convert(MappingContext<Customer, CustomerEntity> mappingContext) {
            Customer customer = mappingContext.getSource();
            CustomerEntity customerEntity = new CustomerEntity();
            UserEntity userEntity = mapper.map(customer, UserEntity.class);

            // TODO desmartelate 🔨🔨🔨🔨
            userEntity.setRole("C");
            customerEntity.setId(customer.getId());
            customerEntity.setUser(userEntity);
            customerEntity.setDiscountRate(customer.getDiscountRate());
            customerEntity.setAddress(customer.getAddress());
            customerEntity.setVatNo(customer.getVatNo());

            Set<CustomerBicycleEntity> bicycles = customer.getBicycles().stream().map(
                    bicycle -> mapper.map(bicycle, CustomerBicycleEntity.class)
            ).collect(Collectors.toSet());

            customerEntity.setCustomerBicycles(bicycles);
            return customerEntity;
        }
    };
}
