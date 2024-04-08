package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.UserEntity;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.SessionNotFoundException;
import org.nolhtaced.core.exceptions.UnableToChangePasswordException;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.utilities.PasswordUtil;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

// TODO Error Handling Review
public class UserService extends BaseService {
    private final Dao<UserEntity, Integer> userDao;

    public UserService(NolhtacedSession session) {
        super(session);
        this.userDao = new DaoImpl<>(UserEntity.class);
    }

    public void create(User user) {
        UserEntity userEntity = this.mapper.map(user, UserEntity.class);
        String hashedPassword = PasswordUtil.generateHash(user.getPassword());
        userEntity.setPassword(hashedPassword);
        userDao.save(userEntity);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userDao.get(id);

        if (userEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        return this.mapper.map(userEntity.get(), User.class);
    }

    // TODO check if it makes sense to add the session logic here
    public User getByUsername(String userName) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userDao.getByUniqueAttribute("username", userName);

        if (userEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        return this.mapper.map(userEntity.get(), User.class);
    }

    public List<User> getAll() throws SessionNotFoundException {
        User currentUser = session.getCurrentUser().orElseThrow(SessionNotFoundException::new);

        return userDao.getAll().stream()
                .map(user -> this.mapper.map(user, User.class))
                .filter(user -> {
                    UserRoleEnum role = currentUser.getRole();

                    if (role == UserRoleEnum.ADMINISTRATOR) {
                        return true;
                    }

                    if (role == UserRoleEnum.MANAGER) {
                        return user.getRole() != UserRoleEnum.ADMINISTRATOR;
                    }

                    if (role == UserRoleEnum.SALES_REPRESENTATIVE || role == UserRoleEnum.MECHANIC) {
                        return user.getRole() == UserRoleEnum.CUSTOMER;
                    }

                    return false;
                }).collect(Collectors.toList());
    }

    public void update(User user) throws UserNotFoundException {
        Optional<UserEntity> userFromDao = userDao.get(user.getId());

        if (userFromDao.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserEntity userEntity = this.mapper.map(user, UserEntity.class);

        userDao.update(userEntity);
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, UnableToChangePasswordException {
        User user = getByUsername(username);

        String oldPasswordHash = PasswordUtil.generateHash(oldPassword);

        if (!PasswordUtil.checkPassword(oldPassword, oldPasswordHash)) {
            throw new UnableToChangePasswordException("Old password is incorrect");
        }

        String newPasswordHash = PasswordUtil.generateHash(newPassword);
        user.setPassword(newPasswordHash);
        update(user);
    }

    public boolean verifyPassword(String username, String password) throws UserNotFoundException {
        User user = getByUsername(username);
        return PasswordUtil.checkPassword(password, user.getPassword());
    }
    
    public void delete(Integer id) throws UserNotFoundException, ObjectStillReferencedException {
        Optional<UserEntity> userEntity = userDao.get(id);
        
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        try {
            userDao.delete(userEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
