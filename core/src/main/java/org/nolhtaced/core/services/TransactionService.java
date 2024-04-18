package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.models.Service;
import org.nolhtaced.core.types.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.*;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.TransactionNotFoundException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionService extends BaseService {
    private final Dao<TransactionEntity, Integer> transactionDao = new DaoImpl<>(TransactionEntity.class);
    private final Dao<CustomerEntity, Integer> customerDao = new DaoImpl<>(CustomerEntity.class);
    private final Dao<ProductEntity, Integer> productDao = new DaoImpl<>(ProductEntity.class);
    private final Dao<ServiceEntity, Integer> serviceDao = new DaoImpl<>(ServiceEntity.class);

    public TransactionService(NolhtacedSession session) {
        super(session);
    }

    public void create(Transaction transaction) throws UserNotFoundException {
        TransactionEntity transactionEntity = this.mapper.map(transaction, TransactionEntity.class);
        Optional<CustomerEntity> customer = customerDao.get(transaction.getCustomerId());

        if (customer.isEmpty()) {
            throw new UserNotFoundException();
        }

        transactionDao.save(transactionEntity);

        Set<TransactionProductEntity> transactionProductEntities = transaction.getItems().stream()
                .filter(item -> item.getType() == SellableTypeEnum.PRODUCT)
                .map(
                        transactionProduct -> {
                            TransactionProductEntity line = new TransactionProductEntity();
                            ProductEntity product = productDao.get(transactionProduct.getId()).orElseThrow();

                            TransactionProductIdEntity transactionProductIdEntity = new TransactionProductIdEntity();
                            transactionProductIdEntity.setProductId(product.getId());
                            transactionProductIdEntity.setTransactionId(transactionEntity.getId());
                            line.setId(transactionProductIdEntity);
                            line.setProduct(product);
                            line.setTransaction(transactionEntity);
                            line.setQuantity(transactionProduct.getQuantity());
                            line.setUnitPrice(transactionProduct.getPrice());

                            return line;
                        }
                ).collect(Collectors.toSet());

        Set<TransactionServiceEntity> transactionServiceEntities = transaction.getItems().stream()
                .filter(item -> item.getType() == SellableTypeEnum.SERVICE)
                .map(
                        transactionService -> {
                            TransactionServiceEntity line = new TransactionServiceEntity();
                            ServiceEntity service =  serviceDao.get(transactionService.getId()).orElseThrow();

                            TransactionServiceIdEntity transactionServiceIdEntity = new TransactionServiceIdEntity();
                            transactionServiceIdEntity.setServiceId(service.getId());
                            transactionServiceIdEntity.setTransactionId(transactionEntity.getId());
                            line.setId(transactionServiceIdEntity);
                            line.setService(service);
                            line.setTransaction(transactionEntity);
                            line.setQuantity(transactionService.getQuantity());
                            line.setPrice(transactionService.getPrice());

                            return line;
                        }
                ).collect(Collectors.toSet());

        transactionEntity.setTransactionProducts(transactionProductEntities);
        transactionEntity.setTransactionServices(transactionServiceEntities);

        transactionDao.update(transactionEntity);

        // SUBTRACT FROM PRODUCT STOCK WHEN SELLING
        transactionProductEntities.forEach(transactionProduct -> {
            ProductEntity product = transactionProduct.getProduct();
            product.setAvailableUnits(product.getAvailableUnits() - transactionProduct.getQuantity().intValue());
            productDao.update(product);
        });
    }

    public Transaction get(Integer id) throws TransactionNotFoundException {
        Optional<TransactionEntity> transactionEntity = transactionDao.get(id);

        if (transactionEntity.isEmpty()) {
            throw new TransactionNotFoundException();
        }

        return mapper.map(transactionEntity.get(), Transaction.class);
    }

    public List<Transaction> getAll() {
        return transactionDao.getAll().stream().map(
                transaction -> mapper.map(transaction, Transaction.class)
        ).collect(Collectors.toList());
    }

    public List<Transaction> getAllOnlineOrders() {
        return transactionDao.getAll().stream()
                .filter(transaction -> transaction.getEmployee() == null)
                .map(transaction -> mapper.map(transaction, Transaction.class))
                .collect(Collectors.toList());
    }

    public void update(Transaction transaction) throws TransactionNotFoundException, UserNotFoundException {
        Optional<TransactionEntity> transactionFromDao = transactionDao.get(transaction.getId());

        if (transactionFromDao.isEmpty()) {
            throw new TransactionNotFoundException();
        }

        Optional<CustomerEntity> customer = customerDao.get(transaction.getCustomerId());

        if (customer.isEmpty()) {
            throw new UserNotFoundException();
        }

        TransactionEntity transactionEntity = mapper.map(transaction, TransactionEntity.class);

        Set<TransactionProductEntity> transactionProductEntities = transaction.getItems().stream()
            .filter(item -> item.getType() == SellableTypeEnum.PRODUCT)
            .map(
                transactionProduct -> {
                    TransactionProductEntity line = new TransactionProductEntity();
                    ProductEntity product =  productDao.get(transactionProduct.getId()).orElseThrow();
                    TransactionProductIdEntity transactionProductIdEntity = new TransactionProductIdEntity();
                    transactionProductIdEntity.setProductId(product.getId());
                    transactionProductIdEntity.setTransactionId(transactionEntity.getId());
                    line.setId(transactionProductIdEntity);
                    line.setProduct(product);
                    line.setTransaction(transactionEntity);
                    line.setQuantity(transactionProduct.getQuantity());
                    line.setUnitPrice(transactionProduct.getPrice());

                    return line;
            }
        ).collect(Collectors.toSet());

        Set<TransactionServiceEntity> transactionServiceEntities = transaction.getItems().stream()
            .filter(item -> item.getType() == SellableTypeEnum.SERVICE)
            .map(
                transactionService -> {
                    TransactionServiceEntity line = new TransactionServiceEntity();
                    ServiceEntity service =  serviceDao.get(transactionService.getId()).orElseThrow();
                    TransactionServiceIdEntity transactionServiceIdEntity = new TransactionServiceIdEntity();
                    transactionServiceIdEntity.setServiceId(service.getId());
                    transactionServiceIdEntity.setTransactionId(transactionEntity.getId());
                    line.setId(transactionServiceIdEntity);
                    line.setService(service);
                    line.setTransaction(transactionEntity);
                    line.setQuantity(transactionService.getQuantity());
                    line.setPrice(transactionService.getPrice());

                    return line;
                }
        ).collect(Collectors.toSet());

        transactionEntity.setTransactionProducts(transactionProductEntities);
        transactionEntity.setTransactionServices(transactionServiceEntities);

        transactionDao.update(transactionEntity);
    }

    public void delete(Integer id) throws TransactionNotFoundException, ObjectStillReferencedException {
        Optional<TransactionEntity> transactionEntity = transactionDao.get(id);

        if (transactionEntity.isEmpty()) {
            throw new TransactionNotFoundException();
        }

        try {
            transactionDao.delete(transactionEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
