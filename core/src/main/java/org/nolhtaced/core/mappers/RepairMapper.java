package org.nolhtaced.core.mappers;

import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerBicycleEntity;
import org.nolhtaced.core.entities.EmployeeEntity;
import org.nolhtaced.core.entities.RepairEntity;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.models.Repair;
import org.nolhtaced.core.enumerators.RepairStateEnum;
import org.nolhtaced.core.models.RepairItem;
import org.nolhtaced.core.models.Service;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.providers.ModelMapperProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RepairMapper {
    private static final ModelMapper mapper = ModelMapperProvider.getModelMapper();
    private static final HashMap<String, RepairStateEnum> REVERSE_REPAIR_STATES = new HashMap<>();
    private static final Dao<CustomerBicycleEntity, Integer> bicycleDao = new DaoImpl<>(CustomerBicycleEntity.class);
    private static final Dao<EmployeeEntity, Integer> employeeDao = new DaoImpl<>(EmployeeEntity.class);

    static {
        REVERSE_REPAIR_STATES.put(RepairStateEnum.PENDING.toString(), RepairStateEnum.PENDING);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.IN_PROGRESS.toString(), RepairStateEnum.IN_PROGRESS);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.ON_HOLD.toString(), RepairStateEnum.ON_HOLD);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.COMPLETED.toString(), RepairStateEnum.COMPLETED);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.CANCELLED.toString(), RepairStateEnum.CANCELLED);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.AWAITING_PAYMENT.toString(), RepairStateEnum.AWAITING_PAYMENT);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.QUALITY_CHECK.toString(), RepairStateEnum.QUALITY_CHECK);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.DELIVERED.toString(), RepairStateEnum.DELIVERED);
        REVERSE_REPAIR_STATES.put(RepairStateEnum.REOPENED.toString(), RepairStateEnum.REOPENED);
    }

    public static Converter<RepairEntity, Repair> entityToDomain = new Converter<RepairEntity, Repair>() {
        @Override
        public Repair convert(MappingContext<RepairEntity, Repair> mappingContext) {
            RepairEntity repairEntity = mappingContext.getSource();
            Repair repair = new Repair();

            repair.setId(repairEntity.getId());
            repair.setBicycleId(repairEntity.getBicycle().getId());
            repair.setNotes(repairEntity.getNotes());
            repair.setAssignedEmployeeId(repairEntity.getAssignedEmployee().getId());
            repair.setState(REVERSE_REPAIR_STATES.get(repairEntity.getCurrentState()));

            List<RepairItem<Product>> productsUsed = repairEntity.getRepairProducts().stream().map(
                    repairProduct -> new RepairItem<>(
                            mapper.map(repairProduct.getProduct(), Product.class),
                            repairProduct.getQuantity()
                    )
            ).collect(Collectors.toList());

            List<RepairItem<Service>> servicesUsed = repairEntity.getRepairServices().stream().map(
                    repairService -> new RepairItem<>(
                            mapper.map(repairService.getService(), Service.class),
                            repairService.getQuantity()
                    )
            ).collect(Collectors.toList());

            repair.setProductsUsed(productsUsed);
            repair.setServicesUsed(servicesUsed);

            return repair;
        }
    };

    public static Converter<Repair, RepairEntity> domainToEntity = new Converter<Repair, RepairEntity>() {
        @Override
        public RepairEntity convert(MappingContext<Repair, RepairEntity> mappingContext) {
            Repair repair = mappingContext.getSource();
            RepairEntity repairEntity = new RepairEntity();

            CustomerBicycleEntity bicycle = bicycleDao.get(repair.getBicycleId()).orElseThrow();
            EmployeeEntity assignedEmployee = employeeDao.get(repair.getAssignedEmployeeId()).orElseThrow();

            repairEntity.setId(repair.getId());
            repairEntity.setBicycle(bicycle);
            repairEntity.setNotes(repair.getNotes());
            repairEntity.setAssignedEmployee(assignedEmployee);
            repairEntity.setCurrentState(repair.getState().toString());

            return repairEntity;
        }
    };
}
