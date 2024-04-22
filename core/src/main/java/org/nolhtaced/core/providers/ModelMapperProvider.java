package org.nolhtaced.core.providers;

import org.nolhtaced.core.mappers.*;
import org.modelmapper.ModelMapper;

public class ModelMapperProvider {
    private static ModelMapper modelMapper;

    private ModelMapperProvider() {}

    public static ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            registerConverters();
        }

        return modelMapper;
    }

    private static void registerConverters() {
        modelMapper.addConverter(UserMapper.entityToDomain);
        modelMapper.addConverter(UserMapper.domainToEntity);
        modelMapper.addConverter(CustomerMapper.entityToDomain);
        modelMapper.addConverter(CustomerMapper.domainToEntity);
        modelMapper.addConverter(BicycleMapper.entityToDomain);
        modelMapper.addConverter(BicycleMapper.domainToEntity);
        modelMapper.addConverter(RepairMapper.entityToDomain);
        modelMapper.addConverter(RepairMapper.domainToEntity);
        modelMapper.addConverter(CategoryMapper.entityToDomain);
        modelMapper.addConverter(CategoryMapper.domainToEntity);
        modelMapper.addConverter(ProductMapper.entityToDomain);
        modelMapper.addConverter(ProductMapper.domainToEntity);
        modelMapper.addConverter(ServiceMapper.entityToDomain);
        modelMapper.addConverter(ServiceMapper.domainToEntity);
        modelMapper.addConverter(EmployeeMapper.entityToDomain);
        modelMapper.addConverter(EmployeeMapper.domainToEntity);
        modelMapper.addConverter(TransactionMapper.entityToDomain);
        modelMapper.addConverter(TransactionMapper.domainToEntity);
        modelMapper.addConverter(AppointmentMapper.entityToDomain);
        modelMapper.addConverter(AppointmentMapper.domainToEntity);
    }
}
