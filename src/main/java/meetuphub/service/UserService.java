package meetuphub.service;

import meetuphub.models.Event;
import meetuphub.models.Role;
import meetuphub.models.User;
import meetuphub.repository.*;

import java.util.List;

public class UserService {

    public void addRoleToUser(int userId, int roleId) {
        List<User> users = UserRepository.getUserData("SELECT * FROM \"user\" WHERE id = ? ", userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден.");
        }

        List<Role> roles = RoleRepository.getRoleData("SELECT * FROM role WHERE id = ? ", roleId);
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        UserRoleRepository.addRoleToUser(roleId, userId);
    }
}
