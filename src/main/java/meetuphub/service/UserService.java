package meetuphub.service;

import meetuphub.model.Role;
import meetuphub.model.User;
import meetuphub.repository.RoleRepository;
import meetuphub.repository.UserRoleRepository;

import java.util.List;

import static meetuphub.repository.UserRepository.getUserData;

// Реализация добавления роли юзеру при регистрации(?)

public class UserService {

    public void addRoleToUser(int userId, int roleId) {
        List<User> users = getUserData("SELECT * FROM \"user\" WHERE id = ? ", userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден.");
        }

        List<Role> roles = RoleRepository.getRoleData("SELECT * FROM role WHERE id = ? ", roleId);
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        UserRoleRepository.addRoleToUser(roleId, userId);
    }

    public static User findUserByName(String name) {
        List<User> users = getUserData("SELECT * FROM \"user\" WHERE name = ?", name);
        return users.isEmpty() ? null : users.get(0);
    }
}
