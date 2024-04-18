package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.types.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.*;
import org.nolhtaced.core.exceptions.BicycleNotFoundException;
import org.nolhtaced.core.exceptions.EmployeeNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.RepairNotFoundException;
import org.nolhtaced.core.models.Repair;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RepairService extends BaseService {
    private final Dao<RepairEntity, Integer> repairDao;
    private final Dao<CustomerBicycleEntity, Integer> bicycleDao;
    private final Dao<EmployeeEntity, Integer> employeeDao;
    private final Dao<ProductEntity, Integer> productDao;
    private final Dao<ServiceEntity, Integer> serviceDao;

    public RepairService(NolhtacedSession session) {
        super(session);
        this.repairDao = new DaoImpl<>(RepairEntity.class);
        this.bicycleDao = new DaoImpl<>(CustomerBicycleEntity.class);
        this.employeeDao = new DaoImpl<>(EmployeeEntity.class);
        this.productDao = new DaoImpl<>(ProductEntity.class);
        this.serviceDao = new DaoImpl<>(ServiceEntity.class);
    }

    public void create(Repair repair) throws BicycleNotFoundException, EmployeeNotFoundException {
        RepairEntity repairEntity = this.mapper.map(repair, RepairEntity.class);
        Optional<EmployeeEntity> employeeEntity = employeeDao.get(repair.getAssignedEmployeeId());
        Optional<CustomerBicycleEntity> bicycleEntity = bicycleDao.get(repair.getBicycleId());

        if (employeeEntity.isEmpty()) {
            throw new EmployeeNotFoundException();
        }


        if (bicycleEntity.isEmpty()) {
            throw new BicycleNotFoundException();
        }

        repairDao.save(repairEntity);

        Set<RepairProductEntity> repairProductEntities = repair.getSellablesUsed().stream()
                .filter(repairSellable -> repairSellable.getType() == SellableTypeEnum.PRODUCT)
                .map(repairSellable -> {
                    RepairProductEntity line = new RepairProductEntity();
                    ProductEntity product = productDao.get(repairSellable.getId()).orElseThrow();

                    RepairProductIdEntity repairProductIdEntity = new RepairProductIdEntity();
                    repairProductIdEntity.setProductId(product.getId());
                    repairProductIdEntity.setRepairId(repairEntity.getId());
                    line.setId(repairProductIdEntity);
                    line.setProduct(product);
                    line.setRepair(repairEntity);
                    line.setQuantity(repairSellable.getQuantity());

                    return line;
                }
        ).collect(Collectors.toSet());

        Set<RepairServiceEntity> repairServiceEntities = repair.getSellablesUsed().stream()
                .filter(repairSellable -> repairSellable.getType() == SellableTypeEnum.SERVICE)
                .map(repairService -> {
                    RepairServiceEntity line = new RepairServiceEntity();
                    ServiceEntity service = serviceDao.get(repairService.getId()).orElseThrow();

                    RepairServiceIdEntity repairServiceIdEntity = new RepairServiceIdEntity();
                    repairServiceIdEntity.setServiceId(service.getId());
                    repairServiceIdEntity.setRepairId(repairEntity.getId());
                    line.setId(repairServiceIdEntity);
                    line.setService(service);
                    line.setRepair(repairEntity);
                    line.setQuantity(repairService.getQuantity());

                    return line;
        }).collect(Collectors.toSet());

        repairEntity.setRepairProducts(repairProductEntities);
        repairEntity.setRepairServices(repairServiceEntities);

        repairDao.update(repairEntity);
    }

    public Repair get(Integer id) throws RepairNotFoundException {
        Optional<RepairEntity> repairEntity = repairDao.get(id);

        if (repairEntity.isEmpty()) {
            throw new RepairNotFoundException();
        }

        return mapper.map(repairEntity.get(), Repair.class);
    }

    public List<Repair> getAll() {
        return repairDao.getAll().stream().map(
                repair -> mapper.map(repair, Repair.class)
        ).collect(Collectors.toList());
    }

    public void update(Repair repair) throws RepairNotFoundException, EmployeeNotFoundException, BicycleNotFoundException {
        Optional<RepairEntity> repairFromDao = repairDao.get(repair.getId());

        if (repairFromDao.isEmpty()) {
            throw new RepairNotFoundException();
        }

        Optional<EmployeeEntity> employeeEntity = employeeDao.get(repair.getAssignedEmployeeId());

        if (employeeEntity.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        Optional<CustomerBicycleEntity> bicycleEntity = bicycleDao.get(repair.getBicycleId());


        if (bicycleEntity.isEmpty()) {
            throw new BicycleNotFoundException();
        }

        RepairEntity repairEntity = this.mapper.map(repair, RepairEntity.class);

        Set<RepairProductEntity> repairProductEntities = repair.getSellablesUsed().stream()
                .filter(repairSellable -> repairSellable.getType() == SellableTypeEnum.PRODUCT)
                .map(repairSellable -> {
                            RepairProductEntity line = new RepairProductEntity();
                            ProductEntity product = productDao.get(repairSellable.getId()).orElseThrow();

                            RepairProductIdEntity repairProductIdEntity = new RepairProductIdEntity();
                            repairProductIdEntity.setProductId(product.getId());
                            repairProductIdEntity.setRepairId(repairEntity.getId());
                            line.setId(repairProductIdEntity);
                            line.setProduct(product);
                            line.setRepair(repairEntity);
                            line.setQuantity(repairSellable.getQuantity());

                            return line;
                        }
                ).collect(Collectors.toSet());

        Set<RepairServiceEntity> repairServiceEntities = repair.getSellablesUsed().stream()
                .filter(repairSellable -> repairSellable.getType() == SellableTypeEnum.SERVICE)
                .map(repairService -> {
                    RepairServiceEntity line = new RepairServiceEntity();
                    ServiceEntity service = serviceDao.get(repairService.getId()).orElseThrow();

                    RepairServiceIdEntity repairServiceIdEntity = new RepairServiceIdEntity();
                    repairServiceIdEntity.setServiceId(service.getId());
                    repairServiceIdEntity.setRepairId(repairEntity.getId());
                    line.setId(repairServiceIdEntity);
                    line.setService(service);
                    line.setRepair(repairEntity);
                    line.setQuantity(repairService.getQuantity());

                    return line;
                }).collect(Collectors.toSet());

        repairEntity.setRepairProducts(repairProductEntities);
        repairEntity.setRepairServices(repairServiceEntities);

        repairDao.update(repairEntity);
    }

    public void delete(Integer id) throws RepairNotFoundException, ObjectStillReferencedException {
        Optional<RepairEntity> repairEntity = repairDao.get(id);

        if (repairEntity.isEmpty()) {
            throw new RepairNotFoundException();
        }

        try {
            repairDao.delete(repairEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
