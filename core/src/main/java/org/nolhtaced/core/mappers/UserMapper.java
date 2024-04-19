package org.nolhtaced.core.mappers;

import org.nolhtaced.core.entities.UserEntity;

import org.nolhtaced.core.models.User;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.HashMap;

public class UserMapper {
    // don't want to think too much about these hardcoded roles
    private static final HashMap<String, UserRoleEnum> REVERSE_USER_ROLES = new HashMap<>();

    static {
        REVERSE_USER_ROLES.put(UserRoleEnum.ADMINISTRATOR.value, UserRoleEnum.ADMINISTRATOR);
        REVERSE_USER_ROLES.put(UserRoleEnum.CUSTOMER.value, UserRoleEnum.CUSTOMER);
        REVERSE_USER_ROLES.put(UserRoleEnum.MANAGER.value, UserRoleEnum.MANAGER);
        REVERSE_USER_ROLES.put(UserRoleEnum.SALES_REPRESENTATIVE.value, UserRoleEnum.SALES_REPRESENTATIVE);
        REVERSE_USER_ROLES.put(UserRoleEnum.MECHANIC.value, UserRoleEnum.MECHANIC);
    }

    public static Converter<UserEntity, User> entityToDomain = new Converter<UserEntity, User>() {
        @Override
        public User convert(MappingContext<UserEntity, User> mappingContext) {
            UserEntity userEntity = mappingContext.getSource();
            User user = new User();

            user.setId(userEntity.getId());
            user.setUsername(userEntity.getUsername());
            user.setPassword(userEntity.getPassword());
            user.setFirstName(userEntity.getFirstName());
            user.setLastName(userEntity.getLastName());
            user.setDateOfBirth(userEntity.getDateOfBirth());
            user.setActive(userEntity.getActive());
            user.setRole(REVERSE_USER_ROLES.get(userEntity.getRole().trim()));

            return user;
        }
    };

    public static Converter<User, UserEntity> domainToEntity = new Converter<User, UserEntity>() {
        @Override
        public UserEntity convert(MappingContext<User, UserEntity> mappingContext) {
            User user = mappingContext.getSource();
            UserEntity userEntity = new UserEntity();

            userEntity.setId(user.getId());
            userEntity.setUsername(user.getUsername());
            userEntity.setPassword(user.getPassword());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setDateOfBirth(user.getDateOfBirth());
            userEntity.setActive(user.getActive());
            userEntity.setRole(user.getRole().value);

            return userEntity;
        }
    };
}
