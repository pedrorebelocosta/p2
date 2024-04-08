package org.nolhtaced.core.mappers;

import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CustomerBicycleEntity;
import org.nolhtaced.core.entities.CustomerEntity;
import org.nolhtaced.core.models.Bicycle;
import org.nolhtaced.core.enumerators.BicycleTypeEnum;
import org.nolhtaced.core.models.Repair;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.nolhtaced.core.providers.ModelMapperProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BicycleMapper {
    private static final ModelMapper mapper = ModelMapperProvider.getModelMapper();
    private static final Dao<CustomerEntity, Integer> customerDao = new DaoImpl<>(CustomerEntity.class);
    private static final HashMap<String, BicycleTypeEnum> REVERSE_BICYCLE_TYPES = new HashMap<>();

    static {
        REVERSE_BICYCLE_TYPES.put(BicycleTypeEnum.ROAD.toString(), BicycleTypeEnum.ROAD);
        REVERSE_BICYCLE_TYPES.put(BicycleTypeEnum.MOUNTAIN.toString(), BicycleTypeEnum.MOUNTAIN);
        REVERSE_BICYCLE_TYPES.put(BicycleTypeEnum.GRAVEL.toString(), BicycleTypeEnum.GRAVEL);
    }

    public static Converter<CustomerBicycleEntity, Bicycle> entityToDomain = new Converter<CustomerBicycleEntity, Bicycle>() {
        @Override
        public Bicycle convert(MappingContext<CustomerBicycleEntity, Bicycle> mappingContext) {
            CustomerBicycleEntity bicycleEntity = mappingContext.getSource();
            Bicycle bicycle = new Bicycle();

            bicycle.setId(bicycleEntity.getId());
            bicycle.setOwnerId(bicycleEntity.getCustomer().getId());
            bicycle.setName(bicycleEntity.getName());
            bicycle.setModel(bicycleEntity.getModel());
            bicycle.setBrand(bicycleEntity.getBrand());
            bicycle.setType(REVERSE_BICYCLE_TYPES.get(bicycleEntity.getType()));

            List<Repair> repairs = bicycleEntity.getRepairs().stream().map(
                    repair -> mapper.map(repair, Repair.class)
            ).collect(Collectors.toList());

            bicycle.setRepairs(repairs);

            return bicycle;
        }
    };

    public static Converter<Bicycle, CustomerBicycleEntity> domainToEntity = new Converter<Bicycle, CustomerBicycleEntity>() {
        @Override
        public CustomerBicycleEntity convert(MappingContext<Bicycle, CustomerBicycleEntity> mappingContext) {
            Bicycle bicycle = mappingContext.getSource();
            CustomerBicycleEntity bicycleEntity = new CustomerBicycleEntity();

            CustomerEntity customer = customerDao.get(bicycle.getOwnerId()).orElseThrow();

            bicycleEntity.setId(bicycle.getId());
            bicycleEntity.setCustomer(customer);
            bicycleEntity.setName(bicycle.getName());
            bicycleEntity.setModel(bicycle.getModel());
            bicycleEntity.setBrand(bicycle.getBrand());
            bicycleEntity.setType(bicycle.getType().toString());

            return bicycleEntity;
        }
    };
}
