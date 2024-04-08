package org.nolhtaced.core.mappers;

import org.nolhtaced.core.entities.ServiceEntity;
import org.nolhtaced.core.models.Service;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ServiceMapper {
    public static Converter<ServiceEntity, Service> entityToDomain = new Converter<ServiceEntity, Service>() {
        @Override
        public Service convert(MappingContext<ServiceEntity, Service> mappingContext) {
            ServiceEntity serviceEntity = mappingContext.getSource();
            Service service = new Service();

            service.setId(serviceEntity.getId());
            service.setName(serviceEntity.getName());
            service.setTitle(serviceEntity.getTitle());
            service.setPrice(serviceEntity.getPrice());

            return service;
        }
    };

    public static Converter<Service, ServiceEntity> domainToEntity = new Converter<Service, ServiceEntity>() {
        @Override
        public ServiceEntity convert(MappingContext<Service, ServiceEntity> mappingContext) {
            Service service = mappingContext.getSource();
            ServiceEntity serviceEntity = new ServiceEntity();

            serviceEntity.setId(service.getId());
            serviceEntity.setName(service.getName());
            serviceEntity.setTitle(service.getTitle());
            serviceEntity.setPrice(service.getPrice());

            return serviceEntity;
        }
    };
}
