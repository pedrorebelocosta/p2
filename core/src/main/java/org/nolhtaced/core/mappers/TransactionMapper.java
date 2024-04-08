package org.nolhtaced.core.mappers;

import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.entities.EmployeeEntity;
import org.nolhtaced.core.entities.TransactionEntity;
import org.nolhtaced.core.enumerators.TransactionStateEnum;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.models.Service;
import org.nolhtaced.core.models.Transaction;
import org.nolhtaced.core.models.TransactionItem;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.providers.ModelMapperProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {
    private static final ModelMapper mapper = ModelMapperProvider.getModelMapper();
    private static final Dao<CustomerEntity, Integer> customerDao = new DaoImpl<>(CustomerEntity.class);
    private static final Dao<EmployeeEntity, Integer> employeeDao = new DaoImpl<>(EmployeeEntity.class);

    private static final HashMap<String, TransactionStateEnum> REVERSE_TRANSACTION_STATES = new HashMap<>();

    static {
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.ORDERED.toString(), TransactionStateEnum.ORDERED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.AWAITING_PAYMENT.toString(), TransactionStateEnum.AWAITING_PAYMENT);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.PAYMENT_RECEIVED.toString(), TransactionStateEnum.PAYMENT_RECEIVED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.CONFIRMED.toString(), TransactionStateEnum.CONFIRMED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.PROCESSED.toString(), TransactionStateEnum.PROCESSED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.SHIPPED.toString(), TransactionStateEnum.SHIPPED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.DELIVERED.toString(), TransactionStateEnum.DELIVERED);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.DONE.toString(), TransactionStateEnum.DONE);
        REVERSE_TRANSACTION_STATES.put(TransactionStateEnum.CANCELLED.toString(), TransactionStateEnum.CANCELLED);
    }

    public static Converter<TransactionEntity, Transaction> entityToDomain = new Converter<TransactionEntity, Transaction>() {
        @Override
        public Transaction convert(MappingContext<TransactionEntity, Transaction> mappingContext) {
            TransactionEntity transactionEntity = mappingContext.getSource();
            Transaction transaction = new Transaction();

            transaction.setId(transactionEntity.getId());
            transaction.setEmployeeId(transactionEntity.getEmployee().getId());
            transaction.setCustomerId(transactionEntity.getCustomer().getId());
            transaction.setTotalAmount(transactionEntity.getTotalAmount());
            transaction.setCreatedAt(transactionEntity.getCreatedAt());
            transaction.setState(REVERSE_TRANSACTION_STATES.get(transactionEntity.getTransactionState()));

            List<TransactionItem<Product>> acquiredProducts = transactionEntity.getTransactionProducts().stream().map(
                    transactionProduct -> new TransactionItem<>(
                            mapper.map(transactionProduct.getProduct(), Product.class),
                            transactionProduct.getQuantity(),
                            transactionProduct.getUnitPrice()
                    )
            ).collect(Collectors.toList());

            List<TransactionItem<Service>> acquiredServices = transactionEntity.getTransactionServices().stream().map(
                    transactionService -> new TransactionItem<>(
                            mapper.map(transactionService.getService(), Service.class),
                            transactionService.getQuantity(),
                            transactionService.getPrice()
                    )
            ).collect(Collectors.toList());

            transaction.setProducts(acquiredProducts);
            transaction.setServices(acquiredServices);

            return transaction;
        }
    };

    public static Converter<Transaction, TransactionEntity> domainToEntity = new Converter<Transaction, TransactionEntity>() {
        @Override
        public TransactionEntity convert(MappingContext<Transaction, TransactionEntity> mappingContext) {
            Transaction transaction = mappingContext.getSource();
            TransactionEntity transactionEntity = new TransactionEntity();

            CustomerEntity customerEntity = customerDao.get(transaction.getCustomerId()).orElseThrow();
            EmployeeEntity employeeEntity = employeeDao.get(transaction.getEmployeeId()).orElseThrow();

            transactionEntity.setId(transaction.getId());
            transactionEntity.setCustomer(customerEntity);
            transactionEntity.setEmployee(employeeEntity);

            transactionEntity.setTotalAmount(transaction.getTotalAmount());
            transactionEntity.setCreatedAt(transaction.getCreatedAt());
            transactionEntity.setTransactionState(transaction.getState().toString());

            // TODO find a better solution for reverse mappings...
            return transactionEntity;
        }
    };
}
