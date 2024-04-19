package org.nolhtaced.core.mappers;

import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.entities.EmployeeEntity;
import org.nolhtaced.core.entities.TransactionEntity;
import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.enumerators.TransactionStateEnum;
import org.nolhtaced.core.models.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.providers.ModelMapperProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionMapper {
    private static final Dao<CustomerEntity, Integer> customerDao = new DaoImpl<>(CustomerEntity.class);
    private static final Dao<EmployeeEntity, Integer> employeeDao = new DaoImpl<>(EmployeeEntity.class);

    public static Converter<TransactionEntity, Transaction> entityToDomain = new Converter<TransactionEntity, Transaction>() {
        @Override
        public Transaction convert(MappingContext<TransactionEntity, Transaction> mappingContext) {
            TransactionEntity transactionEntity = mappingContext.getSource();
            Transaction transaction = new Transaction();

            transaction.setId(transactionEntity.getId());
            // 🔨🔨🔨🔨🔨🔨🔨🔨🔨🔨
            if (transactionEntity.getEmployee() != null) {
                transaction.setEmployeeId(transactionEntity.getEmployee().getId());
            }

            transaction.setCustomerId(transactionEntity.getCustomer().getId());
            transaction.setTotalAmount(transactionEntity.getTotalAmount());
            transaction.setCreatedAt(transactionEntity.getCreatedAt());
            transaction.setState(TransactionStateEnum.valueOf(transactionEntity.getTransactionState().toUpperCase()));

            List<ITransactionItem> acquiredProducts = transactionEntity.getTransactionProducts().stream().map(
                    transactionProduct -> new TransactionItem(
                            transactionProduct.getProduct().getId(),
                            transactionProduct.getProduct().getName(),
                            transactionProduct.getProduct().getTitle(),
                            transactionProduct.getQuantity(),
                            transactionProduct.getUnitPrice(),
                            SellableTypeEnum.PRODUCT
                    )
            ).collect(Collectors.toList());

            List<ITransactionItem> acquiredServices = transactionEntity.getTransactionServices().stream().map(
                    transactionService -> new TransactionItem(
                            transactionService.getService().getId(),
                            transactionService.getService().getName(),
                            transactionService.getService().getTitle(),
                            transactionService.getQuantity(),
                            transactionService.getPrice(),
                            SellableTypeEnum.SERVICE
                    )
            ).collect(Collectors.toList());

            List<ITransactionItem> items = Stream.concat(acquiredProducts.stream(), acquiredServices.stream()).collect(Collectors.toList());
            transaction.setItems(items);

            return transaction;
        }
    };

    public static Converter<Transaction, TransactionEntity> domainToEntity = new Converter<Transaction, TransactionEntity>() {
        @Override
        public TransactionEntity convert(MappingContext<Transaction, TransactionEntity> mappingContext) {
            Transaction transaction = mappingContext.getSource();
            TransactionEntity transactionEntity = new TransactionEntity();

            CustomerEntity customerEntity = customerDao.get(transaction.getCustomerId()).orElseThrow();

            if (transaction.getEmployeeId() != null) {
                EmployeeEntity employeeEntity = employeeDao.get(transaction.getEmployeeId()).orElseThrow();
                transactionEntity.setEmployee(employeeEntity);
            }

            transactionEntity.setId(transaction.getId());
            transactionEntity.setCustomer(customerEntity);
            transactionEntity.setTotalAmount(transaction.getTotalAmount());
            transactionEntity.setCreatedAt(transaction.getCreatedAt());
            transactionEntity.setTransactionState(transaction.getState().value);

            return transactionEntity;
        }
    };
}
